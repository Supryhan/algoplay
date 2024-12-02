package implicits

/**
  * Task: Develop a Scala application that implements a typeclass named `Truthy` for evaluating truthiness of various types.
  * The application should enable any type to be checked for its truthiness through the `trueOrNot` method. The default
  * implementation should return `true` for all types.  *
  * The primary purpose is to showcase the flexibility of typeclasses in Scala, applying custom logic to different data types
  * and demonstrating practical use cases like filtering collections based on custom conditions.
  */
object TruthyTypeclass extends App {

  //Write a Truthy typeclass, which allows to call
//  def trueOrNot(): Boolean = ??? //function on any type

  trait Truthy[T]{
    def isTrue(value: T): Boolean
  }
  object TruthyInstances {
    implicit val intTruthy: Truthy[Int] = new Truthy[Int] {
      override def isTrue(value: Int): Boolean = value == 0
    }
    implicit val floatTruthy: Truthy[Double] = new Truthy[Double] {
      override def isTrue(value: Double): Boolean = value == 0.0
    }
    implicit val charTruthy: Truthy[Char] = new Truthy[Char] {
      override def isTrue(value: Char): Boolean = value == 'a'
    }
    implicit def listTruthy[T]: Truthy[List[T]] = new Truthy[List[T]] {
      override def isTrue(value: List[T]): Boolean = value.isEmpty
    }
    implicit val cexceptionTruthy: Truthy[Exception] = new Truthy[Exception] {
      override def isTrue(value: Exception): Boolean = value.isInstanceOf[Exception]
    }
  }

  implicit class TruthyOps[T](value: T) {
    def trueOrNot()(implicit truthy: Truthy[T]): Boolean = truthy.isTrue(value)
  }

  import TruthyInstances._
  println(s"Truthy Int(1) result = ${1.trueOrNot()}")
  println(s"Truthy Int(0) result = ${0.trueOrNot()}")
  println(s"Truthy Double result = ${0.0.trueOrNot()}")
  println(s"Truthy Char result = ${'a'.trueOrNot()}")
  println(s"Truthy List result = ${List().trueOrNot()}")
  println(s"Truthy Exception result = ${new Exception("no exception").trueOrNot()}")


}
