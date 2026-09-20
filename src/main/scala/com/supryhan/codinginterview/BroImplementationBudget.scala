package com.supryhan.codinginterview

/*
* Live Coding Exercise — Personal Finance Tracker
*
* Build a program that analyzes users' monthly expenses.
*
* Each user has a name and a set of spending limits for different categories.
* Each expense contains the user's name, a category, and an amount. A user may
* have multiple expenses in the same category.
*
* The program must:
*
* 1. Calculate the total amount spent by each user in each category.
* 2. Find categories where a user's total spending strictly exceeds the
* configured budget and calculate the excess amount:
*
* ```
*   excess = total spent - budget
*  ```
*
* 3. Find the category with the highest total spending across all users.
*
* Expenses in categories without a configured budget must be included in the
* global category totals but must not be reported as budget overruns.
*
* Print the calculated budget overruns and the most expensive category.
*/


object BroImplementationBudget extends App {

  private case class User(
    name: String,
    budgets: Map[String, BigDecimal]
  )

  private case class Expense(
    userName: String,
    category: String,
    amount: BigDecimal
  )

  private case class BudgetOverrun(
    userName: String,
    category: String,
    spent: BigDecimal,
    limit: BigDecimal,
    overBy: BigDecimal
  )

  private val users: List[User] = List(
    User("Alice", Map("groceries" -> BigDecimal(30), "transport" -> BigDecimal(120))),
    User("Bob", Map("groceries" -> BigDecimal(10), "transport" -> BigDecimal(50))),
    User("Carla", Map("groceries" -> BigDecimal(20), "transport" -> BigDecimal(90))),
  )

  private val expenses: List[Expense] = List(
    Expense("Alice", "groceries", BigDecimal(10)),
    Expense("Alice", "groceries", BigDecimal(20)),
    Expense("Alice", "transport", BigDecimal(120)),
    Expense("Bob", "groceries", BigDecimal(10)),
    Expense("Bob", "groceries", BigDecimal(20)),
    Expense("Bob", "transport", BigDecimal(50)),
    Expense("Carla", "groceries", BigDecimal(10)),
    Expense("Carla", "groceries", BigDecimal(20)),
    Expense("Carla", "transport", BigDecimal(90)),
  )

  private def totalExpensesByUserAndCategory(
    expenses: List[Expense]
  ): Map[(String, String), BigDecimal] =
    expenses
      .groupMapReduce(
        expense =>
          (expense.userName, expense.category)
      )(_.amount)(_ + _)

  private def mostExpensiveCategory(expenses: List[Expense]): Option[(String, BigDecimal)] =
    expenses
      .groupMapReduce(_.category)(_.amount)(_ + _)
      .maxByOption(_._2)

  private def findBudgetOverruns(budgets: List[User], expenses: List[Expense]): Map[String, List[(String, BigDecimal, BigDecimal, BigDecimal)]] = {
    val totalSpendByUserAndCategory: Map[(String, String), BigDecimal] = totalExpensesByUserAndCategory(expenses)
    budgets.map {
        usersBudget => {
          (usersBudget.name,
            usersBudget.budgets.map {
                case (category, limit) =>
                  val total: BigDecimal = totalSpendByUserAndCategory.getOrElse((usersBudget.name, category), BigDecimal(0))
                  (category, total, limit, total - limit)
              }
              .filter(cat => cat._4 > 0)
              .toList
          )
        }
      }
      .toMap
      .filterNot(enn => enn._2.isEmpty)
  }

  // Calculate results
  private val totals: Map[(String, String), BigDecimal] = totalExpensesByUserAndCategory(expenses)
  private val overruns: Map[String, List[(String, BigDecimal, BigDecimal, BigDecimal)]] = findBudgetOverruns(users, expenses)
  private val topCategory: Option[(String, BigDecimal)] = mostExpensiveCategory(expenses)

  // Verify expected results for the demonstration data
  private val expectedTotals: Map[(String, String), BigDecimal] = Map(
    ("Alice", "groceries") -> BigDecimal(30),
    ("Alice", "transport") -> BigDecimal(120),
    ("Bob", "groceries")   -> BigDecimal(30),
    ("Bob", "transport")   -> BigDecimal(50),
    ("Carla", "groceries") -> BigDecimal(30),
    ("Carla", "transport") -> BigDecimal(90)
  )

  private val expectedOverruns: Map[String, List[(String, BigDecimal, BigDecimal, BigDecimal)]] = Map(
    "Bob" -> List(
      ("groceries", BigDecimal(30), BigDecimal(10), BigDecimal(20))
    ),
    "Carla" -> List(
      ("groceries", BigDecimal(30), BigDecimal(20), BigDecimal(10))
    )
  )

  private val expectedTopCategory = ("transport", BigDecimal(260))

  assert(
    totals == expectedTotals,
    s"Unexpected totals: $totals"
  )

  assert(
    overruns == expectedOverruns,
    s"Unexpected budget overruns: $overruns"
  )

  assert(
    topCategory.contains(expectedTopCategory),
    s"Unexpected most expensive category: $topCategory"
  )


  /////// Display results ///////
  private def money(amount: BigDecimal): String =
    "$" + amount.setScale(2, BigDecimal.RoundingMode.HALF_UP).toString

  println("=== Personal Finance Tracker ===")

  println("\n1. Total expenses by user and category")
  totals.toList.sortBy(_._1).foreach {
    case ((userName, category), spent) =>
      println(f"  $userName%-8s $category%-12s ${money(spent)}%10s")
  }

  println("\n2. Budget overruns")
  if (overruns.isEmpty) {
    println("  No budget overruns.")
  } else {
    overruns.toList.sortBy(_._1).foreach {
      case (userName, entries) =>
        println(s"  $userName:")
        entries.sortBy(_._1).foreach {
          case (category, spent, limit, overBy) =>
            println(
              s"    $category: spent ${money(spent)}, " +
                s"limit ${money(limit)}, excess ${money(overBy)}"
            )
        }
    }
  }

  println("\n3. Most expensive category")
  topCategory match {
    case Some((category, total)) =>
      println(s"  $category: ${money(total)}")
    case None =>
      println("  No expenses found.")
  }

  println("\nAll demonstration checks passed.")
}
