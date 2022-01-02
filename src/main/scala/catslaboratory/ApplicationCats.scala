package catslaboratory

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits._

object ApplicationCats extends App {
  println("Hello Cats!".toUpperCase)

  val testPositive: Int => IO[Option[Int]] = i =>
    IO {
      if (i > 0)
        Some(1)
      else
        None
    }

  val noneInAnyCase: Int => IO[Option[Int]] = _ => IO(None)

  val combinedOptions1: IO[Option[Int]] = noneInAnyCase(4) <+> IO(Option(3))
  val resNegative: Option[Int] = combinedOptions1.unsafeRunSync() // None
  println(resNegative.fold(0)(identity)) // 0 in case of None

  val combinedOptions2: IO[Option[Int]] = testPositive(4) <+> noneInAnyCase(3)
  val resPositive: Option[Int] = combinedOptions2.unsafeRunSync() // Some
  println(resPositive.fold(0)(identity))
}
