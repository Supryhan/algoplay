package catslaboratory.trampolining

import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.std.Supervisor

import scala.concurrent.duration.DurationInt

object SupervisorExample extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    Supervisor[IO].use { supervisor =>
      for {
        fiber <- supervisor.supervise(IO(println("Running task...")))
        _ <- IO.sleep(1000.millis) // Long running task
        _ <- fiber.cancel // Cancelling
      } yield ExitCode.Success
    }
  }
}