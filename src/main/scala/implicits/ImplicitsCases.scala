package implicits


sealed trait Container[T] {
  def apply(container: List[T]): Unit
}

object ImplicitsCases extends App {
  implicit def stringImpl: Container[String] = new Container[String] {
    override def apply(container: List[String]): Unit = print("string \n")
  }

  implicit def intImpl: Container[Int] = new Container[Int] {
    override def apply(container: List[Int]): Unit = print("int \n")
  }

  def convert[A: Container](x: List[A]): Unit = implicitly[Container[A]].apply(x)

  convert(List(1, 2, 3))
  convert(List("1", "2", "3"))
}

