package problems.refined

import cats.data.ValidatedNel
import cats.implicits._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.Contains
import io.estatico.newtype.macros._
import eu.timepit.refined.api.RefinedTypeOps
import eu.timepit.refined.numeric.Greater
import eu.timepit.refined.predicates.all.NonEmpty
import eu.timepit.refined.refineV
import eu.timepit.refined.types.all.NonEmptyString

object CoerceFeature extends App {

  val str: String = "some runtime value"
  val preFtp1: Either[String, NonEmptyString] = refineV[NonEmpty](str)

  type Username = String Refined Contains['g']
  @newtype case class Email(value: String)
  case class User(name: Username, email: Email)
  def lookup[F[_]](username: Username): F[Option[User]] = ???


  type GTFive = Int Refined Greater[5]
  object GTFive extends RefinedTypeOps[GTFive, Int]
  val number: Int = 33
  val preFtp2: Either[String, GTFive] = GTFive.from(number)

  case class MyType(a: NonEmptyString, b: GTFive)

  def validate(a: String, b: Int): ValidatedNel[String, MyType] = (
    NonEmptyString.from(a).toValidatedNel,
    GTFive.from(b).toValidatedNel
    ).mapN(MyType.apply)

  println(validate("name", 42))

}