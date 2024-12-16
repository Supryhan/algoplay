package problemslab.rsa

object MyRsa extends App {
  val characters = Array[Char]('A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z')

  val p = 13
  val q = 17
  if(isTheNumberSimple(p) && isTheNumberSimple(q)) {
    val n = p * q
    val m = (p - 1) * (q - 1)
    val d = calculateD(m)
    val e = calculateE(d, m)
    val result = rsaEncode("text", 13, 17)
    println(s">>>$result<<<")
  } else {
    println(isTheNumberSimple(13))
    println(isTheNumberSimple(17))
    println("The numbers are not simple")
  }


  def rsaEncode(text: String, e: Int, n: Int): String =
    text.toList.map { s =>
      val i = characters.indexOf(s)
      val p = Math.pow(i, e)
      val bi = p.toInt % n
      bi.toChar
    }.toString

  def rsaDecode(input: String, d: Int, n: Int): String = "???"


  def calculateD(m: Int): Int = {
    def calcD(d: Int): Int = {
      for(i <- 2 until m) {
        if(d % i == 0 && m % i == 0)
          return calcD(d - 1)
      }
      d
    }
    calcD(m - 1)
  }

  def calculateE(d: Int, m: Int): Int = {
    val e = 10
    def calcE(e: Int): Int =
      if((e * d) % m == 1) e
      else calcE(e + 1)

    calcE(e)
  }

  def isTheNumberSimple(x: Int): Boolean =
    if(x < 2) {
      false
    } else if(x == 2) {
      true
    } else {
      for(i <- 2 until x)
        if(x % i == 0) return false
      true
    }


}
