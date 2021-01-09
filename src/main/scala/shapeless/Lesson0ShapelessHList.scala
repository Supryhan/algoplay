package shapeless

object Lesson0ShapelessHList extends App {


  sealed trait HList {
    type prepend[A] <: HList
    def ::[A](a: A): prepend[A]
  }

  case class HCons[H, Tail <: HList](head: H, tail: Tail) extends HList {
    override type prepend[A] = HCons[A, HCons[H, Tail]]
    override def ::[A](a: A): prepend[A] = HCons(a, this)
  }

  case object HNil extends HList {
    override type prepend[A] = HCons[A, HNil.type]
    override def ::[A](a: A): prepend[A] = HCons(a, this)
  }

  val x = 2 :: false :: 2 :: Some(4) :: "foo" :: HNil

  val a: Int = x.head // Int
  val b: Boolean = x.tail.head // Boolean


}
