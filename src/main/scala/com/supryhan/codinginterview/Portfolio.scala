package com.supryhan.codinginterview

import cats.effect.Async
import cats.syntax.all._

import java.time.Instant
import java.util.UUID

/**
 * This is an interview done with our domain expert, about what portfolios are,
 * how will you represent it with code?
 *
 * A customer's portfolio is a list of shares that the customer owns. Each entry
 * in the portfolio contains the name of the share - i.e. 'Apple', 'Google',
 * the number of shares owned and the individual share value.
 *
 * Every Moneyfarm customer portfolio refers to a 'Model' which represents the
 * percentage of each share that our Investment Team thinks that the customer
 * should own, based on the customer's risk tolerance.
 *
 * Obviously the more money the customer puts into their portfolio the more of
 * each share he/she owns but the percentages should stay roughly the same.
 * Every 3 months the Investment team changes the makeup of the Model and each
 * customer's portfolio must be realigned to the same percentage distribution
 * as their Model.
 *
 * This is done by buying and selling shares - these are called trades.
 */

case class Client(id: UUID, portfolios: List[Portfolio])

case class Portfolio(id: UUID, modelId: ModelId, appliedModelVersion: Int, cashBalance: BigDecimal, shares: List[OwnedShare], riskTolerance: Double, createdAt: Instant)

case class OwnedShare(share: Share, quantity: BigDecimal)

case class Share(ticker: Ticker, name: String)

case class MarketPrice(ticker: Ticker, price: BigDecimal, asOf: Instant)

case class Ticker(value: String)

case class ModelId(value: UUID)

case class InvestmentModel(id: ModelId, version: Int, allocations: Map[Ticker, BigDecimal], effectiveFrom: Instant)

case class RiskMetric(ticker: Ticker, score: Double, asOf: Instant)

sealed trait Trade {
  def ticker: Ticker

  def quantity: BigDecimal
}

object Trade {
  final case class Buy(
    ticker: Ticker,
    quantity: BigDecimal
  ) extends Trade

  final case class Sell(
    ticker: Ticker,
    quantity: BigDecimal
  ) extends Trade
}

trait InvestmentModelRepository[F[_]] {
  def latest(modelId: ModelId): F[InvestmentModel]
}

trait MarketPriceService[F[_]] {
  def getPrices(tickers: Set[Ticker]): F[Map[Ticker, MarketPrice]]
}

trait TradeExecutor[F[_]] {
  def execute(
    portfolio: Portfolio,
    trades: List[Trade]
  ): F[Portfolio]
}

trait PortfolioRepository[F[_]] {
  def save(portfolio: Portfolio): F[Unit]
}


object Solution extends App {

  import SampleData._

  val updatedPortfolio =
    PortfolioRebalancer.rebalancePortfolio(
      portfolio,
      modelV2,
      prices,
      shareCatalog
    )

  printPortfolio(updatedPortfolio, prices)

  def printPortfolio(
    portfolio: Portfolio,
    prices: Map[Ticker, MarketPrice]
  ): Unit = {

    val positions = portfolio.shares.map { owned =>
      val price = prices(owned.share.ticker).price
      val value = owned.quantity * price

      (owned, price, value)
    }

    val totalValue =
      portfolio.cashBalance +
        positions.map(_._3).sum

    println(s"Portfolio: ${portfolio.id}")
    println(s"Model version: ${portfolio.appliedModelVersion}")
    println(s"Total value: $$${totalValue}")
    println(s"Cash balance: $$${portfolio.cashBalance}")
    println("Positions:")

    positions.foreach {
      case (owned, price, value) =>
        val allocation =
          value / totalValue * BigDecimal(100)

        println(
          s"  ${owned.share.ticker.value} (${owned.share.name}): " +
            s"quantity=${owned.quantity}, " +
            s"price=$$${price}, " +
            s"value=$$${value}, " +
            s"allocation=${allocation}%"
        )
    }
  }

}


final class RebalancingService[F[_] : Async](
  modelRepository: InvestmentModelRepository[F],
  priceService: MarketPriceService[F],
  tradeExecutor: TradeExecutor[F],
  portfolioRepository: PortfolioRepository[F]
) {

  def rebalancePortfolio(portfolio: Portfolio): F[Portfolio] =
    for {
      targetModel <-
        modelRepository.latest(portfolio.modelId)

      tickers =
        targetModel.allocations.keySet ++
          portfolio.shares.map(_.share.ticker)

      prices <-
        priceService.getPrices(tickers)

      trades =
        TradeCalculator.calculateTrades(
          portfolio,
          targetModel,
          prices
        )

      rebalancedPortfolio <-
        tradeExecutor.execute(portfolio, trades)

      updatedPortfolio =
        rebalancedPortfolio.copy(
          appliedModelVersion = targetModel.version
        )

      _ <-
        portfolioRepository.save(updatedPortfolio)

    } yield updatedPortfolio
}

