package cats

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

object FutureOptionManualMonadTransformer extends App {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  def findUserById(id: Long): Future[Option[User]] = Future.successful(Some(new User))

  def findAddressByUser(user: User) = Future.successful(Some(new Address))

  def findAddressByUserId(id: Long): Future[Option[Address]] =
    (for {
      user <- FutOpt(findUserById(id))
      address <- FutOpt(findAddressByUser(user))
    } yield address).value

  case class FutOpt[A](value: Future[Option[A]]) {

    def map[B](f: A => B): FutOpt[B] =
      FutOpt(value.map(optA => optA.map(f)))

    def flatMap[B](f: A => FutOpt[B]): FutOpt[B] =
      FutOpt(value.flatMap(opt => opt match {
        case Some(a) => f(a).value
        case None => Future.successful(None)
      }))
  }

  case class Address()

  case class User()

}