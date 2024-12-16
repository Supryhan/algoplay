package problemslab.simple

import cats.Monoid

object WordsCounter extends App {

  sealed trait WC
  case class Stub(chars: String) extends WC
  case class Part(lStub: String, words: Int, rStub: String) extends WC

  val wcMonoid: Monoid[WC] = new Monoid[WC] {
    val empty: WC = Stub("")

    def combine(wc1: WC, wc2: WC): WC = (wc1, wc2) match {
      case (Stub(a), Stub(b)) => Stub(a + b)
      case (Stub(a), Part(l, w, r)) => Part(a + l, w, r)
      case (Part(l, w, r), Stub(a)) => Part(l, w, r + a)
      case (Part(l1, w1, r1), Part(l2, w2, r2)) =>
        Part(l1, w1 + (if ((r1 + l2).isEmpty) 0 else 1) + w2, r2)
    }
  }

  def foldMapV[A, B](as: IndexedSeq[A], m: Monoid[B])(f: A => B): B =
    if (as.isEmpty)
      m.empty
    else if (as.length == 1)
      f(as(0))
    else {
      val (l, r) = as.splitAt(as.length / 2)
      m.combine(foldMapV(l, m)(f), foldMapV(r, m)(f))
    }

  def count(s: String): Int = {
    // A single character's count. Whitespace does not count,
    // and non-whitespace starts a new Stub.
    def wc(c: Char): WC =
      if (c.isWhitespace)
        Part("", 0, "")
      else
        Stub(c.toString)

    def unstub(s: String): Int = if (s.isEmpty) 0 else 1

    foldMapV[Char, WC](s.toIndexedSeq, wcMonoid)(wc) match {
      case Stub(s) => unstub(s)
      case Part(l, w, r) => unstub(l) + w + unstub(r)
    }
  }

  println(s"Count: ${count("lorem ipsum dolor sit amet, ")}")

}
