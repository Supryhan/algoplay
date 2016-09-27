trait Stack[+T] {
  def isEmpty: Boolean
  def cons[U >: T](t: U): Stack[U]
  def head: T
  def tail: Stack[T]
}

sealed abstract class CList[+T] extends Stack[T] {
  def cons[U >: T](t: U): CList[U] = new Cons(t, this)
}

case object Empty extends CList[Nothing] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException()
  def tail: Nothing = throw new NoSuchElementException()
}

case class Cons[+T](hd: T, tl: CList[T]) extends CList[T] {
  def isEmpty: Boolean = false
  def head: T = hd
  def tail: CList[T] = tl
}

