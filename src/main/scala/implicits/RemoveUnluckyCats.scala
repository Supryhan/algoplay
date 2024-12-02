package implicits

/**
  * Task: Develop a Scala application that demonstrates filtering collections of custom case classes, `Train` and `Cat`, based on specific
  * conditions. The `Train` objects should be filtered to exclude any trains whose name contains the number "13". Meanwhile,
  * `Cat` objects should be filtered to exclude any cats that are either black in color or 13 years old.
  *
  * The primary purpose is to showcase the flexibility of typeclasses in Scala, applying custom logic to different data types
  * and demonstrating practical use cases like filtering collections based on custom conditions.
  */
object RemoveUnluckyCats extends App {

  case class Train(name: String) {
    def is13: Boolean = name.contains("13")
  }

  case class Cat(age: Int, color: String) {
    def isBlack: Boolean = color == "black"
  }

  val trains = Seq(Train("k2839"), Train("f13"))
  val cats = Seq(Cat(10, "black"), Cat(5, "white"), Cat(13, "pink"))

  // TODO: Implement type-class here


//  trains.removeUnlucky      //shoud be List(Train(k2839))
//  cats.removeUnlucky        //shoud be List(Cat(5,white))
  println(s"Trains: ${trains.filterNot(x => x.is13)}")
  println(s"Unlucky: ${cats.filterNot(c => c.isBlack || c.age == 13)}")
}

class X {
  def removeUnlucky() = ???
}

object TC extends App {
  sealed trait Json
  final case class JsObject(get: Map[String, Json]) extends Json
  final case class JsString(get: String) extends Json
  final case class JsNumber(get: Double) extends Json
  final case object JsNull extends Json

  trait JsonWriter[A] {
    def write(value: A): Json
  }

  object JsonWriterInstances {
    implicit val stringWriter: JsonWriter[String] = value => JsString(value)
    implicit val personWriter: JsonWriter[Int] = value => JsNumber(value.toDouble)
    implicit val optionIntWriter: JsonWriter[Option[Int]] = {
      case Some(i) => i.toJson
      case None => JsNull
    }
    implicit val optionPersonWriter: JsonWriter[Option[String]] = {
      case Some(str) => str.toJson
      case None => JsNull
    }

    implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] = {
      case Some(aValue) => writer.write(aValue)
      case None => JsNull
    }
  }

  object Json {
    def toJson[A](value: A)(implicit w: JsonWriter[A]): Json = w.write(value)
  }

  import JsonWriterInstances._

  Json.toJson(1)
  Json.toJson("1")

  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json = w.write(value)
  }

  println(1.toJson)
  println("2".toJson)
  println(Option(3).toJson)
  println(Option("4").toJson)

  val o: Option[String] = None
  println(o.toJson)
}