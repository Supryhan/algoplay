package catslaboratory.kleislilab

import cats.data.Kleisli
import cats.effect.IO
import cats.implicits.catsSyntaxTuple2Semigroupal

object SemigroupKleisli extends App {
  type KleisliIO[A, B] = Kleisli[IO, A, B]

  val oneK: Kleisli[IO, Int, Int] = Kleisli((a: Int) => IO(a))
  val twoK: Kleisli[IO, Int, Int] = Kleisli((b: Int) => IO(b))
  val threeK: Kleisli[IO, Int, Int] = (oneK, twoK).mapN((a: Int, b: Int) => a + b)

  val oneListStringK: Kleisli[IO, Int, List[String]] = Kleisli((a: Int) => IO(List(a.toString)))
  val twoListStringK: Kleisli[IO, Int, List[String]] = Kleisli((b: Int) => IO(List(b.toString)))
  val threeListStringK: Kleisli[IO, Int, List[String]] = (oneListStringK, twoListStringK)
    .mapN((a: List[String], b: List[String]) => a ++ b)

  val oneListIntK: Kleisli[IO, Int, List[Int]] = Kleisli((a: Int) => IO(List(a)))
  val twoListBooleanK: Kleisli[IO, Int, List[Boolean]] = Kleisli((b: Int) => IO(List(true)))
  val threeListK: Kleisli[IO, Int, List[String]] = (oneListIntK, twoListBooleanK)
    .mapN((a: List[Int], b: List[Boolean]) => List(a.size + b.size).map(_.toString))

  val oneListStringK2: Kleisli[IO, Int, List[String]] = Kleisli((a: Int) => IO(List(a.toString, a.toString)))
  val twoMapStringIntK: Kleisli[IO, Int, Map[String, Int]] = Kleisli((b: Int) => IO(Map("true" -> 1)))
  val threeListMapK: Kleisli[IO, Int, List[String]] = (oneListStringK2, twoMapStringIntK)
    .mapN((a: List[String], b: Map[String, Int]) => List(a.size + b.size).map(_.toString))
}
