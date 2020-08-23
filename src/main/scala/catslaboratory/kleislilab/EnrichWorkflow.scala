package catslaboratory.kleislilab

import cats.data.Kleisli
import cats.effect.IO
import cats.implicits.catsSyntaxTuple2Semigroupal
import io.circe.{Json, parser}

object EnrichWorkflow extends App {
  type Query = String
  type KleisliIO[A, B] = Kleisli[IO, A, B]
  type Workbook = List[String]

  val getFromDb1: KleisliIO[Query, List[String]] = Kleisli { _: Query => IO(List("1", "2", "3")) }
  val getFromDb2: KleisliIO[Query, List[String]] = Kleisli { _: Query => IO(List("4", "5", "6")) }

  val getFromService: KleisliIO[Unit, Json] = Kleisli { _ => IO(parser.parse("{}").getOrElse(Json.Null)) }
  val createWorkbook: KleisliIO[List[String], Workbook] = Kleisli { list: List[String] => IO(list) }


  val result: Workbook = (getFromDb1, getFromDb2)
    .mapN(_ ::: _)
    .andThen(createWorkbook)
    .run(new Query).unsafeRunSync()
  result.foreach(x => println(x))
}
