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

//private sealed abstract class Animal
//private case class Cat() extends Animal
//private class CatChild() extends Cat
//private case class DogChild() extends Animal

private case class Box[+A](value: A) {
  def set[B >: A](a: B): A = value
}
private case class BoxNP[-A, +B](value: B) {
  def set(a: A): B = value
}

private object Box extends App {
  val catBox = new Box[Cat](Cat(14, "ham"))
  val animalBox: Box[Animal] = catBox
  val dog = Dog
  animalBox.set(dog)
  Box[Cat](ShroedingersCat).set(new Animal(tongue = "tongue"){override val age: Int = 0; override def isAlive: Boolean = false;})
}