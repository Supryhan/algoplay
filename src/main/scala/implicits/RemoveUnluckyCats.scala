package implicits

class RemoveUnluckyCats {
  //Write a Truthy typeclass, which allows to call
  def trueOrNot(): Boolean = ??? //function on any type

  implicit class A[T](value: T) {
    def trueOrNot(): Boolean = true
  }

  1.trueOrNot()
  (0.0).trueOrNot()
  'a'.trueOrNot()
  List().trueOrNot()
  new Exception("no exception").trueOrNot()


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
      case Some(value) => JsNull
      case None => JsNull
    }
    implicit val optionPersonWriter: JsonWriter[Option[String]] = {
      case Some(value) => JsNull
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

  1.toJson
  "1".toJson
  Option(1).toJson
}