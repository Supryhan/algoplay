package cats

import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.concurrent.duration._

object FutureOptionManualMonadTransformer extends App {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  def findUserById(id: Long): Future[Option[User]] = Future.successful(Some(new User))

  def findAddressByUser(user: User) = Future.successful(Some(new Address("blvd")))

  def findAddressByUserId(id: Long): Future[Option[Address]] =
    (for {
      user <- FutOpt(findUserById(id))
      address <- FutOpt(findAddressByUser(user))
    } yield address).value

  def findAddressByUserId2(id: Long): Future[Option[Address]] =
    FutOpt(findUserById(id))
      .flatMap(user =>
        FutOpt(findAddressByUser(user))
          .map(address => address))
      .value

  val result = Await.result(findAddressByUserId2(13L), 1.millis)
  result match {
    case Some(address) => print(address.name)
    case None => print("No address found")
  }

  case class FutOpt[A](value: Future[Option[A]]) {

    def map[B](f: A => B): FutOpt[B] =
      FutOpt(value.map(optA => optA.map(f)))

    def flatMap[B](f: A => FutOpt[B]): FutOpt[B] =
      FutOpt(value.flatMap {
        case Some(a) => f(a).value
        case None => Future.successful(None)
      })
  }

  case class Address(name: String)

  case class User()

}