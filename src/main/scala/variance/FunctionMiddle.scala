package variance

object FunctionMiddle {
  def middle[A](xs: List[A]): Maybe[A] = {
    xs match {
      case scala.collection.immutable.Nil => Nil
      case _ if isEven(xs.size) => Nil
      case _ => Just(xs(xs.size / 2))
    }
  }

  def isEven(number: Int): Boolean = number % 2 == 0
}

sealed abstract class Maybe[+A] {
  def isEmpty: Boolean
  def get: A
}

final case class Just[A](value: A) extends Maybe[A] {
  def isEmpty = false
  def get = value
}

case object Nil extends Maybe[Nothing] {
  def isEmpty = true
  def get = throw new NoSuchElementException("Nil.get")
}
