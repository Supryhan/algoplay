package catslaboratory

import cats.Monad
import cats.implicits.toFlatMapOps

object RndMonad extends App {

  import scala.util.Random

  case class Rnd[A](run: Random => A)


  implicit val rndMonad: Monad[Rnd] = new Monad[Rnd] {
    override def pure[A](x: A): Rnd[A] = Rnd(_ => x)

    override def flatMap[A, B](fa: Rnd[A])(f: A => Rnd[B]): Rnd[B] =
      Rnd[B] { random: Random =>
        val a: A = fa.run(random)
        val b: B = f(a).run(random)
        b
      }

    override def tailRecM[A, B](a: A)(f: A => Rnd[Either[A, B]]): Rnd[B] = {
      Rnd { random: Random =>
        val e: Random => Either[A, B] = f(a).run
        val e1: Either[A, B] = e(random)
        e1 match {
          case Left(value) => tailRecM(value)(f).run(random)
        }
      }
    }
  }

  val intToRndDouble: Int => Rnd[Double] = (x: Int) => Rnd(_ => x.toDouble)
  val doubleToRndString: Double => Rnd[String] = (x: Double) => Rnd(_ => x.toString)
  val m: Rnd[Int] = Monad[Rnd].pure(1)
  val randomOne = new Random(1)

  // Monad laws:
  // 1. Left id
  assert {
    Monad[Rnd].pure(1)
      .flatMap(intToRndDouble).run(randomOne) == intToRndDouble(1).run(randomOne)
  }
  //  assert(Monad[Rnd].pure(1).flatMap(intToListDouble) == intToListDouble(1))

  // 2. Right id
  assert {
    m.flatMap {
      e =>
        Monad[Rnd].pure(e)
    }.run(randomOne) == m.run(randomOne)
  }

  // 3. Associativity
  assert {
    m
      .flatMap(intToRndDouble)
      .flatMap(doubleToRndString)
      .run(randomOne) ==
      m
        .flatMap {
          x =>
            intToRndDouble(x)
              .flatMap {
                y =>
                  doubleToRndString(y)
              }
        }.run(randomOne)
  }

}
