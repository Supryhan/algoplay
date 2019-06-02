package cats

import cats.data.OptionT
import cats.implicits._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object FutureOptionTry extends App {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  val result = Await.result(findAddressByUserId(13).value, 1.millis)
  result match {
    case Some(value) => println(s"User address is: $value")
    case None => println("There was an error")
  }

  def findUserById(id: Long)(implicit e: ExecutionContext): Future[Option[User]] = Future.successful(Some(User("K3")))

  def findAddressByUser(user: User)(implicit e: ExecutionContext): Future[Option[Address]] = Future.successful(Some(Address("Addrr13")))

  def findAddressByUserId(id: Long)(implicit e: ExecutionContext): OptionT[Future, Address] =
    for {
      user <- OptionT(findUserById(id))
      address <- OptionT(findAddressByUser(user))
    } yield address

  case class User(name: String)

  case class Address(name: String)

}
