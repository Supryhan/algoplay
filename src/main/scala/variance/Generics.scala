package variance

object Generics {
  def main(args: Array[String]) {

  }
}

class GenericCellMut[+T](val x: T) {
  def foo[U >: T](x: U): Unit = println()
}

//class Generics[-A, +B] {
//  def p(a: A): B = ???
//  def getResult(a: A, f: A => B): List[B] = {
//    val b = List[B](new B(), new B()) ::: List.empty[B]
//    b
//  }
//}

class A {
  def a() {}
}

class B extends A {
  def b() {}
}

class C extends B {
  def c() {}
}

sealed abstract class Animal2
case class Cat2() extends Animal2
class Cat22() extends Cat2
case class Dog2() extends Animal2

case class Box2[+A](value: A) {
  def set[B >: A](a: B): A = value
}
case class Box22[-A, +B](value: B) {
  def set(a: A): B = value
}

object Box2 extends App {
  val catBox = new Box2[Cat2](Cat2())
  val animalBox: Box2[Animal2] = catBox
  val dog = new Dog2
  animalBox.set(dog)
  Box2[Cat2](new Cat22()).set(new Animal2{})
}