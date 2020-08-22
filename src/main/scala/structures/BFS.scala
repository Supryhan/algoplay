package structures

object BFS extends App {

  type Vertex = Int
  type Graph = Map[Vertex, List[Vertex]]
  val g: Graph = Map(1 -> List(2, 4), 2 -> List(1, 3), 3 -> List(2, 4, 5, 6), 4 -> List(1, 3), 5 -> List(3), 6 -> List(3, 7), 7 -> List(6))

  //example graph meant to represent
  //  1---2   5
  //  |   | /
  //  4---3---6---7


  def BFS(start: Vertex): List[List[Vertex]] = {

    def BFS0(elems: List[Vertex], visited: List[List[Vertex]]): List[List[Vertex]] = {
      val newNeighbors = elems
        .flatMap(g(_))
        .filterNot(visited.flatten.contains)
        .distinct
      if (newNeighbors.isEmpty)
        visited
      else
        BFS0(newNeighbors, newNeighbors :: visited)
    }

    BFS0(List(start), List(List(start))).reverse
  }
  print(BFS(4))
}
