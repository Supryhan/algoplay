package implicits

object ImplicitTuple extends App {
  println(Show.showArgument(12 -> "34")) // "(12, 34)"
  println(Show.showArgument("34")) // "(12, 34)"
  print(Show.showArgument(12)) // "(12, 34)"
}

trait Show[T] {

  def show(value: T): String
}

object Show {

  def apply[TT: Show]: Show[TT] = implicitly[Show[TT]]

  def showArgument[T: Show](value: T): String = apply[T].show(value)

  implicit val string2Show: Show[String] = s => s
  //  implicit val string2Show: Show[String] = new Show[String] {
  //    def show(value: String): String = value
  //  }

  implicit val int2Show: Show[Int] = _.toString
  //  implicit val int2Show: Show[Int] = new Show[Int] {
  //    override def show(value: Int): String = value.toString
  //  }

  implicit def tuple2Show[T: Show, U: Show]: Show[(T, U)] =
    tuple => s"(${Show.showArgument[T](tuple._1)}, ${Show.showArgument[U](tuple._2)})"

  //  implicit def tuple2Show[T: Show, U: Show]: Show[(T, U)] = new Show[(T, U)]{
  //    override def show(tuple: (T, U)): String = s"(${Show.showArgument[T](tuple._1)}, ${Show.showArgument[U](tuple._2)})"
  //  }
}