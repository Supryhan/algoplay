package catslaboratory.kleislilab

import cats.data.State
import cats.implicits.catsSyntaxApplicativeId

object StateMonadProblem extends App {

  type CalcState[A] = State[List[Int], A]

  //  val result = State[List[Int], Int] { oldStack =>
  //    val newStack = someTransformation(oldStack)
  //    val result = someCalculation
  //    (newStack, result)
  //  }

  def evalOne(sym: String): CalcState[Int] =
    sym match {
      case "+" => operator(_ + _)
      case "-" => operator(_ - _)
      case "*" => operator(_ * _)
      case "/" => operator(_ / _)
      case num => operand(num.toInt)
    }

  def evalAll(input: List[String]): CalcState[Int] =
    input
      .foldLeft(0.pure[CalcState]) {
        (a, b) =>
          a.flatMap(_ => evalOne(b))
      }

  def operand(num: Int): CalcState[Int] =
    State[List[Int], Int] {
      stack: List[Int] =>
        (num :: stack, num)
    }

  def operator(func: (Int, Int) => Int): CalcState[Int] =
    State[List[Int], Int] {
      case b :: a :: tail =>
        val ans = func(a, b)
        (ans :: tail, ans)
      case _ => sys.error("Fail!")
    }

  val result: Int = evalOne("42").runA(Nil).value
  println(result)

  val program = for {
    _ <- evalOne("1")
    _ <- evalOne("2")
    ans <- evalOne("+")
  } yield ans

  val programs = for {
    _ <- evalAll(List("1", "2", "+"))
    _ <- evalAll(List("3", "+"))
    ans <- evalAll(List("2", "*"))
  } yield ans

  println(program.runA(Nil).value)
  println(programs.runA(Nil).value)

  def evalInput(input: String): Int = evalAll(input.split(" ").toList).runA(Nil).value
  println(evalInput("1 2 + 3 + 2 * 1 -"))

}
