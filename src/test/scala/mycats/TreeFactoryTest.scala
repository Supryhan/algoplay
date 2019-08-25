package mycats

import org.scalatest.FunSuite

class TreeFactoryTest extends FunSuite {

  import TreesOps._

  test("Leaf's value should be doubled") {
    val original = TreeFactory.leaf(100)
    val function = (n: Int) => n * 2
    val expected = TreeFactory.leaf(200)

    original.map(function) eq expected
  }

  test("All Leafs' values in Branch should be doubles") {
    val original = TreeFactory.branch(TreeFactory.leaf(10), TreeFactory.leaf(20))
    val function = (n: Int) => n * 2
    val expected = TreeFactory.branch(TreeFactory.leaf(20), TreeFactory.leaf(40))

    original.map(function) eq expected
  }

}
