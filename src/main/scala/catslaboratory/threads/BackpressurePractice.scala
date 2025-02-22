package catslaboratory.threads

import cats.effect.{IO, IOApp, Outcome}
import cats.effect.std.Backpressure
import cats.effect.unsafe.implicits.global
import scala.concurrent.duration._

object BackpressurePractice extends IOApp.Simple {
  val program =
    for {
      backpressure <- Backpressure[IO](Backpressure.Strategy.Lossless, 1)
      _ <- IO.println("Backpressure created with a buffer of size 1.")
      f1 <- backpressure.metered(IO.println("Task 1 starting...") >> IO.sleep(1.second) >> IO.pure(1)).start
      _ <- IO.sleep(100.millis)
      f2 <- backpressure.metered(IO.println("Task 2 starting...") >> IO.sleep(1.second) >> IO.pure(1)).start
      res1 <- f1.joinWithNever
      res2 <- f2.joinWithNever
      _ <- IO.println(s"Results: Task 1 -> $res1, Task 2 -> $res2")
    } yield ()

  override def run: IO[Unit] = {
    program.handleErrorWith { error =>
      IO.println(s"An error occurred: $error")
    }
  }
}
