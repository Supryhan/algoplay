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

  implicit def listDescription[P](implicit childDescr: Description[P]): Description[List[P]] = ContainerDescription[P, List]("List")

  implicit def arrayDescription[P](implicit childDescr: Description[P]): Description[Array[P]] = ContainerDescription[P, Array]("Array")

  implicit def seqDescription[P](implicit childDescr: Description[P]): Description[Seq[P]] = ContainerDescription[P, Seq]("Sequence")

  implicit val intDescription = AtomDescription[Int]("Integer")
  implicit val stringDescription = AtomDescription[String]("String")
  implicit val booleanDescription = AtomDescription[Boolean]("Boolean")

  println(List(1, 2, 3).describe)
  println(List(false, true, true).describe)
  println(Array("str1", "str2").describe)
  println(Seq(Array(List(1, 2), List(3, 4))).describe)
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
