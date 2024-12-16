package problemslab.future

import cats.{Applicative, Functor}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object FutureComposition extends App {
  val i: Future[Option[Int]] = Future.successful(Functor[Option].widen(Some(1))) // widen is just for fun
  val c: Future[Option[Char]] = Future.successful(Some('a'))

  val composedApplicative: Applicative[({type λ[α] = Future[Option[α]]})#λ] = Applicative[Future].compose[Option]
  val result: Future[Option[Int]] = composedApplicative.map2(i, c)((i, c) => i + c)

  result onComplete {
    case Success(value) => println(s"Result: $value")
    case Failure(exception) => println(s"Exception: ${exception.getMessage}")
  }

  type FutureOption[A] = Future[Option[A]]
  val composedApplicativeAlternative: Applicative[FutureOption] = Applicative[Future].compose[Option]
  composedApplicativeAlternative.map(Future.successful(Some(1)))(identity)

}
