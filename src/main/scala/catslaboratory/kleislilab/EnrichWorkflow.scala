package catslaboratory.kleislilab

import cats.data.Kleisli
import cats.effect.IO
import cats.implicits.catsSyntaxTuple2Semigroupal
import catslaboratory.kleislilab.EnrichWorkflow.{Query, Workbook}
import com.softwaremill.macwire.wire

object EnrichWorkflow extends App {
  type Query = String
  type KleisliIO[A, B] = Kleisli[IO, A, B]
  type Workbook = List[String]

  val getFromDb1: Query => IO[List[String]] = wire[GetFromSourse1]
  val getFromDb2: Query => IO[List[String]] = wire[GetFromSourse2]

  val getFromService: KleisliIO[Unit, Map[String, Int]] = Kleisli { _: Unit =>
    IO(Map("7" -> 7, "8" -> 8, "9" -> 9))
  }
  val createWorkbook: List[String] => IO[Workbook] = wire[CreateWorkbook]


  val result: Workbook = (Kleisli(getFromDb1), Kleisli(getFromDb2))
    .mapN(_ ::: _)
    .andThen(createWorkbook)
    .run(new Query).unsafeRunSync()
  result.foreach(x => println(x))
}

class GetFromSourse1 extends (Query => IO[List[String]]) {
  override def apply(query: Query): IO[List[String]] = IO(List("1", "2", "3"))
}

class GetFromSourse2 extends (Query => IO[List[String]]) {
  override def apply(query: Query): IO[List[String]] = IO(List("4", "5", "6"))
}

class CreateWorkbook extends (List[String] => IO[Workbook]) {
  override def apply(list: List[String]): IO[Workbook] = IO(list)
}
