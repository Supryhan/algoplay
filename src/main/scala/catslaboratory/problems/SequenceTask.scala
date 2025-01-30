package catslaboratory.problems

import cats.effect.{IO, IOApp}
import cats.implicits._

object SequenceTask extends IOApp.Simple {

  def cov(s: String): IO[List[String]] = IO(List(s))

  private val a: List[IO[String]] = List(IO("String 1;"), IO("String 2;"), IO("String 3;"))
  private val b: IO[List[String]] = a.sequence

  override def run: IO[Unit] = {
    b.flatMap {
      b1 =>
        IO(println(b1.mkString(" ")))
    }
  }
}
