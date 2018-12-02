package implicits

object Conversion extends App {
  //
  //  class A(val n: Int)
  //
  //  class B(val m: Int, val n: Int)
  //
  //  class C(val m: Int, val n: Int, val o: Int) {
  //    def total = m + n + o
  //  }
  //
  //  implicit def toA(n: Int): A = new A(n)
  //
  //  implicit def aToB[A1](a: A1)(implicit f: A1 => A): B =
  //    new B(a.n, a.n)
  //
  //  implicit def bToC[B1](b: B1)(implicit f: B1 => B): C =
  //    new C(b.m, b.n, b.m + b.n)
  //
  //  // works
  //  println(5.total)
  //  println(new A(5).total)
  //  println(new B(5, 5).total)
  //  println(new C(5, 5, 10).total)

  print("------------------")

  //  implicit def StrToSeq(srt: String): List[String] = {
  //    srt.toList.map(c => c.toString)
  //  }
  //
  //  def getIndex[T, CC](seq: CC, value: T)(implicit conv: CC => List[T]) = seq.indexOf(value)
  //
  //  getIndex("abc", 'a')

  println("---------------------")
//
//  val result = for {
//    l <- List("jhdfjdg djfhdjhgf djhjdhgj dfdj dfd jhejhdjfhdj dfjd")
//    c <- l.split(" ")
//    if c.length < 5
//  } yield c
//
//  println(result)
//
//  val result2 =
//
//    List("jhdfjdg djfhdjhgf djhjdhgj dfdj dfd jhejhdjfhdj dfjd")
//      .flatMap(l =>
//        l.split(" ")
//          .withFilter(_.length < 5)
//          .map(c => c))
//
//  println(result2)
  print("----------")
  val states = Set("Alabama", "Alaska", "Wyoming")
  val lengths = states map (st => st.length)
  print(lengths)
}
