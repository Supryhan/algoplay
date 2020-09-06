package variance

import org.scalatest.funsuite.AnyFunSuite

class FunctionMiddleTest extends AnyFunSuite {

  import FunctionMiddle._

  test("test when Middle is empty") {
    assert(middle(List.empty).isEmpty)
  }

  test("test when Middle is empty and throws exception") {
    assertThrows[NoSuchElementException] {
      assert(middle(List.empty).get)
    }
  }

  test("test when Middle is odd") {
    assert(Nil == middle(List(1, 2, 3, 4)))
  }

  test("test when Int Middle in even") {
    assert(3 == middle(List(1, 2, 3, 4, 5)).get)
  }

  test("test when String Middle in even") {
    assert("3" == middle(List("1", "2", "3", "4", "5")).get)
  }
}
