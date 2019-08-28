package mycats

final case class Box[A](value: A)

trait Printable[A] {
  self =>

  def format(value: A): String

  def contramap[B](func: B => A): Printable[B] =
    new Printable[B] {
      def format(value: B): String =
        self.format(func(value))
    }
}

object Print extends App {
  def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)

  implicit val stringPrintable: Printable[String] =
    new Printable[String] {
      def format(value: String): String =
        "\"" + value + "\""
    }
  implicit val booleanPrintable: Printable[Boolean] =
    new Printable[Boolean] {
      def format(value: Boolean): String =
        if (value) "yes" else "no"
    }

  //  implicit def boxPrintable[A](implicit p: Printable[A]): Printable[Box[A]] =
  //    new Printable[Box[A]] {
  //      def format(box: Box[A]): String =
  //        p.format(box.value)
  //    }

  implicit def boxPrintable[A](implicit p: Printable[A]): Printable[Box[A]] =
    p.contramap[Box[A]](box => box.value)

  format("hello")
  format(true)
  format(Box("hello world"))
  format(Box(true))
}