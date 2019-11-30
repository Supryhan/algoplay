package mycats

object PrimitivesInCats extends App {

  import scala.language.higherKinds
  import cats.Monad
  import cats.syntax.functor._
  import cats.syntax.flatMap._
  type D[A] = A

  def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
      x <- a
      y <- b
    } yield x * x + y * y

  import cats.instances.option._
  import cats.instances.list._

  println(sumSquare(Option(2), Option(5)))
  println(sumSquare(List(2), List(5, 6)))
  println(sumSquare(3: D[Int], 4: D[Int]))

  import cats.syntax.either._

  def countPositive(nums: List[Int]) =
    nums.foldLeft(0.asRight[String]) { (accumulator, num) =>
      if (num > 0) {
        accumulator.map(_ + 1)
      } else {
        Left("Negative. Stopping!")
      }
    }

  println(countPositive(List(1, 2, 3))) // Right(3)
  println(countPositive(List(1, -2, 3))) // Left(Negative. Stopping!)

  println(Either.fromTry(scala.util.Try("foo".toInt)))
  //Left(java.lang.NumberFormatException: For input string: "foo")
  println(Either.fromOption[String, Int](None, "Badness"))
  //Left(Badness)
}