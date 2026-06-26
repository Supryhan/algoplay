package com.supryhan.catslaboratory.problems

object ToyRsaDemo extends App {

  /*
   * Toy RSA encryption/decryption example.
   *
   * Given an original message M represented as a number, a public exponent E,
   * a private exponent D, and a modulus N, we demonstrate the basic RSA idea:
   *
   *   C = M^E mod N
   *
   * where C is the encrypted message, also called the ciphertext.
   *
   * Then we recover the original message by applying the private exponent:
   *
   *   M = C^D mod N
   *
   * In this example:
   *
   *   public key  = (E, N)
   *   private key = (D, N)
   *
   * The numbers are intentionally small and are used only for educational
   * purposes. Real RSA uses much larger keys, secure padding schemes, and
   * should be implemented through standard cryptographic libraries.
   */

  def check[A](taskName: String, actual: A, expected: A): Unit = {
    val status = if (actual == expected) "OK" else "FAIL"

    println()
    println(s"[$status] $taskName")
    println(s"  actual:   $actual")
    println(s"  expected: $expected")
  }

  def demonstrateToyRsa(encr: String => String, decr: String => String): String => String = {
    decr.compose(encr)
  }

  val e: BigInt = BigInt(17)
  val d: BigInt = BigInt(2753)
  val n: BigInt = BigInt(3233)


  val expected = "A"

  def customEncrypt(m: String): String = {
    val inputValue: BigInt = BigInt(m.head.toInt)
    val encrypted: BigInt = inputValue.modPow(e, n)
    encrypted.toString()
  }

  def customDencrypt(m: String): String = {
    val inputValue: BigInt = BigInt(m)
    val decrypted: BigInt = inputValue.modPow(d, n)
    decrypted.toInt.toChar.toString
  }

  def runTask1(): Unit = {
    check("Task 1 - text->encrypted->decrypted->text", demonstrateToyRsa(customEncrypt, customDencrypt)(expected), expected)
  }

  runTask1()

}
