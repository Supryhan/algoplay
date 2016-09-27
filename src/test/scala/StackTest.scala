import org.scalatest.FunSuite

class StackTest extends FunSuite {

  test("I can store three integers") {
    val stack = Cons(1, Cons(2, Cons(3, Empty)))
    val stack2 = Cons(3, Empty).cons(2).cons(1)
    assert(stack == stack2)
  }

}