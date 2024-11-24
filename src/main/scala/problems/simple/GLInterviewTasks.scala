package problems.simple

import scala.util.Try

object GLInterviewTasks extends App {


  def sum(a: Option[Int], b: Option[Int]): Option[Int] = {
    for {
      a1 <- a
      b1 <- b
    } yield a1 + b1
  }

  println(s"Result: ${sum(None, Some(1)) == None}")
  println(s"Result: ${sum(Some(2), None) == None}")
  println(s"Result: ${sum(Some(2), Some(3)) == Some(5)}")

  def multiply(str: String): Int = {
    def toOptionInt(arg: String): Option[Int] = {
      try {
        Some(arg.toInt)
      } catch {
        case e: NumberFormatException => None
      }
    }


    val numbers: Array[Option[Int]] = str.split(",").map(toOptionInt)

    val validNumbers: Array[Int] = numbers.flatten
    val res = validNumbers.product
    res
  }

  val mp = multiply("2,4,string,-5") //result = -40
  println(s"Result: ${mp == -40}")

  def toEitherInt(arg: String): Either[String, Int] =
    Try(arg.toInt).toEither.left.map(t => s"Couldn't parse $arg, because '${t.getMessage}'")

  println(s"Result: ${toEitherInt("42") == Right(42)}")
  println(s"Result: ${toEitherInt("m42") == Left("""Couldn't parse m42, because 'For input string: "m42"'""")}")
}
