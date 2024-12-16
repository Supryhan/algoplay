package probls

/**
  * Implements a system to retrieve a user's name based on their user ID using continuation-passing style (CPS).
  * This program handles potential null values gracefully, ensuring robustness against runtime exceptions
  * and provides a safe approach to accessing data that may not exist in the database.
  *
  * Task Description:
  * - The program models users and their information using case classes `User` and `Info`.
  * - Data is mockingly stored in maps representing databases.
  * - Functions are defined to fetch a user by their ID and fetch user information, both using `getOrElse`
  *   to return `null` for non-existent entries.
  * - Continuation-passing style (CPS) is used for managing dependent operations such as fetching user
  *   details and their associated information in a sequential and safe manner.
  * - The `optional` higher-order function provides a safeguard mechanism, allowing continuation functions
  *   to execute only if the input is not `null`, thus avoiding null pointer exceptions.
  * - The `programCSP` function demonstrates fetching a user's name by integrating CPS functions, handling
  *   errors, and utilizing functional programming techniques.
  *
  * Usage:
  * - The program outputs the name associated with a valid user ID and handles invalid IDs by returning `null`.
  * - Demonstrates functional programming practices in Scala, particularly in error handling and data retrieval.
  *
  * Example Output:
  * - Retrieves and prints the name for a valid user ID.
  * - Gracefully returns `null` for an invalid user ID, demonstrating error handling without crashing.
  */
object ProgramCPS extends App {
  type Name = String

  case class User(id: Long)

  case class Info(name: String)

  val users = Map(123L -> User(123), 122L -> User(122))
  val info = Map(123L -> Info("Tom"))

  def getUser(id: Long): User = users.getOrElse(id, null)

  def getInfo(user: User): Info = info.getOrElse(user.id, null)

  def getName(info: Info): Name = info.name

  def getUserCsp[T](id: Long)(k: User => T): T = k(getUser(id))

  def getInfoCsp[T](user: User)(k: Info => T): T = k(getInfo(user))

  def optional[T, U](k: T => U): T => U = {
    v: T => {
      if (v != null) {
        k(v)
      } else {
        null.asInstanceOf[U]
      }
    }
  }

  def programCPS(id: Long): Name = {
    getUserCsp(id) {
      optional { user =>
        getInfoCsp(user) {
          optional { info =>
            getName(info)
          }
        }
      }
    }
  }

  type Error = String
  def getNameByUserId(id: Int): Either[Error, Name] = {
    users
      .get(id)
      .toRight("Unknown user")
      .flatMap(
        user => info
          .get(user.id)
          .toRight("No user info")
          .map(info => info.name)
      )
  }

  def eitherToString(in: Either[Error, Name]): String = in.fold(identity, identity)

  println(s"Vanilla scala solution when user & info exist: ${eitherToString(getNameByUserId(123))}")
  println(s"Vanilla scala solution when no user exist: ${eitherToString(getNameByUserId(121))}")
  println(s"Vanilla scala solution when user exist bun no info: ${eitherToString(getNameByUserId(122))}")
  println(programCPS(123))
  println(programCPS(1234))


}