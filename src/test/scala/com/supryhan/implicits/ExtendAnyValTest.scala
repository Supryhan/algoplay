package com.supryhan.implicits

import org.scalatest.funsuite.AnyFunSuite

class ExtendAnyValTest extends AnyFunSuite {

  import com.supryhan.implicits.ExtendAnyVal._

  test("test Double is zero") {
    assert(0.0.isZero)
  }

  test("test Double is NOT zero") {
    assert(!3.0.isZero)
  }

}
