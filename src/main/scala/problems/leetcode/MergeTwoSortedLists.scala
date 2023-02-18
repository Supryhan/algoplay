package problems.leetcode

object MergeTwoSortedLists extends App {

  class ListNode(_x: Int = 0, _next: ListNode = null) {
    var next: ListNode = _next
    var x: Int = _x
    override def toString(): String = {
      val nxt =
        if(next == null)
          "null"
        else
          next.toString()
      s"ListNode($x, $nxt})"
    }
  }

  def mergeTwoLists(list1: ListNode, list2: ListNode): ListNode = {
    def merge(current: ListNode, list1: ListNode, list2: ListNode): ListNode = {
      if(list1 == null && list2 == null)
        current
      else if(list2 == null) {
        current.next = list1
        current
      } else if(list1 == null) {
        current.next = list2
        current
      } else if(list1.x < list2.x){
        val n = new ListNode(list1.x)
        current.next = n
        merge(n, list1.next, list2)
      } else if(list2.x < list1.x){
        val n = new ListNode(list2.x)
        current.next = n
        merge(n, list1, list2.next)
      } else {
        val n1 = new ListNode(list1.x)
        val n2 = new ListNode(list2.x)
        current.next = n1
        current.next.next = n2
        merge(n2, list1.next, list2.next)
      }
    }
    val initNode = new ListNode()
    merge(initNode, list1, list2)

    if(list1 == null && list2 == null)
      null
    else
      initNode.next
  }

  val l1 = new ListNode(-1, new ListNode(2, new ListNode(4, null)))
  val l2 = new ListNode(-3, new ListNode(0, new ListNode(4, new ListNode(5, null))))


  val result = mergeTwoLists(l1, l2)

  println(s">>>merge:${if(result == null) null else result}<<<")

}
