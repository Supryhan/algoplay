package catslaboratory.problems

import cats.effect._
import cats.implicits._
import cats.effect.unsafe.implicits.global

object KompstekOptionProblem extends App {

  case class Result1(value: String)
  case class Result2(value: String)

  def toListResult(str: String): IO[List[Result1]] = List(Result1(str), Result1(str), Result1("")).pure[IO]

  def resultToOptionResult(result: Result1): IO[Option[Result2]] = Result2(result.value).some.pure[IO]

  def solution(input: String): IO[List[Result2]] =
    for {
      listR1 <- toListResult(input)
      listOptR2 <- listR1.map(resultToOptionResult).sequence
    } yield listOptR2.map(_.fold(Result2(""))(identity)).filterNot(_ == Result2(""))

  val list: List[Result2] = solution("1234").unsafeRunSync()
  println(list)
  println(s"case 1 - List is not empty: ${list.nonEmpty}")

  val listEmpty: List[Result2] = solution("").unsafeRunSync()
  println(s"case 2 - List is empty: ${listEmpty.isEmpty}")

}
