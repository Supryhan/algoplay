package catslaboratory.problems

import cats.effect._
import cats.effect.unsafe.implicits.global


object KompstekOptionProblem extends App {

  def strToList(str: String): IO[List[String]] = IO {
    str.toList.map(c => c.toString)
  }

  def strToInt(list: List[String]): IO[List[Int]] = IO {
    list.map(s => s.toInt)
  }

  def solution(input: String): IO[List[Int]] = for {
    e <- strToList(input)
    ee <- strToInt(e)
  } yield ee

  val result: List[Int] = solution("1234").unsafeRunSync()
  result.foreach(println(_))
}
