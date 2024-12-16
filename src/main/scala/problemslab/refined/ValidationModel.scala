package problemslab.refined

import cats.data.ValidatedNel
import cats.implicits.toBifunctorOps
import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import shapeless.tag.@@

import scala.annotation.implicitNotFound

trait ValidationModel[R, P] {
  final type Raw = R
  final type Predicate = P
  final type T = Raw Refined P

  /*
   * Tagging is used here in order to guarantee uniqueness because models Like
   *
   * {{{
   *
   * object Mode1A extends ValidationModel[String, NonEmpty]
   * object Mode1B extends ValidationModel[String, NonEmpty]
   *
   * implicit val err0: Mode1A.ErrorMsg[String] = _ => "err1"
   * implicit val err1: Mode1B.ErrorMsg[String] = _ => "err2"
   *
   * }}}
   *
   * leads to ambiguous implicits error
   */

//  final type Err[A] = ValidationModel.Err[R @@ T with this.type, A]
  final type Result[E] = ValidatedNel[E, T]

//  final def apply[E: Err](value: R)(implicit v1: Validate[R, P]): ValidatedNel[E, T] = {
//    ???
//    refineV(value)
//      .leftMap(_ => ValidationModel.Err[R @@ T with this.type, E].get(tag[T with this.type](value)))
//      .toValidatedNel
//  }
}

//object ValidationModel {
//
//  @implicitNotFound("No instance of Err[${A}, ${C}] in scope")
//  trait Err[A, +C] {
//    def get(value: A): C
//  }
//
//  object Err {
//    def apply[A, C: Err[A, _]]: Err[A, C] = implicitly
//  }
//
//  object Predef {
//    implicit def unit[A]: ValidationModel.Err[A, Unit] = _ => ()
//  }
//}
