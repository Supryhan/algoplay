package com.supryhan.problemslab.simple

import scala.annotation.tailrec
import scala.math.BigDecimal.RoundingMode
import scala.util.Try

object BigIntAndDecimalPractice extends App {

  /*
   * BigInt and BigDecimal practice tasks.
   *
   * Recommended order:
   * 1. Large factorial
   * 2. Base-36 conversion
   * 3. Invoice calculation
   * 4. Money splitting
   * 5. Compound interest
   *
   * Uncomment one runTask method at the bottom of the file
   * when you are ready to test the corresponding solution.
   */

  // ===========================================================================
  // Task 1: Large factorial with BigInt
  // ===========================================================================

  /*
   * Calculate n! using BigInt.
   *
   * A factorial can become too large for Int or Long,
   * so the result must be represented as BigInt.
   *
   * Requirements:
   * 1. Return 1 for n = 0.
   * 2. Reject negative values.
   * 3. Do not convert the final result to Int or Long.
   *
   * Examples:
   * factorial(0)  == 1
   * factorial(5)  == 120
   * factorial(20) == 2432902008176640000
   */
  def factorial(n: Int): BigInt = {
    require(n >= 0, "Factorial is not defined for negative numbers")

    @tailrec
    def factr(current: Int, acc: BigInt): BigInt = {
      current match {
        case 0 | 1 => acc
        case _ => factr(current - 1, acc * current)
      }
    }

    factr(n, BigInt(1))
  }

  /*
   * Return the number of decimal digits in n!.
   *
   * Examples:
   * factorialDigitCount(5)  == 3 because 5! = 120
   * factorialDigitCount(10) == 7 because 10! = 3628800
   */
  def factorialDigitCount(n: Int): Int =
    factorial(n).toString.length

  def runTask1(): Unit = {
    assert(factorial(0) == BigInt(1))
    assert(factorial(1) == BigInt(1))
    assert(factorial(5) == BigInt(120))
    assert(factorial(20) == BigInt("2432902008176640000"))

    assert(factorialDigitCount(5) == 3)
    assert(factorialDigitCount(10) == 7)

    println("Task 1 passed.")
    println(s"100! = ${factorial(100)}")
  }

  // ===========================================================================
  // Task 2: Base-36 identifier using BigInt
  // ===========================================================================

  /*
   * Convert a non-negative BigInt value to a base-36 string.
   *
   * Base 36 uses:
   * - digits 0-9
   * - letters a-z
   *
   * Requirements:
   * 1. Reject negative values.
   * 2. Return lowercase letters.
   * 3. Do not implement the numeral-system conversion manually.
   *
   * Examples:
   * toBase36(0)   == "0"
   * toBase36(35)  == "z"
   * toBase36(36)  == "10"
   * toBase36(255) == "73"
   */
  def toBase36(value: BigInt): String =
    ???

  /*
   * Convert a base-36 string back to BigInt.
   *
   * Requirements:
   * 1. Return None for an invalid string.
   * 2. Accept both lowercase and uppercase letters.
   * 3. Do not throw an exception to the caller.
   * 4. Reject empty strings.
   *
   * Hint:
   * Try can be useful for converting an exception into Option.
   */
  def fromBase36(value: String): Option[BigInt] =
    ???

  def runTask2(): Unit = {
    assert(toBase36(BigInt(0)) == "0")
    assert(toBase36(BigInt(35)) == "z")
    assert(toBase36(BigInt(36)) == "10")
    assert(toBase36(BigInt(255)) == "73")

    assert(fromBase36("0").contains(BigInt(0)))
    assert(fromBase36("z").contains(BigInt(35)))
    assert(fromBase36("Z").contains(BigInt(35)))
    assert(fromBase36("73").contains(BigInt(255)))
    assert(fromBase36("").isEmpty)
    assert(fromBase36("invalid!").isEmpty)

    val original =
      BigInt("123456789012345678901234567890")

    val encoded =
      toBase36(original)

    val decoded =
      fromBase36(encoded)

    assert(decoded.contains(original))

    println("Task 2 passed.")
    println(s"Decimal value: $original")
    println(s"Base-36 value: $encoded")
  }

