import org.scalatest.{FunSuite, Tag}

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
    val longStack = Cons(1, Cons(2, Cons(3, Empty)))
    assert(longStack.tail == Cons(2, Cons(3, Empty)))
  }

  test("I can update stack") {
    val stack = Empty.cons("old")
    assert("old" == stack.head)
    val stack2 = stack.update("new", 0)
    assert("new" == stack2.head)
  }

  test("I can concat two stacks") {
    val stack1 = Empty.cons(1).cons(2).cons(3)
    val stack2 = Empty.cons(4).cons(5).cons(6)
    val stack = stack1.++(stack2)
    assert(stack.size == 6)
  }

  test("I can add one to all elements") {
    val stack1 = Empty.cons(1).cons(2).cons(3)
    val stack2 = stack1.map((x: Int) => x + 1)
    assert(stack2 == Empty.cons(2).cons(3).cons(4))
  }

}