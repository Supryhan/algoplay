package problems.taglessfinal

sealed trait IExp
final case class Lit(i: Int) extends IExp
final case class Neg(e: IExp) extends IExp
final case class Add(r: IExp, l: IExp) extends IExp

trait Exp[T] {
  def sym(i: Int): T
  def !(t: T): T
  def +(l: T, r: T): T
}

object Exp {
  implicit val intExp: Exp[Int] = new Exp[Int] {
    def sym(i: Int): Int = i
    def !(t: Int): Int = -t
    def +(l: Int, r: Int): Int = l + r
  }

  implicit val stringExp: Exp[String] = new Exp[String] {
    def sym(i: Int): String = i.toString
    def !(t: String): String = s"(-${t})"
    def +(l: String, r: String): String = s"${l}${r}"
  }
}

object ExpSyntax {
  def lit[T](i: Int)(implicit e: Exp[T]): T = e.sym(i)
  def neg[T](t: T)(implicit e: Exp[T]): T = e.!(t)
  def add[T](l: T, r: T)(implicit e: Exp[T]): T = e.+(l, r)

  def tf0[T](implicit e: Exp[T]): T = e.+(e.sym(8), e.!(e.+(e.sym(1), e.sym(2))))
}
