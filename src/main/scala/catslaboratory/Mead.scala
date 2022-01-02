package catslaboratory

import cats.data.OptionT
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits._

object Mead extends App {

  case class Mead(name: String, honeyRatio: Double, agingYears: Double)

  type Query[T] = IO[Option[T]]

  def defineMead(qName: Query[String],
                 qHoneyRatio: Query[Double],
                 qAgingYears: Query[Double]): Query[Mead] =
    (for {
      name       <- OptionT(qName)
      honeyRatio <- OptionT(qHoneyRatio)
      agingYears <- OptionT(qAgingYears)
    } yield Mead(name, honeyRatio, agingYears)).value

  def defineMead2(qName: Query[String],
                  qHoneyRatio: Query[Double],
                  qAgingYears: Query[Double]): Query[Mead] =
    for {
      name       <- qName
      honeyRatio <- qHoneyRatio
      agingYears <- qAgingYears
    } yield (name, honeyRatio, agingYears).mapN(Mead)

  println(defineMead(IO("hello".some), IO(Option(0.1)), IO(Option(1.0))).unsafeRunSync().fold("error")(_.toString))
  println(defineMead2(IO("hello".some), IO(Option(0.1)), IO(Option(1.0))).unsafeRunSync().fold("error")(_.toString))

}
