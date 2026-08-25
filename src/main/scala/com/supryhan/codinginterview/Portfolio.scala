package com.supryhan.codinginterview

import java.time.LocalDate
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

case class Portfolio(
  clientId: UUID,
  money: Double,
  shares: List[OwnedShare],
  riskTolerance: Double,
  investmentDate: LocalDate
)

case class Share(
  name: String,
  amount: Int,
  risk: Double
)

case class OwnedShare(
  share: Share,
  owned: Double
)

object Solution {

}
