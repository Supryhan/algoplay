package algo

import scala.annotation.tailrec

object FibonacciTailRecursive extends App {

  println(8.fib)//1 1 2 3 5 8 13 21 34 55 89 144


  implicit class Fib(value: Int) {
  def fib: BigInt = {
    @tailrec def fibHelper(x: Int, prev: BigInt = 0, next: BigInt = 1): BigInt = x match {
      case 0 => prev
      case 1 => next
      case _ => fibHelper(x - 1, next, next + prev)
    }
    fibHelper(value)
  }}

}
