package core

import java.time.{DayOfWeek, LocalDate}

object WeekdaysEnumPractice extends App {

  val dayNow = LocalDate.now().getDayOfWeek

  println(s"Is today weekend? Result: ${WeekDay.isWeekend(WeekDay.javaDayOfWeek(dayNow))}")

}


object WeekDay extends Enumeration {
  type WeekDay = Value
  val Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday = Value

  def javaDayOfWeek(dayOfWeek: DayOfWeek): WeekDay = dayOfWeek match {
    case DayOfWeek.MONDAY => Monday
    case DayOfWeek.TUESDAY => Tuesday
    case DayOfWeek.WEDNESDAY => Wednesday
    case DayOfWeek.THURSDAY => Thursday
    case DayOfWeek.FRIDAY => Friday
    case DayOfWeek.SATURDAY => Saturday
    case DayOfWeek.SUNDAY => Sunday
  }

  def isWeekend(day: WeekDay): Boolean = day match {
    case Saturday | Sunday => true
    case _ => false
  }
}
