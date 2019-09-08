package implicits

import scala.language.higherKinds

object ChainOfImplicitsExample extends App {

  sealed trait Description[T] {
    def name: String
  }

  case class ContainerDescription[P, M[_]](name: String)(implicit childDescription: Description[P]) extends Description[M[P]] {
    override def toString: String = s"$name of $childDescription"
  }

  case class AtomDescription[P](name: String) extends Description[P] {
    override def toString: String = name
  }

  implicit class Describable[T](value: T)(implicit description: Description[T]) {
    def describe: String = description.toString
  }

  implicit def listDescription[P](implicit childDescr: Description[P]): Description[List[P]] =
    ContainerDescription[P, List](name = "List")

  implicit def arrayDescription[P](implicit childDescr: Description[P]): Description[Array[P]] =
    ContainerDescription[P, Array](name = "Array")

  implicit def seqDescription[P](implicit childDescr: Description[P]): Description[Seq[P]] =
    ContainerDescription[P, Seq](name = "Sequence")

  implicit val intDescription = AtomDescription[Int](name = "Integer")
  implicit val stringDescription = AtomDescription[String](name = "String")
  implicit val booleanDescription = AtomDescription[Boolean](name ="Boolean")
  implicit val mapDescription = AtomDescription[Map[Int, Int]](name = "Map 0:)")

  println(333.describe)
  println("333".describe)
  println(true.describe)
  println(List(1, 2, 3).describe)
  println(List(false, true, true).describe)
  println(Array("str1", "str2").describe)
  println(Seq(Array(List(1, 2), List(3, 4))).describe)
  println(Map(1 -> 1).describe)
  println(
      Seq(
          Array(
              List(
                  Seq(1, 2, 3),
                  Seq(1, 2, 3)
              ),
              List(
                  Seq(7, 8, 9),
                  Seq(9, 10, 11)
              )
          )
      ).describe)
}
