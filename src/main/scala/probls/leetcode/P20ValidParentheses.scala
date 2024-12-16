package probls.leetcode

import scala.annotation.tailrec

object P20ValidParentheses {
  def isValid(s: String): Boolean = {
    val result = clearing(s.toList, List.empty[Char])
    if(result.isEmpty)
      true
    else
      false
  }

  @tailrec
  def clearing(list: List[Char], acc: List[Char]): List[Char] = {
    if(list.isEmpty)
      return acc
    val head = list.head
    if(list.isEmpty)
      acc
    else if(acc.isEmpty && (head == '(' || head == '{' || head == '['))
      clearing(list.tail, head :: acc)
    else if(acc.isEmpty && (head == ')' || head == '}' || head == ']'))
      list
    else if(acc.nonEmpty && (acc.head == '(' || acc.head == '{' || acc.head == '[') && (head == '(' || head == '{' || head == '['))
      clearing(list.tail, head :: acc)
    else if(acc.nonEmpty && ((acc.head == '(' && head == ')') || (acc.head == '{' && head == '}') || (acc.head == '[' && head == ']')))
      clearing(list.tail, acc.tail)
    else
      list
  }
}
