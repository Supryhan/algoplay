package core

object TrafficLightExample extends App {

  import TrafficLightColorEnum._
  import PartOfDay._

  def actionForColor(color: TrafficLightColor): String = color match {
    case Red => "Stop"
    case Yellow => "Get Ready"
    case Green => "Go"
  }

  def actionForPartOfDay(part: PartOfDay.Value): String = part match {
    case Day => "Day"
    case Night => "Night"
  }

  val currentColor = Red
  println(s"Color: $currentColor, Action: ${actionForColor(currentColor)}")

  val nextColor = Green
  println(s"Color: $nextColor, Action: ${actionForColor(nextColor)}")

  val hello = Day
  println(s"Good morning it is: ${actionForPartOfDay(hello)}")

  val e1 = PartOfDay.withName("Day")
  val e2 = PartOfDay(1)
  println(s"$e1 & $e2")
}


object TrafficLightColorEnum extends Enumeration {
  type TrafficLightColor = Value
  val Red, Yellow, Green = Value
}

object PartOfDay extends Enumeration {
  val Day = Value
  val Night = Value
}

