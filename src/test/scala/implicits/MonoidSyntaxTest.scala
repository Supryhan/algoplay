package implicits

import org.scalatest.FunSuite

class MonoidSyntaxTest extends FunSuite {

  import MonoidSyntax._

  test("test Booleans are processed properly") {
    assert("false" == addAndShow(true, false))
  }

  test("test Integers are processed properly") {
    assert("3" == addAndShow(1, 2))
  }

}
