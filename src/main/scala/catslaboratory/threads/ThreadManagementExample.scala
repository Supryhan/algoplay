package catslaboratory.threads

import zio._

object ThreadManagementExample extends ZIOAppDefault {

  override def run: ZIO[Any, Nothing, Unit] = {
    for {
      _ <- ZIO.succeed(
        println(s"Running on thread: ${Thread.currentThread.getName}")
      )

      _ <- ZIO.async[Any, Nothing, Unit] { callback =>
        val thread = new Thread(() => {
          Thread.sleep(1000)

          callback(
            ZIO.succeed(
              println(s"Callback on thread: ${Thread.currentThread.getName}")
            )
          )
        })

        thread.start()
      }
    } yield ()
  }
}