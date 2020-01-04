package problems

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Success, Failure}

object FutureInterviewProblem {

  type SimpleService[T] = () => Future[T]

  def repeater[T](service: SimpleService[T], attempt: Int): Future[T] = {
    println(s"Attempt:$attempt")
    val r = service()
    r onComplete {
      case Success(value) => value
      case Failure(exception) if attempt > 1 => repeater(service, attempt - 1)
      case Failure(exception) => exception
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
      case Success(value) => print(value)
      case Failure(exception) => print(exception)
    }
    Thread.sleep(2500)
  }
}
