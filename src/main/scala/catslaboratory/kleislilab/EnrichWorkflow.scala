package catslaboratory.kleislilab

import cats.data.Kleisli
import cats.effect.IO
import cats.effect.unsafe.implicits.global
import cats.implicits.catsSyntaxTuple2Semigroupal
import catslaboratory.kleislilab.EnrichWorkflow.{Query, Workbook}
import catslaboratory.kleislilab.KleisliOps.KleisliIO
import com.softwaremill.macwire.wire

object EnrichWorkflow extends App {
  type Query = String
  type Workbook = List[String]

  val getFromService: KleisliIO[Unit, Map[String, Int]] = Kleisli { _: Unit =>
    IO(Map("7" -> 7, "8" -> 8, "9" -> 9))
  }
  val getFromDb1: Query => List[String] = wire[GetFromSource1]
  import KleisliOps._
  val getFromDb2: Query => IO[List[String]] = wire[GetFromSource2].apply _

  val createWorkbook: List[String] => Workbook = wire[CreateWorkbook]


  val result: Workbook = (getFromDb1.toKleisliIO, Kleisli(getFromDb2))
    .mapN(_ ::: _)
    .andThen(createWorkbook.toKleisliIO)
    .run(new Query).unsafeRunSync()
  result.foreach(x => println(x))
}

class GetFromSource1 extends (Query => List[String]) {
  override def apply(query: Query): List[String] = List("1", "2", "3")
}

class GetFromSource2(getFromService: KleisliIO[Unit, Map[String, Int]]) {
  def apply(query: Query): IO[List[String]] = for {
    tmp <- getFromService(())
  } yield List("4", "5", "6") ::: tmp.keySet.toList
}

class CreateWorkbook extends (List[String] => Workbook) {
  override def apply(list: List[String]): Workbook = list
}
