package shapeless

object Lesson1ShapelessHList extends App {
//    import scala.language.higherKinds
//
//    sealed trait HList {
//      type This >: this.type <: HList
//      type Prepend[A] = HCons[A, This]
//      type Append[A] <: HList
//      type Revert <: HList
//    }
//
//    object HNil extends HList {
//      override type This = HNil
//      override type Append[A] = HCons[A, HNil]
//      override type Revert = HNil
//    }
//
//    final class HCons[H, T <: HList] extends HList {
//      override type This = HCons[H, T]
//      override type Append[A] = HCons[H, T# Append[A]]
//      override type Revert = HCons[T# Revert, H]
//    }
//
//    type HNil = HNil.type
//
//    type :: [H, T <: HList] = T# Prepend[H]
//    type + [Xs <: HList, A] = Xs# Append[A]
//    type revert[Xs <: HList] = Xs# Revert
//
//    implicitly[(Int :: String :: Boolean :: HNil)# Revert =:= (Boolean :: String :: Int :: HNil)]
//    implicitly[revert[Int :: String :: Boolean :: HNil] =:= (Boolean :: String :: Int :: HNil)]


  import poly.identity
  import poly._

  object choose extends (Set ~> Option) {
    def apply[T](s: Set[T]) = s.headOption
  }

//  object size extends Poly1 {
//    implicit def caseInt = at[Int](x => 1)
//
//    implicit def caseString = at[String](_.length)
//
//    implicit def caseTuple[T, U](implicit st: Case.Aux[T, Int], su: Case.Aux[U, Int]) =
//      at[(T, U)](t => size(t._1) + size(t._2))
//  }
//
//  object addSize extends Poly2 {
//    implicit def default[T](implicit st: size.Case.Aux[T, Int]) =
//      at[Int, T] { (acc, t) => acc + size(t) }
//  }
//
//  val l: (Int :: String :: HNil) :: HNil.type :: (Boolean :: HNil) :: HNil =
//    (23 :: "foo" :: HNil) :: HNil :: (true :: HNil) :: HNil
//
//  val res = l flatMap identity
}
