package problemslab.taglessfinal

object RunTaglessStep1 extends App {
  val fe: IExp = Add(Lit(8), Neg(Add(Lit(1), Lit(2))))
  println(s"Result1:$fe")

  import ExpSyntax._

  println(s"Result2Int:${tf0[Int]}")
  println(s"Result2Str:${tf0[String]}")

  def tf1[T: Exp]: T = add(lit(8), neg(add(lit(1), lit(2))))

  println(s"Result3:${tf1[Int]}")
  println(s"Result4:${tf1[String]}")
}
