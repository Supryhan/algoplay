package problems

object ProgramCSP extends App {

  case class User(id: Long)

  case class Info(name: String)

  val users = Map(123L -> User(123))
  val info = Map(123L -> Info("Tom"))

  def getUser(id: Long): User = {
    users.getOrElse(id, null)
  }

  def getInfo(user: User): Info = {
    info.getOrElse(user.id, null)
  }

  def getUserCsp[T](id: Long)(k: User => T): T = {
    k(getUser(id))
  }

  def getInfoCsp[T](user: User)(k: Info => T): T = {
    k(getInfo(user))
  }

  def optional[T, U](k: T => U): T => U = {
    v: T => {
      if (v != null) {
        k(v)
      } else {
        null.asInstanceOf[U]
      }
    }
  }

  def programCSP(id: Long): Info = {
    getUserCsp(id) {
      optional { user =>
        getInfoCsp(user) {
          optional(identity)
        }
      }
    }
  }

  println(programCSP(123))

//  println(programCSP(1234))
}