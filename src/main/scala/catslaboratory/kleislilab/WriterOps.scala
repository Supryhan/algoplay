package catslaboratory.kleislilab

import cats.Id
import cats.data.WriterT

object WriterOps extends App {

  import cats.data.Writer

  val e: WriterT[Id, List[String], Int] = Writer(
    List("It was the best of times","it was the worst of times"),
    1859
  )

  type WriterId[W, A] = WriterT[Id, W, A]
  type Logged[A] = WriterId[List[String], A]

  import cats.instances.list._ // for Monoid
  import cats.syntax.applicative._ // for pure
  val r: Logged[Int] = 123.pure[Logged]

  import cats.syntax.writer._ // for tell
  val la: WriterId[List[String], Unit] = List("msg1", "msg2", "msg3").tell
  val lb: WriterId[List[String], Int] = 123.writer(List("msg1", "msg2", "msg3"))

  val aResult: Id[Unit] = la.value
  val aLog: Id[List[String]] = la.written

  val result: (List[String], Int) = lb.run

  val writer1: WriterT[Id, List[String], Int] = for {
    a <- 10.pure[Logged]
    _ <- List("a", "b", "c").tell
    b <- 32.writer(List("x", "y", "z"))
  } yield a + b

  val k1: (List[String], Int) = writer1.run
  println(k1)

  val writer2 = writer1.mapWritten(_.map(_.toUpperCase))

  val k2: (List[String], Int) = writer2.run
  println(k2)

  val writer3 = writer2.reset

  val k3: (List[String], Int) = writer3.run
  println(k3)
}
