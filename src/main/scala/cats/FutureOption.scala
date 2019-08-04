package cats

import cats.data.OptionT
import cats.implicits._

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}

object FutureOption extends App {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  val users = Map(
    1L -> User(1, "K1", Address("Addrr11")),
    2L -> User(1, "K2", Address("Addrr22")),
    3L -> User(1, "K3", Address("Addrr33")))
  val result = Await.result(findAddressByUserId(3L).value, 1.millis)
  result match {
    case Some(address) => println(s"User's address is: ${address.name}")
    case None => println("There was an error")
  }

  def findUserById(id: Long)(implicit e: ExecutionContext): Future[Option[User]] = {
    users(id) match {
      case user: User => Future.successful(Some(user))
      case _ => Future.failed(new Exception("Wrong user's id"))
    }
  }

  def findAddressByUser(user: User)(implicit e: ExecutionContext): Future[Option[Address]] = {
    user.address match {
      case address => Future.successful(Some(address))
      case _ => Future.failed(new Exception(s"Address for user with id = ${user.id} does not exist"))
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
