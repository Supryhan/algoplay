package problems

import io.circe._, io.circe.parser._

class JsonMapUtils {

}


object ElasticSearchProcessor extends App {

  val rawJson: String =
    """{
    |     "foo": "bar",
    |     "baz": 123,
    |     "list of stuff": [ 4, 5, 6 ]
    |   }""".stripMargin
  val parseResult = parse(rawJson)
  print(parseResult)

}