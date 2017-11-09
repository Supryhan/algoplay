package tmp

object Generics{
  def main(args: Array[String]) {

  }
}

class Generics[-A, +B] {
  def getResult(a: A): List[B] = {
    var b = List.empty[B]
    b = b ::: b
    b
  }
}

class A {def a(){}}
class B extends A {def b(){}}
class C extends B {def c(){}}