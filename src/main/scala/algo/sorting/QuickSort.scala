package algo.sorting

object QuickSort extends App {

  def quicksort[A: Ordering](ls: List[A]): List[A] = {
    import Ordered._

    def sort(ls: List[A])(parent: List[A]): List[A] = {
      if (ls.size <= 1) ls ::: parent
      else {
        val pivot = ls.head
        val (less, equal, greater) = ls.foldLeft((List[A](), List[A](), List[A]())) {
          case ((less, equal, greater), e) =>
            if (e < pivot) (e :: less, equal, greater)
            else if (e == pivot) (less, e :: equal, greater)
            else (less, equal, e :: greater)
        }
        sort(less)(equal ::: sort(greater)(parent))
      }
    }

    sort(ls)(Nil)
  }


  val unsortedList = List(3, 6, 8, 10, 1, 2, 1, 12, 56, 1, 2, 3, 4, 5, 6, 7, 0)
  val sortedList = quicksort(unsortedList)
  println(sortedList)
}


object CounterExample extends App {

  class Counter {
    @volatile var counter = 0
    def incrementCounter(): Unit = synchronized {
      // delay for simulation
      val currentValue = counter
      println(s"Thread ${Thread.currentThread().getName} reads counter: $currentValue")
      Thread.sleep(100) // dalay for race condition
      counter = currentValue + 1
      println(s"Thread ${Thread.currentThread().getName} increments counter to: ${counter}")
    }
  }

  val counter = new Counter()

  val thread1 = new Thread(() => counter.incrementCounter(), "Thread-1")
  val thread2 = new Thread(() => counter.incrementCounter(), "Thread-2")
  val thread3 = new Thread(() => counter.incrementCounter(), "Thread-3")

  thread1.start()
  thread2.start()
  thread3.start()

  thread1.join()
  thread2.join()
  thread3.join()

  println(s"Final counter value: ${counter.counter}")
}
