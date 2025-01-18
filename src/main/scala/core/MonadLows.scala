package core

import cats.Monad
import cats.instances.list._

object MonadLows extends App {

  val intToListDouble: Int => List[Double] = (x: Int) => List(x.toDouble)
  val doubleToListString: Double => List[String] = (x: Double) => List(x.toString)
  val m: List[Int] = Monad[List].pure(1)

  // Monad Laws
  // 1. Left Identity
  // pure(x) >>= f === f(x)
  Monad[List].pure(1)
    .flatMap(intToListDouble) == intToListDouble(1)

  // 2. Right Identity
  // m >>= pure === m
  m.flatMap {
    e =>
      Monad[List].pure(e)
  } == m

  // 3. Associativity
  // m >>= f >>= g === m >>= (x => f(x) >>= g)

  m
    .flatMap(intToListDouble)
    .flatMap(doubleToListString) ==
    m
      .flatMap {
        x =>
          intToListDouble(x)
            .flatMap(doubleToListString)
      }

  m
    .flatMap(intToListDouble)
    .flatMap(doubleToListString) == {
    for {
      x <- m
      y <- intToListDouble(x)
      z <- doubleToListString(y)
    } yield z
  }

}
