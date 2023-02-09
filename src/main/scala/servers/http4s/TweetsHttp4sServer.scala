package servers.http4s

import cats.Applicative
import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import cats.syntax.all._
import com.comcast.ip4s._
import org.http4s.ember.server._
import org.http4s.implicits._
import org.http4s.server.Router
import io.circe._
import io.circe.generic.semiauto._
import org.http4s.{EntityDecoder, EntityEncoder}
import org.http4s.circe.{jsonEncoderOf, jsonOf}

import scala.concurrent.duration._

object TweetsHttp4sServer extends IOApp {

  import cats.effect.unsafe.IORuntime
//  implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global
  case class Tweet(id: Int, message: String)
  object Tweet {
    implicit val tweetEncoder: Encoder[Tweet] = Encoder.instance[Tweet] {
      case tweet: Tweet => Json.fromString(s""""id": "${tweet.id}", "message": "${tweet.message}"""")
    }
    implicit def tweetResponseEncoder[F[_]: Applicative]: EntityEncoder[F, Tweet] = jsonEncoderOf[F, Tweet]
    implicit def tweetsEncoder: Encoder[Seq[Tweet]] = Encoder.instance[Seq[Tweet]] {
      case tweets: Seq[Tweet] => Json.fromValues(tweets.map((tweet: Tweet) => Json.fromString(s""""id": "${tweet.id}", "message": "${tweet.message}"""")))
    }
  }


  def getTweet(tweetId: Int): IO[Tweet] = Tweet(tweetId, "zero").pure[IO]
  def getPopularTweets(): IO[Seq[Tweet]] = Seq(Tweet(0, "zero"), Tweet(1, "two"), Tweet(3, "three")).pure[IO]

  val tweetService = HttpRoutes.of[IO] {
//    case GET -> Root / "tweets" / "popular" =>
//      getPopularTweets().flatMap(x => Ok(x))
    case GET -> Root / "tweets" / IntVar(tweetId) =>
      getTweet(tweetId).flatMap(x => Ok(x))
  }

  import HelloWorldHttp4sServer.helloWorldService
  val services = tweetService <+> helloWorldService
  val httpApp = Router("/" -> helloWorldService, "/api" -> services).orNotFound
  val server = EmberServerBuilder
    .default[IO]
    .withHost(ipv4"0.0.0.0")
    .withPort(port"8080")
    .withHttpApp(httpApp)
    .build

//  val shutdown = server.allocated.unsafeRunSync()(runtime)._2

  def run(args: List[String]): IO[ExitCode] =
    server
      .use(_ => IO.never)
      .as(ExitCode.Success)

}
