package structures

object MaybeFun extends App {
  def position[A](xs: List[A], value: A): Maybe[Int] = {
    val index = xs.indexOf(value)
    if (index != -1) Just(index) else MyNil
  }

//  println(position(List.empty[Int], 1).getOrElse(AnyRef))
//  println(position(List(), 1).getOrElse(AnyRef))
//  println(position(List(0, 1, 2, 3, 4), 4).getOrElse(-1))
  val ll1: List[Char] =  List("one","two", "three", "") flatMap { _.toList }
  val ll2: List[List[Char]] =  List("one","two", "three", "") map { _.toList }
  println(ll1)
  println(ll2)
}

sealed abstract class Maybe[+A] {
  def isEmpty: Boolean
  def get: A
  def getOrElse[B >: A](default: B): B = {
    if(isEmpty) default else get
  }
}
final case class Just[A](value: A) extends Maybe[A] {
  def isEmpty = false
  def get = value
}
case object MyNil extends Maybe[scala.Nothing] {
  def isEmpty = true
  def get = throw new NoSuchElementException("Nil.get")
}