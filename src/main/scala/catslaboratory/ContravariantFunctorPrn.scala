package catslaboratory

private[catslaboratory] final case class Box[A](value: A)

private[catslaboratory] trait Printable[A] {
  self =>

  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] = (value: B) => self.format(func(value))
//  def contramap[B](func: B => A): Printable[B] =
//    new Printable[B] {
//      def format(value: B): String =
//        self.format(func(value))
//    }
}

private[catslaboratory] object Print extends App {
  def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)

  implicit val stringPrintable: Printable[String] = (value: String) => "\"" + value + "\""
  implicit val booleanPrintable: Printable[Boolean] = (value: Boolean) => if (value) "yes" else "no"

  implicit def boxPrintable[A](implicit p: Printable[A]): Printable[Box[A]] = p.contramap[Box[A]](box => box.value)

  //  implicit def boxPrintable[A](implicit p: Printable[A]): Printable[Box[A]] =
  //    new Printable[Box[A]] {
  //      def format(box: Box[A]): String =
  //        p.format(box.value)
  //    }

  println(format("hello"))
  println(format(true))
  println(format(Box("hello world")))
  println(format(Box(true)))
}