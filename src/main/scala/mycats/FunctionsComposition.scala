package mycats

import cats.instances.function._
import cats.syntax.functor._

object FunctionsComposition extends App {
  val func1: Int => Double = (x: Int) => x.toDouble
  val func2: Double => Double = (y: Double) => y * 2

  val result1 = (func1 map func2) (1) // composition using map
  val result2 = (func1 andThen func2) (1) // composition using andThen
  val result3 = func2(func1(1)) // composition written out by hand

  println(result1)
  println(result2)
  println(result3)
}
