package problems

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object FutureInterviewProblem {

  type SimpleService[T] = () => Future[T]

  def repeater[T](service: SimpleService[T], attempt: Int): Future[T] = ???

  def main(args: Array[String]): Unit = {
    def buggyService(): Future[String] =
      Future {
        println("Access to master")
        Thread.sleep(500)
        if (Math.random() > 0.7) "Done" else sys.error("try again")
      }

    repeater(buggyService _, 5)
  }
}
