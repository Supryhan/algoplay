package problems

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
import com.typesafe.scalalogging.{LazyLogging, Logger}

object FutureInterviewProblem extends LazyLogging {

  type SimpleService[T] = () => Future[T]

  def repeater[T](service: SimpleService[T], attempt: Int): Future[T] = {
    val r = service()
    r onComplete {
      case Success(value) => {logger.info(s"Success, attempt:$attempt"); value}
      case Failure(exception) if attempt > 1 => {logger.info(s"Failure, up, attempt:$attempt"); repeater(service, attempt - 1)}
      case Failure(exception) => {logger.info(s"Failure, exception, attempt:$attempt"); exception}
    }
    r
  }

  def main(args: Array[String]): Unit = {
    def buggyService(): Future[String] =
      Future {
        println("Access to master")
        Thread.sleep(500)
        if (Math.random() > 0.7) "Done" else sys.error("try again")
      }

    repeater(buggyService _, 5) onComplete {
      case Success(value) => logger.info(value)
      case Failure(exception) => logger.info(exception.getMessage)
    }
    Thread.sleep(2500)
  }
}
