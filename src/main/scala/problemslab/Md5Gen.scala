package problemslab

import java.math.BigInteger
import java.security.MessageDigest

object Md5Gen extends App {
  def md5gen(str: String): String =
    new BigInteger(1, MessageDigest
      .getInstance("MD5")
      .digest(str.getBytes)
    ).toString(16)

  println(s"Result: ${md5gen("Hello World!")}")
}
