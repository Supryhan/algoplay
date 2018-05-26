package tmp

object ChainOfImplicitsExample extends App {

  sealed trait Description[T] {
    def name: String
  }

  case class ContainerDescr[P, M[_]](name: String)(implicit childDescr: Description[P]) extends Description[M[P]] {
    override def toString: String = s"$name of $childDescr"
  }

  case class AtomDescr[P](name: String) extends Description[P] {
    override def toString: String = name
  }

  implicit class Describable[T](value: T)(implicit descr: Description[T]) {
    def describe: String = descr.toString
  }

  implicit def listDescr[P](implicit childDescr: Description[P]): Description[List[P]] = ContainerDescr[P, List]("List")

  implicit def arrayDescr[P](implicit childDescr: Description[P]): Description[Array[P]] = ContainerDescr[P, Array]("Array")

  implicit def seqDescr[P](implicit childDescr: Description[P]): Description[Seq[P]] = ContainerDescr[P, Seq]("Sequence")

  implicit val intDescr = AtomDescr[Int]("Integer")
  implicit val strDescr = AtomDescr[String]("String")
  implicit val boolDescr = AtomDescr[Boolean]("Boolean")

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
