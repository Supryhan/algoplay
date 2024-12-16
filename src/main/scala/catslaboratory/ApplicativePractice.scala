package catslaboratory

import cats.implicits.catsSyntaxTuple3Semigroupal

object ApplicativePractice extends App {
  import cats.instances.option._   // for Applicative instance for Option

  case class Connection(username: String, password: String, url: String)

  val username: Option[String] = Some("username")
  val password: Option[String] = Some("password")
  val url: Option[String] = Some("some.login.url.here")

  def connect(username: String, password: String, url: String): Option[Connection] =
    Some(Connection(username, password, url))

  val connection: Option[Connection] = (username, password, url).mapN(connect).flatten

}
