package mycats

object PrimitivInCats extends App {

  import scala.language.higherKinds
  import cats.Monad
  import cats.syntax.functor._ // for map
  import cats.syntax.flatMap._ // for flatMap

  def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
      x <- a
      y <- b
    } yield x * x + y * y

  import cats.instances.option._ // for Monad
  import cats.instances.list._ // for Monad

  println(sumSquare(Option(2), Option(5)))
  println(sumSquare(List(2), List(5, 6)))

  implicit def IntOption(value: Int): Option[Int] = {
    value match {
      case i: Int => Some(i)
    }
  }

  println(sumSquare(1: cats.Id[Int], 2: cats.Id[Int]))
}

class Id[A] {
  type Id[A] = A

  def pure[A](a: A): Id[A] = a

  def map[B](a: Id[A])(f: A => B): Id[B] = f(a)

  def flatMap[B](a: Id[A])(f: A => Id[B]): Id[B] = f(a)
}
