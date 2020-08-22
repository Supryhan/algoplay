package shapeless

object LessonShapelessHList extends App {
//  import scala.language.higherKinds
//
//  sealed trait HList {
//    type This >: this.type <: HList
//    type Prepend[A] = HCons[A, This]
//    type Append[A] <: HList
//    type Revert <: HList
//  }
//
//  object HNil extends HList {
//    override type This = HNil
//    override type Append[A] = HCons[A, HNil]
//    override type Revert = HNil
//  }
//
//  final class HCons[H, T <: HList] extends HList {
//    override type This = HCons[H, T]
//    override type Append[A] = HCons[H, T# Append [A]]
//    override type Revert = HCons[T# Revert, H]
//  }
//
//  type HNil = HNil.type
//
//  type :: [H, T <: HList] = T# Prepend [H]
//  type + [Xs <: HList, A] = Xs# Append [A]
//  type revert[Xs <: HList] = Xs# Revert

//  implicitly[(Int :: String :: Boolean :: HNil)# Revert =:= (Boolean :: String :: Int :: HNil)]
//  implicitly[revert[Int :: String :: Boolean :: HNil] =:= (Boolean :: String :: Int :: HNil)]
}
