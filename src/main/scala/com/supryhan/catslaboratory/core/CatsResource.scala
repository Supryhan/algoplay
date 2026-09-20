package com.supryhan.catslaboratory.core

import cats.effect.{IO, IOApp, Resource}

import scala.util.Try

object CatsResource extends IOApp.Simple {

  private final case class User(name: String)

  private final case class FakeConnection(id: Long)

  private def openConnection: IO[FakeConnection] =
    for {
      _          <- IO.println("1. Opening connection...")
      connection <- IO.pure(FakeConnection(100))
      _          <- IO.println(s"2. Connection ${connection.id} opened")
    } yield connection

  private def closeConnection(connection: FakeConnection): IO[Unit] =
    IO.println(s"5. Connection ${connection.id} closed")

  private val connectionResource: Resource[IO, FakeConnection] =
    Resource.make(openConnection)(closeConnection)

  private def loadUser(
    connection: FakeConnection,
    userId: Long
  ): IO[User] =
    IO.blocking {
      println(
        s"3. Loading user $userId using connection ${connection.id}"
      )

      Thread.sleep(1000)

      if (userId < 0)
        throw new RuntimeException("User ID cannot be negative")

      User(s"User-$userId")
    }

  private val program: IO[Unit] =
    for {
      _    <- IO.println("Enter user ID:")
      text <- IO.readLine
      id   <- IO.fromTry(Try(text.toLong))

      user <- connectionResource.use { connection =>
        loadUser(connection, id)
      }

      _ <- IO.println(s"4. Result: $user")
    } yield ()

  override def run: IO[Unit] =
    program.handleErrorWith { error =>
      IO.println(s"Program failed: ${error.getMessage}")
    }
}