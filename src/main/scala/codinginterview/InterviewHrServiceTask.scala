package codinginterview

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object InterviewHrServiceTask extends App {

  case class PersonalInfo(firstName: Option[String],
                          middleName: Option[String],
                          lastName: Option[String])

  object RemoteService {
    val personalInfo: List[(Int, PersonalInfo)] = Seq(
      (1, PersonalInfo(Some("Sam"), Some("A"), Some("Dou"))),
      (2, PersonalInfo(Some("Gary"), None, Some("Green"))),
      (3, PersonalInfo(Some("Alex"), Some("B"), Some("Farrel"))),
      (3, PersonalInfo(Some("Dany"), Some("C"), Some("Cole")))
    ).toList

    def getUserInfo(id: Int)(f: Int => Unit): Boolean = {
      f(id)
      true
    }
  }

  def convertPersonalInfo(id: Int): Future[Option[String]] = {

    Future.successful {
      val person: Option[(Int, PersonalInfo)] = RemoteService.personalInfo.find(i => i._1 == id)

      person.map { p: (Int, PersonalInfo) => {
        val firstName = p._2.firstName.fold("`Unknown first name`")(identity)
        val middleName = p._2.middleName.fold("`Unknown middle name`")(identity)
        val lastName = p._2.lastName.fold("`Unknown last name`")(identity)
        s"$firstName $middleName $lastName"
      }
      }
    }
  }

  for (index <- 1 to 10) {

    convertPersonalInfo(index).onComplete {
      case Success(value) => value.foreach(println)
      case Failure(e) => println(e.getStackTrace)
    }
    Thread.sleep(200)
  }
}

