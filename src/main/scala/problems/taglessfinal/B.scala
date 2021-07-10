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

  case class UserId(value: Int)

  trait UserRepo[F[_]] {
    def getUser(userId: Option[Int]): F[UserId]
  }

  object UserRepo {
    def apply[F[_]](implicit UserR: UserRepo[F]): UserRepo[F] = UserR
  }
  def getUser[F[_]: UserRepo](userId: Option[Int]): F[UserId] =
    UserRepo[F].getUser(userId)

  implicit object OptUserRepo extends UserRepo[Option] {
    def getUser(userId: Option[Int]): Option[UserId] = Some(UserId(1331))
  }
  implicit object ListUserRepo extends UserRepo[List] {
    def getUser(userId: Option[Int]): List[UserId] = List(UserId(1331))
  }
//  println(UserRepo[Option].getUser(Some(11)))
  val res: Option[UserId] = getUser[Option](Some(11))
  val res2: List[UserId] = getUser[List](Some(11))
  println(res)

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
