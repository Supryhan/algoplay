package catslaboratory

import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.concurrent.duration._

object FutureOptionManualMonadTransformer extends App {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  def findUserById(id: Long): Future[Option[User]] = Future.successful(Some(new User))

  def findAddressByUser(user: User): Future[Option[Address]] = Future.successful(Some(Address("blvd")))

  def findAddressByUserId(id: Long): Future[Option[Address]] =
    (for {
      user <- FutOpt(findUserById(id))
      address <- FutOpt(findAddressByUser(user))
    } yield address).value

  def findAddressByUserId2(id: Long): Future[Option[Address]] =
    FutOpt(findUserById(id))
      .flatMap(user =>
        FutOpt(findAddressByUser(user))
          .map(identity))
      .value

  val result = Await.result(findAddressByUserId2(13L), 1.millis)
  result match {
    case Some(address) => print(address.name)
    case None => print("No address found")
  }

  case class FutOpt[A](value: Future[Option[A]]) {
    def pure[S](value: Future[Option[S]]): FutOpt[S] = FutOpt(value)
    def map[B](f: A => B): FutOpt[B] = FutOpt(value.map(_.map(f)))
    def flatMap[B](f: A => FutOpt[B]): FutOpt[B] =
      pure(value.flatMap {
        case Some(a) => f(a).value
        case None => Future.successful(None)
      })
  }

  case class Address(name: String)

  case class User()

}