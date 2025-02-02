package catslaboratory.threads

import cats.effect.{ExitCode, IO, IOApp}

import scala.concurrent.duration._

object CancellationExample extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    // Create long async task
    val task = IO.sleep(5.seconds) *> IO(println("Operation completed"))

    // Launch task ib Fiber
    for {
      fiber <- task.start
      _ <- IO.sleep(1.second) *> IO(println("Cancelling the task"))
      _ <- fiber.cancel // Cancel task
      _ <- IO(println("Task cancelled"))
    } yield ExitCode.Success
  }
}

// cancellation