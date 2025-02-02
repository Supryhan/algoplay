package catslaboratory.threads

import zio._

object ThreadManagementExample extends zio.App {
  def run(args: List[String]): URIO[ZEnv, ExitCode] = {
    for {
      _ <- ZIO.succeed(println(s"Running on thread: ${Thread.currentThread.getName}"))
      _ <- ZIO.async[Any, Nothing, Unit] { callback =>
        new Thread(() => {
          Thread.sleep(1000) // Emulate some work
          callback(ZIO.succeed(println(s"Callback on thread: ${Thread.currentThread.getName}")))
        }).start()
      }
    } yield ExitCode.success
  }.exitCode
}