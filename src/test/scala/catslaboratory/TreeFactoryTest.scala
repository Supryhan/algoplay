package catslaboratory

import org.specs2.mutable.Specification

class TreeFactoryTest extends Specification {

  import TreesOps._

  "Leaf's value" should {
    "be doubled" in {
      val original: Tree[Int] = TreeFactory.leaf(100)
      val function: Int => Int = (n: Int) => n * 2
      val expected: Tree[Int] = TreeFactory.leaf(200)

      original.map(function) must beEqualTo(expected)
    }
  }

  "All Leafs' values in Branch" should {
    "be doubled" in {
      val original = TreeFactory.branch(TreeFactory.leaf(10), TreeFactory.leaf(20))
      val function = (n: Int) => n * 2
      val expected = TreeFactory.branch(TreeFactory.leaf(20), TreeFactory.leaf(40))

      original.map(function) must beEqualTo(expected)
    }
  }

}
