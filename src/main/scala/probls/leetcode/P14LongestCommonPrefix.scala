package probls.leetcode

object P14LongestCommonPrefix {
  def longestCommonPrefix(strs: Array[String]): String = {
    if(strs.isEmpty) return ""
    strs.foldLeft(strs.head){(acc, s) =>
      findSimilar(acc, s)
    }
  }

  def findSimilar(prefix: String, s: String): String = {
    if(prefix == "")
      ""
    else if(s.indexOf(prefix) != 0)
      findSimilar(prefix.dropRight(1), s)
    else
      prefix
  }
}
