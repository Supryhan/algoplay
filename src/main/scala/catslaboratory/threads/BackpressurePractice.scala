package catslaboratory.threads

import cats.effect.{IO, IOApp, Outcome}
import cats.effect.std.Backpressure
import cats.effect.unsafe.implicits.global
import scala.concurrent.duration._

object BackpressurePractice extends IOApp.Simple {
  // Функція для демонстрації використання backpressure
  val program =
    for {
      // Створення об'єкта Backpressure зі стратегією Lossless і буфером розміром 1
      backpressure <- Backpressure[IO](Backpressure.Strategy.Lossless, 1)
      _ <- IO.println("Backpressure created with a buffer of size 1.")
      // Запуск двох асинхронних завдань, які будуть керуватись через Backpressure
      f1 <- backpressure.metered(IO.println("Task 1 starting...") >> IO.sleep(1.second) >> IO.pure(1)).start
      _ <- IO.sleep(100.millis) // Невелика затримка для демонстрації ефекту обмеження
      f2 <- backpressure.metered(IO.println("Task 2 starting...") >> IO.sleep(1.second) >> IO.pure(1)).start
      // Очікування завершення обох завдань і збір результатів
      res1 <- f1.joinWithNever
      res2 <- f2.joinWithNever
      _ <- IO.println(s"Results: Task 1 -> $res1, Task 2 -> $res2")
    } yield ()

  // Реалізація методу run для запуску програми
  override def run: IO[Unit] = {
    program.handleErrorWith { error =>
      IO.println(s"An error occurred: $error")
    }
  }
}
