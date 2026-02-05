package catslaboratory.hoftypepractice

object MonadPractice extends App {

  /*
  Requirements:
  - if the host and port are found in the configuration map, then we'll return a M containing a connection
    with those values otherwise the method will fail, according to the logic of the type M (for Try it will
    return a Failure, for Option it will return None, for Future it will be a failed Future, for Either it
    will be a Left)
  - the issueRequest method returns a M containing the string: "request (payload) has been accepted", if the
    payload is cond than 20 characters otherwise the method will fail, according to the logic of the type M
  */

  case class Connection(host: String, port: String)

  type ErrorOr[T] = Either[Throwable, T]

  val config = Map(
    "host" -> "localhost",
    "port" -> "4040"
  )

  trait HttpService[M[_]] {
    def getConnection(cfg: Map[String, String]): M[Connection]

    def issueRequest(connection: Connection, payload: String): M[String]
  }

  private class HttpServiceOption extends HttpService[Option] {
    def getConnection(cfg: Map[String, String]): Option[Connection] =
      for {
        host <- cfg.get("host")
        port <- cfg.get("port")
      } yield Connection(host, port)

    def issueRequest(connection: Connection, payload: String): Option[String] = {
      if (payload.length <= 20) {
        Some(s"request (${payload}) has been accepted")
      } else {
        None
      }
    }
  }

  private val serviceOption = new HttpServiceOption()
  private val result1: Option[String] = serviceOption
    .getConnection(config)
    .flatMap(connection => serviceOption.issueRequest(connection, "hello world"))
  println(s"HttpServiceOption: $result1")


  private class HttpServiceError extends HttpService[ErrorOr] {
    def getConnection(cfg: Map[String, String]): ErrorOr[Connection] =
      (for {
        host <- cfg.get("host")
        port <- cfg.get("port")
      } yield Connection(host, port)).toRight(new IllegalArgumentException("Wrong config"))

    def issueRequest(connection: Connection, payload: String): ErrorOr[String] = {
      if (payload.length <= 20) {
        Right(s"request (  ${payload}) has been accepted")
      } else {
        Left(new IllegalArgumentException("Wrong config"))
      }
    }
  }

  private val serviceError = new HttpServiceError()
  private val result2: ErrorOr[String] = serviceError
    .getConnection(config)
    .flatMap(connection => serviceError.issueRequest(connection, "hello world"))
  println(s"HttpServiceError: $result2")


  type Ei[T] = Either[String, T]

//  import cats.instances.either._
  import cats.Monad

  val intMonad = Monad[Ei]
  val ei: Ei[Int] = intMonad.pure(42)
  ei.flatMap(i => Right(i + 1))

  import cats.syntax.flatMap._

  ei.flatMap(i => Right(i + 2))




}



