package structures

case class UnionFind(parent: Vector[Int], rank: Vector[Int]) {
  def find(p: Int): (UnionFind, Int) = {
    if (parent(p) != p) {
      val (newUf, root) = this.find(parent(p))
      (newUf.copy(parent = newUf.parent.updated(p, root)), root)
    } else {
      (this, p)
    }
  }

  def union(p: Int, q: Int): UnionFind = {
    val (newUf1, rootP) = this.find(p)
    val (newUf2, rootQ) = newUf1.find(q)

    if (rootP != rootQ) {
      if (rank(rootP) < rank(rootQ)) {
        newUf2.copy(parent = newUf2.parent.updated(rootP, rootQ))
      } else if (rank(rootP) > rank(rootQ)) {
        newUf2.copy(parent = newUf2.parent.updated(rootQ, rootP))
      } else {
        newUf2.copy(
          parent = newUf2.parent.updated(rootQ, rootP),
          rank = newUf2.rank.updated(rootP, rank(rootP) + 1)
        )
      }
    } else newUf2
  }

  def connected(p: Int, q: Int): (UnionFind, Boolean) = {
    val (newUf, rootP) = this.find(p)
    val (_, rootQ) = newUf.find(q)
    (newUf, rootP == rootQ)
  }
}

object UnionFind {
  def apply(size: Int): UnionFind = {
    UnionFind(Vector.tabulate(size)(identity), Vector.fill(size)(0))
  }
}

object Main extends App {
  val uf = UnionFind(10)
  val uf2 = uf.union(4, 3).union(3, 8).union(6, 5).union(9, 4).union(2, 1)
  val (uf3, connected1) = uf2.connected(9, 3)
  println(s"9 and 3 connected? $connected1")
  val (uf4, connected2) = uf3.connected(1, 0)
  println(s"1 and 0 connected? $connected2")
  val uf5 = uf4.union(5, 0).union(7, 2).union(6, 1).union(7, 3)
  val (uf6, connected3) = uf5.connected(0, 7)
  println(s"0 and 7 connected? $connected3")
}