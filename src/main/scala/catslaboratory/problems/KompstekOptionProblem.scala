package catslaboratory.problems

import cats.effect._
import cats.implicits._
import cats.effect.unsafe.implicits.global

object KompstekOptionProblem extends App {

  case class Result1(value: String)
  case class Result2(value: String)

  def toListResult(str: String): IO[List[Result1]] = List(Result1(str), Result1(str), Result1("")).pure[IO]

  def resultToOptionResult(result: Result1): IO[Option[Result2]] =  Result2(result.value).some.pure[IO]

  def solution(input: String): IO[List[Result2]] = {
    (for {
      e <- toListResult(input)
      ee: List[IO[Option[Result2]]] = e.map(result1 => resultToOptionResult(result1))
      s: IO[List[Option[Result2]]] = ee.sequence
      eee: IO[List[Result2]] = s.map(_.map(_.fold(Result2(""))(c => c)))
    } yield eee).flatten
  }

  val list: List[Result2] = solution("1234").unsafeRunSync()
  list.foreach(println(_))
}