object PortfolioRebalancer {
  def rebalancePortfolio(
    portfolio: Portfolio,
    targetModel: InvestmentModel,
    prices: Map[Ticker, MarketPrice],
    shareCatalog: Map[Ticker, Share]
  ): Portfolio = {

    val trades =
      TradeCalculator.calculateTrades(
        portfolio,
        targetModel,
        prices
      )

    val initialQuantities: Map[Ticker, BigDecimal] =
      portfolio.shares.map { ownedShare =>
        ownedShare.share.ticker -> ownedShare.quantity
      }.toMap

    val (updatedQuantities, updatedCashBalance) =
      trades.foldLeft(
        (initialQuantities, portfolio.cashBalance)
      ) {

        case ((quantities, cash), Trade.Sell(ticker, quantity)) =>
          val currentQuantity =
            quantities.getOrElse(ticker, BigDecimal(0))

          require(currentQuantity >= quantity)

          val tradeValue =
            quantity * prices(ticker).price

          (
            quantities.updated(
              ticker,
              currentQuantity - quantity
            ),
            cash + tradeValue
          )

        case ((quantities, cash), Trade.Buy(ticker, quantity)) =>
          val currentQuantity =
            quantities.getOrElse(ticker, BigDecimal(0))

          val tradeValue =
            quantity * prices(ticker).price

          require(cash >= tradeValue)

          (
            quantities.updated(
              ticker,
              currentQuantity + quantity
            ),
            cash - tradeValue
          )
      }

    val updatedShares =
      updatedQuantities.toList
        .collect {
          case (ticker, quantity) if quantity > 0 =>
            OwnedShare(
              shareCatalog(ticker),
              quantity
            )
        }
        .sortBy(_.share.ticker.value)

    portfolio.copy(
      appliedModelVersion = targetModel.version,
      cashBalance = updatedCashBalance,
      shares = updatedShares
    )
  }
}

object TradeCalculator {

  def calculateTrades(
    portfolio: Portfolio,
    targetModel: InvestmentModel,
    prices: Map[Ticker, MarketPrice]
  ): List[Trade] = {

    require(portfolio.modelId == targetModel.id)

    val allocationTotal = targetModel.allocations.values.foldLeft(BigDecimal(0))(_ + _)

    require(allocationTotal == BigDecimal(1))

    val quantitiesByTicker: Map[Ticker, BigDecimal] =
      portfolio.shares
        .groupBy(_.share.ticker)
        .map {
          case (ticker, positions) =>
            ticker -> positions.foldLeft(BigDecimal(0)) {
              case (total, position) =>
                total + position.quantity
            }
        }

    def priceOf(ticker: Ticker): BigDecimal = {
      val marketPrice = prices.getOrElse(
        ticker,
        throw new IllegalArgumentException(
          s"Missing price for ${ticker.value}"
        )
      )

      require(marketPrice.price > 0)
      marketPrice.price
    }

    val totalPortfolioValue =
      quantitiesByTicker.foldLeft(portfolio.cashBalance) {
        case (total, (ticker, quantity)) =>
          total + quantity * priceOf(ticker)
      }

    val allTickers =
      targetModel.allocations.keySet ++ quantitiesByTicker.keySet

    val trades: List[Trade] =
      allTickers.toList.flatMap { ticker =>
        val currentQuantity =
          quantitiesByTicker.getOrElse(ticker, BigDecimal(0))

        val currentValue =
          currentQuantity * priceOf(ticker)

        val targetWeight =
          targetModel.allocations.getOrElse(ticker, BigDecimal(0))

        val targetValue =
          totalPortfolioValue * targetWeight

        val difference =
          targetValue - currentValue

        if (difference > 0)
          List(Trade.Buy(ticker, difference / priceOf(ticker)))
        else if (difference < 0)
          List(Trade.Sell(ticker, -difference / priceOf(ticker)))
        else
          Nil
      }

    trades.sortBy {
      case Trade.Sell(ticker, _) => (0, ticker.value)
      case Trade.Buy(ticker, _) => (1, ticker.value)
    }
  }
}


object SampleData {

  val googleTicker = Ticker("GOOGL")
  val amazonTicker = Ticker("AMZN")

  val google = Share(googleTicker, "Alphabet")
  val amazon = Share(amazonTicker, "Amazon")

  val modelId = ModelId(
    UUID.fromString("10000042-0000-0000-0000-000000000001")
  )

  val modelV1 = InvestmentModel(
    id = modelId,
    version = 1,
    allocations = Map(
      googleTicker -> BigDecimal("0.50"),
      amazonTicker -> BigDecimal("0.50")
    ),
    effectiveFrom = Instant.parse("2026-01-01T00:00:00Z")
  )

  val modelV2 = InvestmentModel(
    id = modelId,
    version = 2,
    allocations = Map(
      googleTicker -> BigDecimal("0.40"),
      amazonTicker -> BigDecimal("0.60")
    ),
    effectiveFrom = Instant.parse("2026-04-01T00:00:00Z")
  )

  val prices = Map(
    googleTicker -> MarketPrice(
      googleTicker,
      BigDecimal("200"),
      Instant.parse("2026-04-01T10:00:00Z")
    ),
    amazonTicker -> MarketPrice(
      amazonTicker,
      BigDecimal("100"),
      Instant.parse("2026-04-01T10:00:00Z")
    )
  )

  val portfolio = Portfolio(
    id = UUID.fromString("20000042-0000-0000-0000-000000000001"),
    modelId = modelId,
    appliedModelVersion = 1,
    cashBalance = BigDecimal("0"),
    shares = List(
      OwnedShare(google, BigDecimal("0.25")), // $50
      OwnedShare(amazon, BigDecimal("0.50")) // $50
    ),
    riskTolerance = 0.5,
    createdAt = Instant.parse("2026-01-01T00:00:00Z")
  )

  val client = Client(
    UUID.fromString("30000042-0000-0000-0000-000000000001"),
    List(portfolio)
  )

  val shareCatalog: Map[Ticker, Share] = Map(
    googleTicker -> google,
    amazonTicker -> amazon
  )
}
