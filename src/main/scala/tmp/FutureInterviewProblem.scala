package tmp

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}

object FutureInterviewProblem {

  type SimpleService[T] = () => Future[T]

    //  def repeater[T](service: SimpleService[T], attempt: Int): Future[T] = {
    //    service() recoverWith { case _ if attempt > 0 => println(s"att:${attempt}");repeater(service, attempt-1) }

  def repeater[T](service: SimpleService[T], attempt: Int): Future[T] = ??? //{

//     if((service() result 1.second) != "Done") repeater(service, attempt - 1)
//  }

  def main(args: Array[String]): Unit = {
    def buggyService(): Future[String] =
      Future {
        println("Access to master")
        Thread.sleep(500)
        if (Math.random() > 0.7) "Done" else sys.error("try again")
      }
//    repeater(buggyService _, 5)
        repeater(buggyService _, 5).onComplete {
          case Success(v) => print("result:s:" + v)
          case Failure(e) => print("result:f:" + e)
        }
  }
}