  // ===========================================================================
  // Task 3: Invoice calculation with BigDecimal
  // ===========================================================================

  final case class InvoiceItem(
    name: String,
    unitPrice: BigDecimal,
    quantity: Int
  )

  final case class InvoiceSummary(
    subtotal: BigDecimal,
    discount: BigDecimal,
    tax: BigDecimal,
    total: BigDecimal
  )

  /*
   * Calculate an invoice summary.
   *
   * Calculation order:
   * 1. subtotal = sum of unitPrice * quantity
   * 2. discount = subtotal * discountRate
   * 3. taxableAmount = subtotal - discount
   * 4. tax = taxableAmount * taxRate
   * 5. total = taxableAmount + tax
   *
   * Requirements:
   * 1. All monetary results must have exactly two decimal places.
   * 2. Use HALF_UP rounding.
   * 3. Reject negative prices.
   * 4. Reject negative quantities.
   * 5. discountRate must be between 0 and 1.
   * 6. taxRate must be between 0 and 1.
   *
   * Rate examples:
   * 0.10 represents 10%.
   * 0.20 represents 20%.
   */
  def calculateInvoice(
    items: List[InvoiceItem],
    discountRate: BigDecimal,
    taxRate: BigDecimal
  ): InvoiceSummary =
    ???

  def runTask3(): Unit = {
    val items = List(
      InvoiceItem(
        name = "Keyboard",
        unitPrice = BigDecimal("99.99"),
        quantity = 2
      ),
      InvoiceItem(
        name = "Mouse",
        unitPrice = BigDecimal("25.50"),
        quantity = 1
      )
    )

    val result = calculateInvoice(
      items = items,
      discountRate = BigDecimal("0.10"),
      taxRate = BigDecimal("0.20")
    )

    /*
     * Expected calculation:
     *
     * Subtotal:
     * 99.99 * 2 + 25.50 = 225.48
     *
     * Discount:
     * 225.48 * 0.10 = 22.548 -> 22.55
     *
     * Taxable amount:
     * 225.48 - 22.55 = 202.93
     *
     * Tax:
     * 202.93 * 0.20 = 40.586 -> 40.59
     *
     * Total:
     * 202.93 + 40.59 = 243.52
     */
    assert(result.subtotal == BigDecimal("225.48"))
    assert(result.discount == BigDecimal("22.55"))
    assert(result.tax == BigDecimal("40.59"))
    assert(result.total == BigDecimal("243.52"))

    println("Task 3 passed.")
    println(result)
  }

  // ===========================================================================
  // Task 4: Split money without losing cents
  // ===========================================================================

  /*
   * Split a monetary amount between several people.
   *
   * The sum of all returned parts must be exactly equal
   * to the original amount.
   *
   * Example:
   *
   * splitMoney(10.00, 3) should return:
   * List(3.34, 3.33, 3.33)
   *
   * A naive implementation that returns 3.33 three times
   * loses one cent:
   *
   * 3.33 + 3.33 + 3.33 = 9.99
   *
   * Requirements:
   * 1. The amount must not be negative.
   * 2. The amount must have no more than two decimal places.
   * 3. numberOfParts must be greater than zero.
   * 4. Convert the amount to cents.
   * 5. Use BigInt to distribute the cents.
   * 6. Convert each part back to BigDecimal.
   * 7. The difference between any two parts must not exceed one cent.
   * 8. Distribute remaining cents starting from the first part.
   */
  def splitMoney(
    amount: BigDecimal,
    numberOfParts: Int
  ): List[BigDecimal] =
    ???

