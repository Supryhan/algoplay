package variance

object Generics{
  def main(args: Array[String]) {

  }
}
class GenericCellMut[-T](val x: T){
  def foo(x: T) = ???
}

//class Generics[-A, +B] {
//  def p(a: A): B = ???
//  def getResult(a: A, f: A => B): List[B] = {
//    val b = List[B](new B(), new B()) ::: List.empty[B]
//    b
//  }
//}

class A {def a(){}}
class B extends A {def b(){}}
class C extends B {def c(){}}