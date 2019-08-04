package cats

import cats.data.OptionT
import cats.implicits._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object FutureOption extends App {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  val result = Await.result(findAddressByUserId(13).value, 1.millis)
  result match {
    case Some(value) => println(s"User address is: $value")
    case None => println("There was an error")
  }

  def findUserById(id: Long)(implicit e: ExecutionContext): Future[Option[User]] = {
    id match {
      case 13 => Future.successful(Some(User(13, "K3", Address("Addrr13"))))
      case _ => Future.failed(new Exception("wrong user id"))
    }
  }

  def findAddressByUser(user: User)(implicit e: ExecutionContext): Future[Option[Address]] = {
    user.id match {
      case 13 => Future.successful(Some(Address("Addrr13")))
      case _ => Future.failed(new Exception(s"address for user id = ${user.id} does not exist"))
    }

  }

  def findAddressByUserId(id: Long)(implicit e: ExecutionContext): OptionT[Future, Address] =
    for {
      user <- OptionT(findUserById(id))
      address <- OptionT(findAddressByUser(user))
    } yield address

  case class User(id: Long, name: String, address: Address)

  case class Address(name: String)

}
