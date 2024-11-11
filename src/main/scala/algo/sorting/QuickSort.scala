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
