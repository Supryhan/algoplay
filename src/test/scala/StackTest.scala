import org.scalatest.FunSuite

class StackTest extends FunSuite {

  test("I can store three integers") {
    val stack = Cons(1, Cons(2, Cons(3, Empty)))
    val stack2 = Empty.cons(3).cons(2).cons(1)
    assert(stack == stack2)
  }

  test("I can check head") {
    val stack = Cons("head", Empty)
    assert(stack.hd == "head")
  }

  test("I can check tail") {
    val stack = Cons("tail", Empty).cons("head")
    assert(stack.tail == Cons("tail", Empty))
  }

}