package problemslab.taglessfinal

object LoginService extends App {

  trait Algebra[E[_]] {
    def b(value: Boolean): E[Boolean]
    def i(value: Int): E[Int]
    def &&(v1: E[Boolean], v2: E[Boolean]): E[Boolean]
    def ||(v1: E[Boolean], v2: E[Boolean]): E[Boolean]
    def !(value: E[Boolean]): E[Boolean]
    def add(v1: E[Int], v2: E[Int]): E[Int]
  }
  case class SimpleWrapper[A](value: A)
  case class ComplicatedWrapper[A](value: A)

  object TF_v2 {
    implicit val simpleAlgebra: Algebra[SimpleWrapper] = new Algebra[SimpleWrapper] {
      override def b(value: Boolean): SimpleWrapper[Boolean] = SimpleWrapper(value)
      override def i(value: Int): SimpleWrapper[Int] = SimpleWrapper(value)
      override def &&(v1: SimpleWrapper[Boolean], v2: SimpleWrapper[Boolean]): SimpleWrapper[Boolean] = SimpleWrapper(v1.value && v2.value)
      override def ||(v1: SimpleWrapper[Boolean], v2: SimpleWrapper[Boolean]): SimpleWrapper[Boolean] = ???
      override def !(value: SimpleWrapper[Boolean]): SimpleWrapper[Boolean] = ???
      override def add(v1: SimpleWrapper[Int], v2: SimpleWrapper[Int]): SimpleWrapper[Int] = ???
    }

    //  implicit val complicatedAlgebra: Algebra[ComplicatedWrapper] = new Algebra[ComplicatedWrapper] {
    //    override def b(value: Boolean): ComplicatedWrapper[Boolean] = ???
    //    override def i(value: Int): ComplicatedWrapper[Int] = ???
    //    override def &&(v1: ComplicatedWrapper[Boolean], v2: ComplicatedWrapper[Boolean]): ComplicatedWrapper[Boolean] = ???
    //    override def ||(v1: ComplicatedWrapper[Boolean], v2: ComplicatedWrapper[Boolean]): ComplicatedWrapper[Boolean] = ???
    //    override def !(value: ComplicatedWrapper[Boolean]): ComplicatedWrapper[Boolean] = ???
    //    override def add(v1: ComplicatedWrapper[Int], v2: ComplicatedWrapper[Int]): ComplicatedWrapper[Int] = ???
    //  }
  }

  def program[E[_]](implicit alg: Algebra[E]): E[Boolean] = {
    import alg._
    &&(b(true), b(true))
  }

  import TF_v2._
  println(program.value)
}
