package implicits

object ImplicitTuple extends App {
  Show.show(12 -> "34") // "(12, 34)"
}

trait Show[T] {

  def show(value: T): String
}

object Show {

  def apply[T: Show]: Show[T] = implicitly[Show[T]]

  def show[T: Show](value: T): String = apply[T].show(value)

  implicit val showString: Show[String] = s => s
  implicit val showInt: Show[Int] = _.toString

  implicit def showTuple[T: Show, U: Show]: Show[(T, U)] =
    tuple => s"(${Show.show[T](tuple._1)}, ${Show.show[U](tuple._2)})"
}