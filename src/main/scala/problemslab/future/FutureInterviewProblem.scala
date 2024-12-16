package problemslab.future

import cats.Monoid
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success

object FutureInterviewProblem extends LazyLogging {

  type SimpleService[T] = () => Future[T]

  def repeater[T: Monoid](service: SimpleService[T], attempt: Int): Future[T] = {
    def attemptExecution(remainingAttempts: Int): Future[T] = {
      if (remainingAttempts <= 0) {
        Future.successful(Monoid[T].empty)
      } else {
        service().transformWith {
          case Success(value) => Future.successful(value)
          case Failure(_) => attemptExecution(remainingAttempts - 1)
        }
      }
    }

    attemptExecution(attempt)
  }

  def main(args: Array[String]): Unit = {
    def buggyService(): Future[String] =
      Future {
        println("Access to master")
        Thread.sleep(500)
        if (Math.random() > 0.7) "Done" else {val e: Nothing = sys.error("try again"); e}
      }

    val result = repeater(buggyService _, 5)
    Await.result(result, 5.second)
    result.foreach(println(_))
  }
}
