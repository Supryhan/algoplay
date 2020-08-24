package catslaboratory.kleislilab

import cats.data.Kleisli
import cats.effect.IO
import cats.implicits.catsSyntaxTuple2Semigroupal
import catslaboratory.kleislilab.EnrichWorkflow.{Query, Workbook}
import catslaboratory.kleislilab.KleisliOps.KleisliIO
import com.softwaremill.macwire.wire

object EnrichWorkflow extends App {
  type Query = String
  type Workbook = List[String]

  val getFromDb1: Query => List[String] = wire[GetFromSourse1]
  val getFromDb2: Query => List[String] = wire[GetFromSourse2]

  //TODO: need to integrate into solution
  val getFromService: KleisliIO[Unit, Map[String, Int]] = Kleisli { _: Unit =>
    IO(Map("7" -> 7, "8" -> 8, "9" -> 9))
  }
  val createWorkbook: List[String] => Workbook = wire[CreateWorkbook]

  import KleisliOps._
  val result: Workbook = (getFromDb1.toKleisliIO, getFromDb2.toKleisliIO)
    .mapN(_ ::: _)
    .andThen(createWorkbook.toKleisliIO)
    .run(new Query).unsafeRunSync()
  result.foreach(x => println(x))
}

class GetFromSourse1 extends (Query => List[String]) {
  override def apply(query: Query): List[String] = List("1", "2", "3")
}

class GetFromSourse2 extends (Query => List[String]) {
  override def apply(query: Query): List[String] = List("4", "5", "6")
}

class CreateWorkbook extends (List[String] => Workbook) {
  override def apply(list: List[String]): Workbook = list
}
