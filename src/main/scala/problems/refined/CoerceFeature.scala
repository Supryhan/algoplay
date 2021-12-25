package problems.refined

import cats.data.ValidatedNel
import cats.implicits._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.{Contains, Empty}
import io.estatico.newtype.macros._
import eu.timepit.refined.api.RefinedTypeOps
import eu.timepit.refined.numeric.Greater
import eu.timepit.refined.predicates.all.NonEmpty
import eu.timepit.refined.refineV
import eu.timepit.refined.types.all.NonEmptyString

object CoerceFeature extends App {

  val str: String = "some runtime value"
  val preFtp1: Either[String, NonEmptyString] = refineV[NonEmpty](str)
  println(s"Refine result 1: $preFtp1")
  println(s"Refine result 2: ${refineV[NonEmpty]("")}")
  println(s"Refine result 3: ${refineV[Empty]("")}")

  type UsernameFtp = String Refined Contains['g']
  @newtype case class Email(value: String)
  case class User(name: UsernameFtp, email: Email)
  def lookup[F[_]](username: UsernameFtp): F[Option[User]] = ???


  type GTFiveFtp = Int Refined Greater[5]
  object GTFiveFtp extends RefinedTypeOps[GTFiveFtp, Int]
  val number: Int = 33
  val preFtp2: Either[String, GTFiveFtp] = GTFiveFtp.from(number)
  println(s"Refine result 4: $preFtp2")
  println(s"Refine result 5: ${refineV[Greater[5]](33)}")
  println(s"Refine result 6: ${refineV[Greater[5]](3)}")

  case class MyType(a: NonEmptyString, b: GTFiveFtp)

  def validate(a: String, b: Int): ValidatedNel[String, MyType] = (
    NonEmptyString.from(a).toValidatedNel,
    GTFiveFtp.from(b).toValidatedNel
    ).mapN(MyType.apply)

  println(validate("name", 42))

}