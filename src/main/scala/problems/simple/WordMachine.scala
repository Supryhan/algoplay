package problems.simple

import cats.data.EitherT
import cats.implicits._
import cats.effect.IO
import scala.util.matching.Regex

object WordMachine {

  private val MIN = 0
  private val MAX = 0x7FFF_FFFF
  private val NumericPattern: Regex = "\\d+".r

  type Stack = List[Int]
  type Result[T] = Either[String, T]

  def main(args: Array[String]): Unit = {
    val wm = new WordMachine
    val result = wm.process("1 1 + 2 + DUP +")
    println(result)
  }

  class WordMachine {

    def process(input: String): Result[Int] =
      input
        .split(" ")
        .toList
        .foldM(
          List.empty[Int]
        )(
          (stack, token) => apply(stack, token)
        ).flatMap(pop).map(_._1)

    private def apply(stack: Stack, token: String): Result[Stack] = token match {
      case NumericPattern() => push(stack, token.toInt)
      case "POP" => pop(stack).map(_._2)
      case "DUP" => dup(stack)
      case "+" => add(stack)
      case "-" => sub(stack)
      case _ => Left(s"Unknown operation: $token")
    }

    private def push(stack: Stack, value: Int): Result[Stack] = {
      withinRange(value).map(validValue => validValue :: stack)
    }

    private def pop(stack: Stack): Result[(Int, Stack)] = {
      stack match {
        case head :: tail => Right((head, tail))
        case Nil => Left("Too few elements are available")
      }
    }

    private def dup(stack: Stack): Result[Stack] = {
      stack match {
        case head :: _ => Right(head :: stack)
        case Nil => Left("Too few elements are available")
      }
    }

    private def add(stack: Stack): Result[Stack] = {
      stack match {
        case a :: b :: tail => push(tail, a + b)
        case _ => Left("Too few elements are available")
      }
    }

    private def sub(stack: Stack): Result[Stack] = {
      stack match {
        case a :: b :: tail => push(tail, b - a)
        case _ => Left("Too few elements are available")
      }
    }

    private def withinRange(value: Int): Result[Int] = {
      if (value < MIN || value > MAX) Left("Number is out of integer range")
      else Right(value)
    }
  }
}