  def runTask4(): Unit = {
    val firstResult =
      splitMoney(BigDecimal("10.00"), numberOfParts = 3)

    assert(
      firstResult == List(
        BigDecimal("3.34"),
        BigDecimal("3.33"),
        BigDecimal("3.33")
      )
    )

    assert(firstResult.size == 3)
    assert(firstResult.sum == BigDecimal("10.00"))

    val secondResult =
      splitMoney(BigDecimal("0.05"), numberOfParts = 2)

    assert(
      secondResult == List(
        BigDecimal("0.03"),
        BigDecimal("0.02")
      )
    )

    assert(secondResult.sum == BigDecimal("0.05"))

    val thirdResult =
      splitMoney(BigDecimal("1.00"), numberOfParts = 6)

    assert(
      thirdResult == List(
        BigDecimal("0.17"),
        BigDecimal("0.17"),
        BigDecimal("0.17"),
        BigDecimal("0.17"),
        BigDecimal("0.16"),
        BigDecimal("0.16")
      )
    )

    assert(thirdResult.sum == BigDecimal("1.00"))

    println("Task 4 passed.")
    println(firstResult)
  }

  // ===========================================================================
  // Task 5: Compound interest calculator
  // ===========================================================================

  final case class InvestmentResult(
    initialAmount: BigDecimal,
    finalAmount: BigDecimal,
    totalInterest: BigDecimal
  )

  /*
   * Calculate compound interest.
   *
   * Formula:
   *
   * finalAmount =
   *   principal *
   *   (1 + annualRate / periodsPerYear) ^ totalPeriods
   *
   * totalPeriods =
   *   years * periodsPerYear
   *
   * Requirements:
   * 1. Use BigDecimal for all financial calculations.
   * 2. Reject a negative principal.
   * 3. Reject a negative annual rate.
   * 4. Reject a negative number of years.
   * 5. periodsPerYear must be greater than zero.
   * 6. Keep sufficient precision during intermediate calculations.
   * 7. Round only the final monetary results to two decimal places.
   * 8. Use HALF_EVEN rounding for final monetary results.
   *
   * Example:
   *
   * principal = 1000.00
   * annualRate = 0.05
   * years = 2
   * periodsPerYear = 12
   *
   * The annual rate 0.05 represents 5%.
   */
  def calculateInvestment(
    principal: BigDecimal,
    annualRate: BigDecimal,
    years: Int,
    periodsPerYear: Int
  ): InvestmentResult =
    ???

  /*
   * Convert the earned interest to an exact number of cents.
   *
   * Examples:
   *
   * 102.57 of interest represents 10257 cents.
   * 0.01 of interest represents 1 cent.
   *
   * Requirements:
   * 1. Multiply totalInterest by 100.
   * 2. Return Some only when the result is an exact integer.
   * 3. Do not silently discard a fractional part.
   *
   * Hint:
   * Look at BigDecimal.toBigIntExact.
   */
  def interestInCents(
    result: InvestmentResult
  ): Option[BigInt] =
    ???

  def runTask5(): Unit = {
    val result = calculateInvestment(
      principal = BigDecimal("1000.00"),
      annualRate = BigDecimal("0.05"),
      years = 2,
      periodsPerYear = 12
    )

    assert(result.initialAmount == BigDecimal("1000.00"))
    assert(result.finalAmount > result.initialAmount)

    assert(
      result.totalInterest ==
        result.finalAmount - result.initialAmount
    )

    assert(
      interestInCents(result).contains(
        result.totalInterest.bigDecimal
          .movePointRight(2)
          .toBigIntegerExact
      )
    )

    val exactResult = InvestmentResult(
      initialAmount = BigDecimal("1000.00"),
      finalAmount = BigDecimal("1102.57"),
      totalInterest = BigDecimal("102.57")
    )

    assert(
      interestInCents(exactResult).contains(BigInt(10257))
    )

    println("Task 5 passed.")
    println(result)
    println(s"Interest in cents: ${interestInCents(result)}")
  }

  // ===========================================================================
  // Practice execution
  // ===========================================================================

  println("BigInt and BigDecimal practice.")
  println("Uncomment one runTask method to test your implementation.")

  runTask1()
  // runTask2()
  // runTask3()
  // runTask4()
  // runTask5()
}