package cats

import cats.data.OptionT
import cats.implicits._
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

object FutureOptionTry extends App {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  println(s"User address is: %s", findAddressByUserId(13).value)


  def findUserById(id: Long)(implicit e: ExecutionContext): Future[Option[User]] = Future {
    Some(User("K3"))
  }

  def findAddressByUser(user: User)(implicit e: ExecutionContext): Future[Option[Address]] = Future {
    Some(Address("Addrr13"))
  }

  def findAddressByUserId(id: Long)(implicit e: ExecutionContext): OptionT[Future, Address] =
    for {
      user <- OptionT(findUserById(id))
      address <- OptionT(findAddressByUser(user))
    } yield address

  case class User(name: String)

  case class Address(name: String)

}
