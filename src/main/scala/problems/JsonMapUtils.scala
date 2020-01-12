package problems

import com.typesafe.scalalogging.LazyLogging
import io.circe._
import io.circe.parser._

class JsonMapUtils {
}

object ElasticSearchProcessor extends App with LazyLogging {

  val rawJsonWithMap: String =
    """{
      |     "foo": "bar",
      |     "baz": 123,
      |     "list of stuff": [ 4, 5, 6 ],
      |     "trickyMap": [
      |       {"key1": {"Simple Name": "Simple Value"}},
      |       {"key2": {"innerName": "innerValue"}, total: 1}
      |     ]
      |   }""".stripMargin

  val rawJsonWithNoMap: String =
    """{
      |     "foo": "bar",
      |     "baz": 123,
      |     "list of stuff": [ 4, 5, 6 ]
      |   }""".stripMargin

  val j = parse("""{"foo":"bar","baz":123}""")
  j match {
    case Right(value) => println(value)
    case Left(ex) => println(ex)
  }
  parse("""{"foo":"bar","baz":123}""").getOrElse(Json.Null).hcursor.downField("trickyMap")

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

}

