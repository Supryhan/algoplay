package implicits

object ImplicitTuple extends App {
  print(Show.show(12 -> "34")) // "(12, 34)"
}

trait Show[T] {

  def show(value: T): String
}

object Show {

    def apply[TT: Show](): Show[TT] = implicitly[Show[TT]]

  def show[T: Show](value: T): String = apply[T].show(value)

    implicit val showString: Show[String] = s => s
//  implicit val showString = new Show[String] {
//    def show(value: String): String = value
//  }
  implicit val showInt: Show[Int] = _.toString

  implicit def showTuple[T: Show, U: Show]: Show[(T, U)] =
    argument => s"(${Show.show[T](argument._1)}, ${Show.show[U](argument._2)})"
}