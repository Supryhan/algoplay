package com.supryhan.core.problems

import com.supryhan.problemslab.lx.streams1.Transaction

/**
 * Create a financial transaction domain model.
 *
 * Define a sealed trait named Transaction that declares the common
 * properties id, amount, and currency.
 *
 * Implement two final case classes:
 *
 * 1. CardPayment, containing:
 *    - transaction ID;
 *    - amount;
 *    - currency;
 *    - last four digits of the card;
 *    - merchant name;
 *    - transaction status with a default value.
 *
 * 2. BankTransfer, containing:
 *    - transaction ID;
 *    - amount;
 *    - currency;
 *    - sender account;
 *    - recipient account;
 *    - transaction status with a default value.
 *
 * Requirements:
 * - Decide where def, val, and override should be used.
 * - Create one instance of each transaction type.
 * - Access their common properties through references of type Transaction.
 * - Create an updated copy of one transaction using the copy method.
 * - Use pattern matching to identify and process both transaction types.
 * - Keep all domain types and demonstration code inside this object.
 */
object FinancialTransactionModeling extends App {

  sealed trait Transaction {
    def id: Long

    def amount: BigDecimal

    def currency: String

    def status: TransactionStatus
  }

  sealed trait TransactionStatus

  object TransactionStatus {
    case object New extends TransactionStatus

    case object InProgress extends TransactionStatus

    case object Completed extends TransactionStatus
  }

  final case class CardPayment(
    override val id: Long,
    override val amount: BigDecimal,
    override val currency: String,
    cardLastFourDigits: Int,
    merchantName: String,
    status: TransactionStatus = TransactionStatus.New
  ) extends Transaction

  final case class BankTransfer(
    override val id: Long,
    override val amount: BigDecimal,
    override val currency: String,
    senderAccount: String,
    recipientAccount: String,
    override val status: TransactionStatus = TransactionStatus.New
  ) extends Transaction

  val cardPaymentExample =
    CardPayment(1L, BigDecimal(100), "USD", 1234, "CitiBank", TransactionStatus.New)
  val bankTransfer =
    BankTransfer(2L, BigDecimal(250), "EUR", "UA001234", "DE009876", TransactionStatus.Completed)

  println(cardPaymentExample.id)
  println(cardPaymentExample.merchantName)
  println(bankTransfer.id)

  def describe(transaction: Transaction): String =
    transaction match {
      case CardPayment(id, amount, currency, lastFourDigits, merchant, status) =>
        s"Card payment #$id: $amount $currency at $merchant, " +
          s"card ****$lastFourDigits, status: $status"
      case BankTransfer(id, amount, currency, sender, recipient, status) =>
        s"Bank transfer #$id: $amount $currency from $sender to $recipient, " +
          s"status: $status"
    }

  println(describe(cardPaymentExample))
  println(describe(bankTransfer))

  val completedPayment = cardPaymentExample.copy(status = TransactionStatus.Completed)

  println(describe(completedPayment))

}