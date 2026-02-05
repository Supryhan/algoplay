package catslaboratory.trampolining

import cats.effect.{IO, IOApp, ExitCode, Fiber}
import cats.effect.std.Supervisor
import scala.concurrent.duration._

object FiberLifecycleDemo extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    val action = IO(println(s"Action running on thread ${Thread.currentThread().getName}")) >> IO.sleep(1.second) >> IO(println("Action completed"))

    for {
      _ <- demonstrateStart(action)
//      _ <- demonstrateBackground(action)
      _ <- demonstrateSupervisor(action)
    } yield ExitCode.Success
  }

  private def demonstrateStart(action: IO[Unit]): IO[Unit] = {
    IO.println("Starting 'start' demo:") >>
      (for {
        fiber <- action.start
        _ <- IO.sleep(500.millis) >> IO.println("Main fiber still running")
        _ <- fiber.join
      } yield ())
  }

  import cats.effect.{IO, Outcome}
//  private def demonstrateBackground(action: IO[Unit]): IO[Unit] = {
//    IO.println("Starting 'background' demo:") >>
//      (for {
//        fiber <- action.background
//        _ <- IO.sleep(500.millis) >> IO.println("Main fiber still running despite background task")
//        outcome <- fiber.join
//        _ <- outcome match {
//          case Outcome.Succeeded(fa) => fa  // Виконання `fa`, яке є IO[Unit]
//          case Outcome.Errored(e) => IO.println(s"Action failed with error: $e")
//          case Outcome.Canceled() => IO.println("Action was canceled")
//        }
//      } yield ())
//  }



  private def demonstrateSupervisor(action: IO[Unit]): IO[Unit] = {
    IO.println("\nStarting 'Supervisor' demo:") >>
      Supervisor[IO].use { supervisor =>
        for {
          fiber <- supervisor.supervise(action)
          _ <- IO.sleep(500.millis) >> IO.println("Main fiber still running independently of Supervisor")
          _ <- fiber.join
        } yield ()
      }
  }
}

