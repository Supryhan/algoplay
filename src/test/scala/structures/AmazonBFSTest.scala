package structures

import org.scalatest.FunSuite

class AmazonBFSTest extends FunSuite {

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

    assert(List("A", "B", "C", "D", "E", "F", "G", "H") == AmazonBFS.AmazonBFS("A")(gTest).reverse.flatten)
  }

}
