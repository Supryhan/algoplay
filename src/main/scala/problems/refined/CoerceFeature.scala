package problems.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.Contains
import io.estatico.newtype.macros._
import eu.timepit.refined.api.RefinedTypeOps
import eu.timepit.refined.numeric.Greater

object CoerceFeature extends App {


  type Username = String Refined Contains['g']
  @newtype case class Email(value: String)
  @newtype case class User(name: Username, email: Email)
  def lookup[F[_]](username: Username): F[Option[User]] = ???


  type GTFive = Int Refined Greater[5]
  object GTFive extends RefinedTypeOps[GTFive, Int]
  val number: Int = 33
  val res: Either[String, GTFive] = GTFive.from(number)

}
