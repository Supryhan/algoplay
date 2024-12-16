package catslaboratory.monadslab

object ParallelMonads extends App {

  import java.util.Date

  // import everything for simplicity:
  import cats._
  import cats.syntax.all._

  def longToDate: Long => Date = new Date(_)
  def dateToLong: Date => Long = _.getTime

  implicit val semigroupDate: Semigroup[Date] =
    Semigroup[Long].imap(longToDate)(dateToLong)

  val today: Date = longToDate(1449088684104l)
  val timeLeft: Date = longToDate(1900918893l)

  println(today |+| timeLeft)


  import io.circe._
  import io.circe.generic.semiauto._
  import io.circe.parser._
  import io.circe.syntax._
  import cats._
  import cats.implicits._

  case class UserSettings(language: String, notifications: Boolean)

  case class UserProfile(name: String, age: Int, settings: UserSettings)

  implicit val userSettingsEncoder: Encoder[UserSettings] = deriveEncoder[UserSettings]
  implicit val userSettingsDecoder: Decoder[UserSettings] = deriveDecoder[UserSettings]
  implicit val userProfileEncoder: Encoder[UserProfile] = deriveEncoder[UserProfile]
  implicit val userProfileDecoder: Decoder[UserProfile] = deriveDecoder[UserProfile]

  // Функція для серіалізації UserProfile до JSON
  def serializeUserProfile(userProfile: UserProfile): String = userProfile.asJson.spaces2

  // Функція для десеріалізації JSON до UserProfile
  def deserializeUserProfile(json: String): Either[Error, UserProfile] = decode[UserProfile](json)

  // Тестування серіалізації та десеріалізації
  val user = UserProfile("Alice", 28, UserSettings("English", true))
  val userJson = serializeUserProfile(user)
  println(s"Serialized JSON: $userJson")

  val parsedUser = deserializeUserProfile(userJson)
  println(s"Deserialized user: $parsedUser")

  /////////////////////////////////////////////////////////////////////////////////////////

  val comp: List[Option[Int]] = Functor[List].compose[Option].map(List(Option(1)))(identity)
  val composedFunctor: Functor[({type λ[α] = List[Option[α]]})#λ] = Functor[List].compose[Option]

  type ListOption[A] = List[Option[A]]
  val alternativeComposedFunctor: Functor[ListOption] = Functor[List].compose[Option]
  alternativeComposedFunctor.map(List(Some(1)))(identity)



  //  implicit val forIO: Applicative[IO] =
//    new Applicative[IO] {
//      def zip[A, B](
//                     left: IO[A],
//                     right: IO[B]
//                   ): IO[(A, B)] = ??? //concurrently(left, right)
//    }
//
//  implicit def forEither[
//    E: Semigroup
//  ]: Applicative[Either[E, _]] =
//    new Applicative[Either[E, _]] {
//      def zip[A, B](
//                     left: Either[E, A],
//                     right: Either[E, B]
//                   ): Either[E, (A, B)] = ???  // merge(left, right)
//    }


}
