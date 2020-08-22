package structures

object AmazonBFS extends App {

  type VERTEX = String
  type TREE = Map[VERTEX, List[VERTEX]]

  //      A
  //    /   \
  //   B     C
  //   /\   /|\
  //  D  E F G H
  // print - HGFEDCBA
  implicit val g: TREE = Map(
    "A" -> List("B", "C"),
    "B" -> List("A", "D", "E"),
    "C" -> List("A", "F", "G", "H"),
    "D" -> List("B"),
    "E" -> List("B"),
    "F" -> List("C"),
    "G" -> List("C"),
    "H" -> List("C"))

  def AmazonBFS(start: VERTEX)(implicit g: TREE) = {
    def AmazonBFS0(elem: List[VERTEX], visited: List[List[VERTEX]]): List[List[VERTEX]] = {
      val neighbors = elem.flatMap(g(_)).filterNot(visited.flatten.contains).distinct
      if (neighbors.isEmpty)
        visited
      else
        AmazonBFS0(neighbors, neighbors :: visited)
    }

    AmazonBFS0(List(start), List(List(start)))
  }
  AmazonBFS("A").reverse.flatten.foreach(print)
}
