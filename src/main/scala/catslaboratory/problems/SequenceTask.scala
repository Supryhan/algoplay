package catslaboratory.problems

import cats.Traverse
import cats.effect.unsafe.implicits.global
import cats.effect.{IO, IOApp}
import cats.implicits._

import scala.collection.immutable.List

object SequenceTask extends IOApp.Simple {

  def cov(s: String): IO[List[String]] = IO(List(s))

  private val a: List[IO[String]] = List(IO("String 1;"), IO("String 2;"), IO("String 3;"))
  private val b: IO[List[String]] = a.sequence

  override def run: IO[Unit] = {
    b.flatMap {
      b1 =>
        IO(println(b1.mkString(" ")))
    }

    val travers: Traverse[List] = Traverse[List]
    val list: List[Int] = List(1, 2, 3)

    def intToIo(n: Int): IO[Int] = {
      println(n)
      IO(n)
    }

    val result1: IO[List[Int]] = travers.traverse(list)(intToIo)
    val r1: List[Int] = result1.unsafeRunSync()
    println(r1.mkString)

    def mySequence[A](list: List[IO[A]]): IO[List[A]] =
      travers.traverse(list)(identity)

    val result2: IO[List[String]] = mySequence(a)
    val r2: List[String] = result2.unsafeRunSync()
    println(r2.mkString)

    IO()
  }

}
