package problems.refined

import cats.data.{EitherNel, ValidatedNel}
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

  type NonEmptyStringFtp = NonEmptyString

  val str: String = "some runtime value"
  val preFtp1: Either[String, NonEmptyStringFtp] = refineV[NonEmpty](str)
  println(s"Refine result 1: $preFtp1")
  println(s"Refine result 2: ${refineV[NonEmpty]("")}")
  println(s"Refine result 3: ${refineV[Empty]("")}")

  type UsernameFtp = String Refined Contains['g']
  @newtype case class Email(value: String)
  case class User(name: UsernameFtp, email: Email)
  def lookup[F[_]](username: UsernameFtp): F[Option[User]] = ???


  type GTFiveFtp = Int Refined Greater[5]
  object GTFiveOps extends RefinedTypeOps[GTFiveFtp, Int]
  val number: Int = 33
  val preFtp2: Either[String, GTFiveFtp] = GTFiveOps.from(number)
  println(s"Refine result 4: $preFtp2")
  println(s"Refine result 5: ${refineV[Greater[5]](33)}")
  println(s"Refine result 6: ${refineV[Greater[5]](3)}")

  case class MyType(a: NonEmptyStringFtp, b: GTFiveFtp)

  def validateNel(a: String, b: Int): ValidatedNel[String, MyType] = (
    NonEmptyString.from(a).toValidatedNel, // Either[String, NonEmptyString] => ValidatedNel[String, NonEmptyString]
    GTFiveOps.from(b).toValidatedNel
    ).mapN(MyType)

  println(validateNel("name", 42)) // Valid(MyType(name,42))
  println(validateNel("", 4)) // Invalid(NonEmptyList(Predicate isEmpty() did not fail., Predicate failed: (4 > 5).))

  def validateEitherNel(a: String, b: Int): EitherNel[String, MyType] = (
    NonEmptyString.from(a).toEitherNel, // Either[String, NonEmptyString] => EitherNel[String, NonEmptyString]
    GTFiveOps.from(b).toEitherNel
    ).parMapN(MyType)

  println(validateEitherNel("name", 42)) // Right(MyType(name,42))
  println(validateEitherNel("", 4)) // Left(NonEmptyList(Predicate isEmpty() did not fail., Predicate failed: (4 > 5).))

}