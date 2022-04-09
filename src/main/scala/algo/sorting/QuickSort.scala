package algo.sorting

object QuickSort extends App {

  def quicksort[A: Ordering](ls: List[A]): List[Any] = {
    import Ordered._

    def sort(ls: List[A])(parent: List[A]): List[A] = {
      if (ls.size <= 1) ls ::: parent else {
        val pivot = ls.head
        val (less, equal, greater) = ls
          .foldLeft((List[A](), List[A](), List[A]())) {
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

  println(s"Sorted list: ${quicksort(List(12, 56, 1, 2, 3, 4, 5, 6, 7, 0))}")
}
