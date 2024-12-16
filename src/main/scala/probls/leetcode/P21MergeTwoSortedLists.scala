package probls.leetcode

object P21MergeTwoSortedLists extends App {

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

  def mergeTwoLists(startNodeA: ListNode, startNodeB: ListNode): ListNode = {
    def merge(currentNode: ListNode, nodeFromListA: ListNode, nodeFromListB: ListNode): ListNode = {
      if(nodeFromListA == null && nodeFromListB == null)
        currentNode
      else if(nodeFromListB == null) {
        currentNode.next = nodeFromListA
        currentNode
      } else if(nodeFromListA == null) {
        currentNode.next = nodeFromListB
        currentNode
      } else if(nodeFromListA.x < nodeFromListB.x){
        currentNode.next = nodeFromListA
        merge(nodeFromListA, nodeFromListA.next, nodeFromListB)
      } else {
        currentNode.next = nodeFromListB
        merge(nodeFromListB, nodeFromListA, nodeFromListB.next)
      }
    }
    val initNode = new ListNode()
    merge(initNode, startNodeA, startNodeB)

    if(startNodeA == null && startNodeB == null)
      null
    else
      initNode.next
  }

  val listA = new ListNode(-1, new ListNode(2, new ListNode(4, null)))
  val listB = new ListNode(-3, new ListNode(0, new ListNode(4, new ListNode(5, null))))


  val result = mergeTwoLists(listA, listB)

  println(s">>>merge:${if(result == null) null else result}<<<")

}
