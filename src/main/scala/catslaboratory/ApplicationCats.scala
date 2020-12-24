package catslaboratory

import cats._
import cats.effect.IO
import cats.implicits._

object ApplicationCats extends App {
  println("Hello Cats!".toUpperCase)

  val f1: Int => IO[Option[Int]] = i => IO(Option(1))
  val f2: Int => IO[Option[Int]] = i => IO(None)

  val res: IO[Option[Int]] = f2(4) <+> IO(Option(3))
  val r: Option[Int] = res.unsafeRunSync()
  print(r.fold(0)(identity))

}
