package structures

object AmazonBFS extends App {

  type VERTEX = String
  type TREE = Map[VERTEX, List[VERTEX]]

  //      A
  //    /   \
  //   B     C
  //   /\   /|\
  //  D  E F G H
  // print - ACBHGFED
  implicit val g: TREE = Map(
    "A" -> List("B", "C"),
    "B" -> List("A", "D", "E"),
    "C" -> List("A", "F", "G", "H"),
    "D" -> List("B"),
    "E" -> List("B"),
    "F" -> List("C"),
    "G" -> List("C"),
    "H" -> List("C"))

  def bfsTraversal(start: VERTEX)(implicit g: TREE): List[VERTEX] = {
    def bfs(elem: List[VERTEX], visited: List[VERTEX]): List[VERTEX] = {
      val neighbors = elem.flatMap(g(_)).filterNot(visited.contains).distinct
      if (neighbors.isEmpty)
        visited
      else
        bfs(neighbors, neighbors ::: visited)
    }

    bfs(List(start), List(start))
  }
  bfsTraversal("A").reverse.foreach(print)
}
