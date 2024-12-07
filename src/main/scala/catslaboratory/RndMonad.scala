package catslaboratory

import cats.Monad

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
      val e: Random => Either[A, B] = f(a).run
      Rnd { random: Random =>
        val e1: Either[A, B] = e(random)
        e1 match {
          case Left(value) => tailRecM(value)(f).run(random)
          case Right(value) => value
        }
      }
    }
  }

}
