package problems

import io.circe._, io.circe.parser._

class JsonMapUtils {
}

object ElasticSearchProcessor extends App {

  val rawJson: String =
    """{
      |     "foo": "bar",
      |     "baz": 123,
      |     "list of stuff": [ 4, 5, 6 ],
      |     "trickyMap": [
      |       {"key1": "value1"},
      |       {"key2": {"innerName": "innerValue"}}
      |     ]
      |   }""".stripMargin
  val parsedJson = parse(rawJson).getOrElse(Json.Null)
  //  print(parsedJson)
  val mapCursor = parsedJson.hcursor

  print(mapCursor.downField("trickyMap").downArray.as[Json] match {
    case Right(value) => value.hcursor.downField("key1").as[String] match {
      case Right(value) => value
      case Left(value) => value
    }
    case Left(value) => value
  })

}