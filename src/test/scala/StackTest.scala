import org.scalatest.FunSuite

class StackTest extends FunSuite {

  test("I can store three integers") {
    val stack = Cons(1, Cons(2, Cons(3, Empty)))
    val stack2 = Empty.cons(3).cons(2).cons(1)
    val stack3 = Empty.cons(Empty).cons(Empty).cons(Empty)
    val stack4 = Cons(Empty,Cons(Empty,Cons(Empty,Empty)))
    assert(stack == stack2)
    assert(stack4 == stack3)
  }

  test("I can add new elements") {
    val emptyStack = Empty
    val oneElementStack = Cons(1, Empty)
    val twoElementStack = oneElementStack ++ Cons(2, Empty)
    assert(emptyStack.size == 0)
    assert(oneElementStack.size == 1)
    assert(twoElementStack.size == 2)
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