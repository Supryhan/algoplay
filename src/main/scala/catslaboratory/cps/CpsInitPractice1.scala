package catslaboratory.cps

import cats.effect.{ExitCode, IO, IOApp}

import scala.concurrent.duration.DurationInt

object CpsInitPractice1 extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = program.as(ExitCode.Success)

  private sealed case class Result(data: String, isOK: Boolean)

  private def talkToServer(message: String, arg: Option[String]): IO[Result] = arg
    .fold(
      IO(Result("Not OK", false)))(
      _ => IO(Result("OK", true))
    )

  private def writeToFile(message: String): IO[Unit] = IO.unit

  private def program: IO[Boolean] =
    for {
      results1 <- talkToServer("request1", None)
      _ <- IO.sleep(100.millis)
//      results2 <- talkToServer("request2", Some(results1.data))
      results2 <- talkToServer("request2", None)

      back <- if (results2.isOK) {
        for {
          _ <- writeToFile(results2.data)
          _ <- IO.println("done!")
        } yield true
      } else {
        IO.println("abort abort abort").as(false)
      }
    } yield back


}
