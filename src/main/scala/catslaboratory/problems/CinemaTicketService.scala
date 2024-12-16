package catslaboratory.problems

import cats.data.OptionT

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}


object CinemaTicketService extends App {

  case class CinemaUser(firstName: Option[String], lastName: Option[String])

  case class CinemaTicket(number: String, user: CinemaUser)

  def getUser(firstName: String, LastName: String): CinemaUser = {
    CinemaUser(
      if (firstName.isBlank) None else Some(firstName),
      if (LastName.isBlank) None else Some(LastName)
    )
  }

  def getTicketNumber(user: CinemaUser): Option[String] = {
    Option
      .when[String](
        user.firstName.isDefined && user.lastName.isDefined)(
        s"${user.firstName.get}${user.lastName.get}"
      )
  }

  def getTicket(number: String): OptionT[Future, String] = OptionT.some[Future](number)

  val e = (for {
    u <- OptionT.some[Future](getUser("name1", "name2"))
    n <- OptionT.fromOption[Future](getTicketNumber(u))
    t <- getTicket(n)
  } yield t).value

  e.onComplete {
    case Success(Some(value)) => println(s"The ticket is: $value")
    case Success(None) => println("The ticket cannot be generated")
    case Failure(exception) => s"We get error: ${exception.getMessage}"
  }
}
