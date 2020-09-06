package implicits

import org.scalatest.funsuite.AnyFunSuite

class MonoidSyntaxTest extends AnyFunSuite {

  import MonoidSyntax._

  test("test Booleans are processed properly") {
    assert("false" == addAndShow(true, false))
  }

  test("test Integers are processed properly") {
    assert("3" == addAndShow(1, 2))
  }

}
