package core

import cats.implicits._

object MonadLows extends App {

  val intToListDouble = (x: Int) => List(x.toDouble)
  val doubleToListString = (x: Double) => List(x.toString)

  // Monad Laws
  // 1. Left Identity
  // pure(x) >>= f === f(x)
  List(1)
    .flatMap(intToListDouble) == intToListDouble(1)

  // 2. Right Identity
  // m >>= pure === m
  List(1)
    .flatMap {
      e =>
        List(e)
    } == List(1)

  // 3. Associativity
  // m >>= f >>= g === m >>= (x => f(x) >>= g)

  List(1)
    .flatMap(intToListDouble)
    .flatMap(doubleToListString) ==
    List(1)
      .flatMap {
        x =>
          intToListDouble(x)
            .flatMap(doubleToListString)
      }

  List(1)
    .flatMap(intToListDouble)
    .flatMap(doubleToListString) == {
    for {
      x <- List(1)
      y <- intToListDouble(x)
      z <- doubleToListString(y)
    } yield z
  }

}
