
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Success}
type SimpleService[T] = () => Future[T]
def buggyService(): Future[String] =
  Future {
    println("Access to master")
    Thread.sleep(500)
    if (Math.random() > 0.7) "Done" else sys.error("try again")
  }

def repeater[T](service: SimpleService[T], attempt: Int): Future[T] = {
  for {
    s <- service
  } yield s
}
