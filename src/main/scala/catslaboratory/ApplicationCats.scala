package catslaboratory

import cats._
import cats.effect.IO
import cats.implicits._

object ApplicationCats extends App {
  println("Hello Cats!".toUpperCase)

  val res: IO[Option[Int]] = IO(Option(3)) <+> IO(None)
  val r: Option[Int] = res.unsafeRunSync()
  print(r.fold(0)(identity))

}
