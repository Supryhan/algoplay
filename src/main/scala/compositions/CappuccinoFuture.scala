package compositions

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object CappuccinoFuture extends App {
  val cappuccinoFuture = new CappuccinoFuture
  cappuccinoFuture.prepareCappuccino() onComplete  {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
  cappuccinoFuture.prepareCappucinoFlatMap() onComplete  {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
  Await.result(cappuccinoFuture.prepareCappucinoFlatMap(), Duration.Inf)
}

class CappuccinoFuture {

  type CoffeeBeans = String
  type GroundCoffee = String

  case class Water(temperature: Int)

  type Milk = String
  type FrothedMilk = String
  type Espresso = String
  type Cappuccino = String

  case class GrindingException(msg: String) extends Exception(msg)

  case class FrothingException(msg: String) extends Exception(msg)

  case class WaterBoilingException(msg: String) extends Exception(msg)

  case class BrewingException(msg: String) extends Exception(msg)

  def grind(beans: CoffeeBeans): Future[GroundCoffee] = Future {
    if (beans == "baked beans") throw GrindingException("are you joking?")
    s"ground coffee of $beans"
  }

  def heatWater(water: Water): Future[Water] = Future.successful { water.copy(temperature = 85) }

  def frothMilk(milk: Milk): Future[FrothedMilk] = Future.successful { s"frothed $milk" }

  def brew(coffee: GroundCoffee, heatedWater: Water): Future[Espresso] = Future.successful { "espresso" }

  def combine(espresso: Espresso, frothedMilk: FrothedMilk): Cappuccino = "cappuccino"

  def prepareCappuccino(): Future[Cappuccino] = for {
    ground <- grind("arabica beans")
    water <- heatWater(Water(25))
    espresso <- brew(ground, water)
    foam <- frothMilk("milk")
    combined <- Future { combine(espresso, foam) }
  } yield combined

  def prepareCappucinoFlatMap(): Future[Cappuccino] =
    grind("arabica beans")
      .flatMap(ground => heatWater(Water(25))
        .flatMap(water => brew(ground, water)
          .flatMap(espresso => frothMilk("milk")
            .map(foam => combine(espresso, foam)))))
}
