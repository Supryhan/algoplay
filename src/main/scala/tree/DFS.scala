package tree

object DFS extends App {
  type Vertex = Int
  type Graph = Map[Vertex, List[Vertex]]
  val g: Graph = Map(1 -> List(2, 4), 2 -> List(1, 3, 5), 3 -> List(2), 4 -> List(1), 5 -> List(2))

  //example graph meant to represent
  //  1---2
  //  |   | \
  //  4   3  5

  def DFS(start: Vertex): List[Vertex] = {

    def DFS0(v: Vertex, visited: List[Vertex]): List[Vertex] = {
      if (visited.contains(v))
        visited
      else {
        val neighbours: List[Vertex] = g(v).filterNot(visited.contains)
        neighbours.foldLeft(v :: visited)((b, a) => DFS0(a, b))
      }
    }

    DFS0(start, List()).reverse
  }

  println(DFS(5))

}
