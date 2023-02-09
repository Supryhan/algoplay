package servers.http4s

import cats.data.Kleisli
import cats.effect._
import com.comcast.ip4s._
import org.http4s.{Http, HttpApp, HttpRoutes, Request, Response}
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.ember.server._

object HelloWorldHttp4sServer extends IOApp {

  val helloWorldService: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }//.orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(helloWorldService.orNotFound)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
}
