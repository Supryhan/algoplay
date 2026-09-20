package com.supryhan.catslaboratory.core

import cats.effect.std.{Queue, Semaphore}
import cats.effect._
import cats.syntax.all._

import scala.concurrent.duration._

object CatsEffectExamples extends IOApp {

  // ============================================================
  // 1. FIBER
  // ============================================================

  private val fiberExample: IO[Unit] = {

    val backgroundJob: IO[Int] =
      for {
        _ <- IO.println("Fiber: background job started")
        _ <- IO.sleep(2.seconds)
        _ <- IO.println("Fiber: background job finished")
      } yield 42

    for {
      _ <- IO.println("Starting fiber example")

      fiber <- backgroundJob.start

      _ <- IO.println(
        "Main fiber continues working while background fiber runs"
      )

      result <- fiber.joinWithNever

      _ <- IO.println(s"Fiber result: $result")
    } yield ()
  }

  // ============================================================
  // 2. REF
  // ============================================================

  private val refExample: IO[Unit] =
    for {
      _ <- IO.println("Starting Ref example")

      counter <- Ref.of[IO, Int](0)

      _ <- counter.update(_ + 1)
      _ <- counter.update(_ + 10)
      _ <- counter.update(_ * 2)

      result <- counter.get

      _ <- IO.println(s"Ref result: $result")
    } yield ()

  // ============================================================
  // 3. DEFERRED
  // ============================================================

  private val deferredExample: IO[Unit] =
    for {
      _ <- IO.println("Starting Deferred example")

      signal <- Deferred[IO, String]

      producerFiber <- (
        for {
          _ <- IO.println("Producer: preparing data...")
          _ <- IO.sleep(2.seconds)
          _ <- IO.println("Producer: data is ready")
          _ <- signal.complete("Hello from producer")
        } yield ()
        ).start

      _ <- IO.println("Consumer: waiting for data...")

      result <- signal.get

      _ <- IO.println(s"Consumer received: $result")

      _ <- producerFiber.joinWithNever
    } yield ()

  // ============================================================
  // 4. QUEUE
  // ============================================================

  private val queueExample: IO[Unit] =
    for {
      _ <- IO.println("Starting Queue example")

      queue <- Queue.unbounded[IO, String]

      producer =
        List("Event-1", "Event-2", "Event-3").traverse_ { event =>
          for {
            _ <- IO.println(s"Producer created: $event")
            _ <- queue.offer(event)
            _ <- IO.sleep(500.millis)
          } yield ()
        }

      consumer =
        List.range(1, 4).traverse_ { _ =>
          for {
            event <- queue.take
            _     <- IO.println(s"Consumer processed: $event")
          } yield ()
        }

      // Producer і consumer запускаються конкурентно
      _ <- (producer, consumer).parTupled
    } yield ()

  // ============================================================
  // 5. SEMAPHORE
  // ============================================================

  private def semaphoreTask(
    taskId: Int,
    semaphore: Semaphore[IO]
  ): IO[Unit] =
    semaphore.permit.use { _ =>
      for {
        _ <- IO.println(s"Task $taskId started")
        _ <- IO.sleep(2.seconds)
        _ <- IO.println(s"Task $taskId finished")
      } yield ()
    }

  private val semaphoreExample: IO[Unit] =
    for {
      _ <- IO.println("Starting Semaphore example")

      // Максимум дві операції можуть виконуватися одночасно
      semaphore <- Semaphore[IO](2)

      tasks =
        List.range(1, 6).map { taskId =>
          semaphoreTask(taskId, semaphore)
        }

      _ <- tasks.parSequence_
    } yield ()

  // ============================================================
  // ЗАПУСК УСІХ ПРИКЛАДІВ
  // ============================================================

  private def printSeparator(title: String): IO[Unit] =
    IO.println(
      s"\n==================== $title ====================\n"
    )

  private val allExamples: IO[Unit] =
    for {
      _ <- printSeparator("FIBER")
      _ <- fiberExample

      _ <- printSeparator("REF")
      _ <- refExample

      _ <- printSeparator("DEFERRED")
      _ <- deferredExample

      _ <- printSeparator("QUEUE")
      _ <- queueExample

      _ <- printSeparator("SEMAPHORE")
      _ <- semaphoreExample
    } yield ()

  // ============================================================
  // ВИБІР ПРИКЛАДУ ЧЕРЕЗ PROGRAM ARGUMENT
  // ============================================================

  override def run(args: List[String]): IO[ExitCode] = {

    val exampleName =
      args.headOption
        .getOrElse("all")
        .toLowerCase

    val selectedExample: IO[Unit] =
      exampleName match {
        case "fiber" =>
          fiberExample

        case "ref" =>
          refExample

        case "deferred" =>
          deferredExample

        case "queue" =>
          queueExample

        case "semaphore" =>
          semaphoreExample

        case "all" =>
          allExamples

        case unknown =>
          IO.println(
            s"""Unknown example: "$unknown"
               |
               |Available examples:
               |  fiber
               |  ref
               |  deferred
               |  queue
               |  semaphore
               |  all
               |""".stripMargin
          )
      }

    selectedExample.as(ExitCode.Success)
  }
}