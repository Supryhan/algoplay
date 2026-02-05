package catslaboratory.problems

object URLShortenerBase64 {
  val alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
  val base = alphabet.length

  def encode(number: Long): String = {
    var n = number
    var encoded = ""
    while (n > 0) {
      encoded = alphabet.charAt((n % base).toInt) + encoded
      n /= base
    }
    if (encoded.isEmpty) alphabet.head.toString else encoded
  }

  def decode(url: String): Long = {
    url.foldLeft(0L) { (acc, char) =>
      acc * base + alphabet.indexOf(char)
    }
  }

  def main(args: Array[String]): Unit = {
    val number = 12345L
    val encoded = encode(number)
    println(s"Encoded: $encoded") // Перевіримо кодування

    val initial = decode(encoded)
    println(s"Decoded: $initial") // Перевіримо декодування
  }

}
