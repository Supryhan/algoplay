package problems.future

import cats.Monoid
import cats.instances.string._
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object FutureInterviewProblem extends LazyLogging {

  type SimpleService[T] = () => Future[T]

  def repeater[T: Monoid](service: SimpleService[T], attempt: Int): Future[T] =
    if (attempt == 1)
      service().recoverWith { case _: Exception => Future.successful(Monoid[T].empty)}
    else
      service().recoverWith { case e: Exception => repeater(service, attempt - 1) }

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
