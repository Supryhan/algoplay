package problemslab.leetcode

object P28FindTheIndex1stOccurrenceString extends App {
  def strStr(haystack: String, needle: String): Int = {
    var index = 0
    val needleLength = needle.length
    var result = -1

    while(index < haystack.length - needleLength && haystack.substring(index, index + needleLength) != needle) {
      index += 1
    }

    if(haystack.length < needleLength) {
      result = -1
    } else if(index <= haystack.length - needleLength && haystack.substring(index, index + needleLength) == needle) {
      result = index
    } else if (index == 0 && haystack.substring(index, index + needleLength) == needle) {
      result = 0
    }
    result
  }

  println(strStr("sadbutsad", "sad"))
  println(strStr("sadbutsad", "s1ad"))
  println(strStr("qwertysadbutsad", "sad"))
  println(strStr("aa", "aa"))
  println(strStr("aa", "aaa"))
  println(strStr("aaaa", "baaa"))
  println(strStr("abc", "c"))



}
