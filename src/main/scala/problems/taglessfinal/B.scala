package problems.taglessfinal

object B extends App {
  //Task1
  def task1(n: Int): String = {
    var res = ""
    for (_ <- 0 until n) {
      res = res + "K"
    }
    res
  }
  println(task1(12))

  type Error = String
  type EitherError[A] = Either[Error, A]

  val userStorage: Set[Int] = (1 to 99).toSet

  case class UserId(value: Int)

  trait UserRepo[F[_]] {
    def getUser(id: Int): F[UserId]
  }

  object UserRepo {
    def apply[F[_]](implicit UserR: UserRepo[F]): UserRepo[F] = UserR
  }
  def getUser[F[_]: UserRepo](id: Int): F[UserId] =
    UserRepo[F].getUser(id)

  implicit object OptUserRepo extends UserRepo[Option] {
    def getUser(id: Int): Option[UserId] = Option.when(userStorage.contains(id))(UserId(id))
  }
  implicit object ListUserRepo extends UserRepo[List] {
    def getUser(id: Int): List[UserId] = Option.when(userStorage(id))(UserId(id)).toList
  }
  implicit object EitherUserRepo extends UserRepo[EitherError] {
    def getUser(id: Int): EitherError[UserId] = Either.cond(userStorage(id), UserId(id), "Can't find user in repo.")
  }
//  println(UserRepo[Option].getUser(11))
  val res1: Option[UserId] = getUser[Option](11) // Some(UserId(11))
  val res2: List[UserId] = getUser[List](100) // List()
  val res3: EitherError[UserId] = getUser[EitherError](0) // Can't find user in repo.
  res1.foreach(println)
  res2.foreach(println)
  res3.fold(e => println(e), r => println(r))

}

object C extends App {
  class Color
  case object Red extends Color
  case object Green extends Color
  case object Blue extends Color

  def task(value: Color): Option[Color] = {
    value match {
      case Red => Some(value)
      case Blue => Some(value)
      case Green => Some(value)
      case _ => None
    }
  }
  println(task(Red).getOrElse("error"))
}
