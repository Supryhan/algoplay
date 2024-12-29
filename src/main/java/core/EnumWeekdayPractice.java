package core;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class EnumWeekdayPractice {
  public static void main(String[] args) {

    DayOfWeek localDate = LocalDate.now().getDayOfWeek();
    System.out.println("Is today weekend? Result: " + JavaWeekDay.isWeekend(JavaWeekDay.fromLocalDate(localDate)));

  }
}

enum JavaWeekDay {
  MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

  public static boolean isWeekend(JavaWeekDay javaWeekDay) {
    return javaWeekDay == SATURDAY || javaWeekDay == SUNDAY;
  }

  public static JavaWeekDay fromLocalDate(DayOfWeek dayOfWeek) {
    switch (dayOfWeek) {
      case MONDAY:
        return MONDAY;
      case TUESDAY:
        return TUESDAY;
      case WEDNESDAY:
        return WEDNESDAY;
      case THURSDAY:
        return THURSDAY;
      case FRIDAY:
        return FRIDAY;
      case SATURDAY:
        return SATURDAY;
      case SUNDAY:
        return SUNDAY;
      default:
        throw new IllegalArgumentException("Error in " + dayOfWeek);
    }
  }

}

