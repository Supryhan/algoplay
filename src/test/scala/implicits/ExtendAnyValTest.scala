package implicits

import org.scalatest.FunSuite

class ExtendAnyValTest extends FunSuite {

  import ExtendAnyVal._

  test("test Double is zero") {
    assert(0.0.isZero)
  }

  test("test Double is NOT zero") {
    assert(!3.0.isZero)
  }

}
