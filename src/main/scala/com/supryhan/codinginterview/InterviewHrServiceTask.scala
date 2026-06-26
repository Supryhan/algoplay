package com.supryhan.codinginterview

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

object InterviewHrServiceTask extends App {

  case class PersonalInfo(
    firstName: Option[String],
    middleName: Option[String],
    lastName: Option[String])

  case class PersonalInfoRecord(id: Int, personalInfo: PersonalInfo)

  object RemoteService {
    val personalInfo: List[PersonalInfoRecord] = List(
      PersonalInfoRecord(1, PersonalInfo(Some("Sam"), Some("A"), Some("Dou"))),
      PersonalInfoRecord(2, PersonalInfo(Some("Gary"), None, Some("Green"))),
      PersonalInfoRecord(3, PersonalInfo(Some("Alex"), Some("B"), Some("Farrel"))),
      PersonalInfoRecord(3, PersonalInfo(Some("Dany"), Some("C"), Some("Cole")))
    )

    def getUserInfo(id: Int)(f: Int => Unit): Boolean = {
      f(id)
      true
    }
  }

  def convertPersonalInfo(id: Int): Future[Option[String]] = {

    Future.successful {
      val person: Option[PersonalInfoRecord] = RemoteService.personalInfo.find(i => i.id == id)

      person.map { p: PersonalInfoRecord => {
        val firstName = p.personalInfo.firstName.fold("`Unknown first name`")(identity)
        val middleName = p.personalInfo.middleName.fold("`Unknown middle name`")(identity)
        val lastName = p.personalInfo.lastName.fold("`Unknown last name`")(identity)
        s"$firstName $middleName $lastName"
      }
      }
    }
  }

  val program: Future[Unit] =
    Future
      .sequence((1 to 10).map(convertPersonalInfo))
      .map(values => values.flatten.foreach(println))

  Await.result(program, 5.seconds)
}

