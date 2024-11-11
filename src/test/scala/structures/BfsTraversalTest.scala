package structures

import org.scalatest.funsuite.AnyFunSuite

class BfsTraversalTest extends AnyFunSuite {

  test("test AmazonBFS process properly") {
    val gTest = Map(
      "A" -> List("B", "C"),
      "B" -> List("A", "D", "E"),
      "C" -> List("A", "F", "G", "H"),
      "D" -> List("B"),
      "E" -> List("B"),
      "F" -> List("C"),
      "G" -> List("C"),
      "H" -> List("C"))

    assert(AmazonBFS.bfsTraversal("A")(gTest).reverse == List("A", "C", "B", "H", "G", "F", "E", "D"))
  }

}
