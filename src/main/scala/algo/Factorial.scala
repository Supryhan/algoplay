package algo

import scala.annotation.tailrec

object Factorial extends App {

  println(factorial(5))
  println(factorial2(5))

  // 1 - basic recursive factorial method
  def factorial(n: Int): Int = {
    n match {
      case 0 => 1
      case _ => n * factorial(n - 1)
    }
  }

  // 2 - tail-recursive factorial method
  def factorial2(n: Long): Long = {
    @tailrec
    def factorialAccumulator(acc: Long, n: Long): Long = {
      n match {
        case 0 => acc
        case _ => factorialAccumulator(n * acc, n - 1)
      }
    }

    factorialAccumulator(1, n)
  }

}