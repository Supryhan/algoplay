package problemslab.refined

import cats.Apply

object MyProductRImpl extends App {

  import cats.implicits._
  import cats.data.Validated
  import Validated.{Valid, Invalid}

  type ErrOr[A] = Validated[String, A]

  val validInt: ErrOr[Int] = Valid(3)
  val validBool: ErrOr[Boolean] = Valid(true)
  val invalidInt: ErrOr[Int] = Invalid("Invalid int.")
  val invalidBool: ErrOr[Boolean] = Invalid("Invalid boolean.")

  println(Apply[ErrOr].productR(validInt)(validBool)) //Valid(true)
  println(Apply[ErrOr].productR(invalidInt)(validBool)) //Invalid(Invalid int.)
  println(Apply[ErrOr].productR(validInt)(invalidBool)) //Invalid(Invalid boolean.)
  println(Apply[ErrOr].productR(invalidInt)(invalidBool)) //Invalid(Invalid int.Invalid boolean.)

}
