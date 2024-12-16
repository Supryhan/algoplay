package probls

import com.typesafe.scalalogging.LazyLogging
import io.circe._
import io.circe.parser._

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

class JsonMapUtils {
}

object ElasticSearchProcessor extends App with LazyLogging {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  val rawJsonWithMap: Future[String] = Future.successful(
    """{
      |     "foo": "bar",
      |     "baz": 123,
      |     "list of stuff": [ 4, 5, 6 ],
      |     "traickyMap": [
      |       {"key1": {"Simple Name": "Simple Value"}},
      |       {"key2": {"innerName": "innerValue"}, total: 1}
      |     ]
      |   }""".stripMargin)

  val rawJsonWithNoMap: Future[String] = Future.successful(
    """{
      |     "foo": "bar",
      |     "baz": 123,
      |     "list of stuff": [ 4, 5, 6 ]
      |   }""".stripMargin)

  print(">>>f1")
  val j1 = parse("""{"foo":"bar","baz":123}""")
  j1 match {
    case Right(value) => println(value)
    case Left(ex) => println(ex)
  }
  print(">>>f2")
  val j2 = parse("""{"foo":"bar","baz":123}""").getOrElse(Json.Null).hcursor.downField("trickyMap").as[Json]
  j2 match {
    case Right(value) => println(value)
    case Left(ex) => println(ex)
  }
//  logger.info(total(rawJsonWithMap))

  def total(rawString: String): String = {
//    val r = parse(rawJsonWithMap)
//      .getOrElse(Json.Null)
//      .hcursor
//      .downField("trickyMap").downArray.as[Json] match {
//      case Right(value) => value.hcursor.downField("key1").downField("Simple Name").as[String] match {
//        case Right(value) => value
//        case Left(value) => value
//      }
//      case Left(value) => value
//    }
    ""
  }
//  def proccess(f: Future[String])(implicit e: ExecutionContext): Future[String] = {
//    f.transform( (_ => _): String => String,
//    case Success(_) => Success("OK")
//    case Failure(_) => Success("KO")
//    )
//  }

}

