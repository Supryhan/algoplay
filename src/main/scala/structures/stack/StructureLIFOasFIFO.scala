package structures.stack

object Main extends App {
  // Make use:
  val queue = new StructureLIFOasFIFO[Int]
  queue.enqueue(1)
  queue.enqueue(2)
  queue.enqueue(3)
  println(queue.dequeue()) // Output Some(1)
  println(queue.dequeue()) // Output Some(2)
  queue.enqueue(4)
  println(queue.dequeue()) // Output Some(3)
  println(queue.dequeue()) // Output Some(4)
  println(queue.dequeue()) // Output None

}
class StructureLIFOasFIFO[T] {
  private var inStack: List[T] = Nil
  private var outStack: List[T] = Nil

  def enqueue(element: T): Unit = {
    inStack = element :: inStack
  }

  def dequeue(): Option[T] = {
    outStack match {
      case head :: tail =>
        outStack = tail
        Some(head)
      case Nil =>
        transfer()
        outStack match {
          case head :: tail =>
            outStack = tail
            Some(head)
          case Nil => None
        }
    }
  }

  private def transfer(): Unit = {
//    outStack = inStack.reverse
    while (inStack.nonEmpty) {
      inStack match {
        case head :: tail =>
          outStack = head :: outStack
          inStack = tail
      }
    }
  }
}