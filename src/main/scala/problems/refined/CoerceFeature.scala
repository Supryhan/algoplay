package problems.refined

import cats.data.{EitherNel, ValidatedNel}
import cats.implicits._
import eu.timepit.refined.api.{Refined, RefinedTypeOps, Validate}
import eu.timepit.refined.collection.{Contains, Empty}
import io.estatico.newtype.macros._
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
  type EmailR = NonEmptyString
  //  object EmailR extends RefinedTypeOps[EmailR, String]
  @newtype case class Email(value: EmailR)
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

  println(s"Refine result 7: ${validateNel("name", 42)}") // Valid(MyType(name,42))
  println(s"Refine result 8: ${validateNel("", 4)}") // Invalid(NonEmptyList(Predicate isEmpty() did not fail., Predicate failed: (4 > 5).))

  def validateEitherNel(a: String, b: Int): EitherNel[String, MyType] = (
    NonEmptyString.from(a).toEitherNel, // Either[String, NonEmptyString] => EitherNel[String, NonEmptyString]
    GTFiveOps.from(b).toEitherNel
    ).parMapN(MyType)

  println(s"Refine result 9: ${validateEitherNel("name", 42)}") // Right(MyType(name,42))
  println(s"Refine result 10: ${validateEitherNel("", 4)}") // Left(NonEmptyList(Predicate isEmpty() did not fail., Predicate failed: (4 > 5).))

  type UserNameR = NonEmptyString
  object UserNameR extends RefinedTypeOps[UserNameR, String]
  type NameR = NonEmptyString
  object NameR extends RefinedTypeOps[NameR, String]
//  type EmailR = String Refined NonEmpty

  @newtype case class UserName(value: UserNameR)
  @newtype case class Name(value: NameR)
  case class Person(username: UserName, name: Name, email: Email)

  import NewtypeRefinedOps._
  import io.estatico.newtype.ops._
  def mkPerson(u: String, n: String, e: String): EitherNel[String, Person] =
    (validate[UserName](u),
      validate[Name](n),
      validate[Email](e)).parMapN(Person.apply)

  println(s"Refine result 11: ${mkPerson("username", "name", "email")}")
  println(s"Refine result 12: ${mkPerson("", "", "email")}")
}

object NewtypeRefinedOps {

  import io.estatico.newtype.Coercible
  import io.estatico.newtype.ops._

  final class NewtypeRefinedPartiallyApplied[A] {
    def apply[T, P](raw: T)(implicit
                            c: Coercible[Refined[T, P], A],
                            v: Validate[T, P]): EitherNel[String, A] =
      refineV[P](raw).toEitherNel.map(_.coerce[A])
  }

  def validate[A]: NewtypeRefinedPartiallyApplied[A] =
    new NewtypeRefinedPartiallyApplied[A]
}