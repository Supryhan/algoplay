package mycats

object CatsTraining extends App {

  implicit val i: Mon[String] = new Mon[String] {
    override def comb(a: String, b: String): String = s".$a.$b"
    override val em: String = "->"
  }
  val a = "a1" /+/ "b4"
  println("a1" /+/ "b4")

}

//trait Sem[A] {
//}

trait Mon[A] {//extends Sem[A] {
  def -=-(o: A): A = A.c
  def comb(a: A, b: A): A
  val em: A
}

