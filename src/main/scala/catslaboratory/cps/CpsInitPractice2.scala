package catslaboratory.cps

import cats.effect.cps._
import cats.effect.{ExitCode, IO, IOApp}

import scala.concurrent.duration.DurationInt

object CpsInitPractice2 extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = program.as(ExitCode.Success)

  private sealed case class Result(data: String, isOK: Boolean)

  private def talkToServer(message: String, arg: Option[String]): IO[Result] = arg
    .fold(
      IO(Result("Not OK", false)))(
      _ => IO(Result("OK", true))
    )

  private def writeToFile(message: String): IO[Unit] = IO.unit

  private def program: IO[Boolean] =
    async[IO] {
      val results1 = talkToServer("request1", None).await
      IO.sleep(100.millis).await

      val results2 = talkToServer("request2", Some(results1.data)).await

      if (results2.isOK) {
        writeToFile(results2.data).await
        IO.println("done!").await
        true
      } else {
        IO.println("abort abort abort").await
        false
      }
    }

}
