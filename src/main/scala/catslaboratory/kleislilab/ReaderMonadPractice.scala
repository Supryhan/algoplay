package catslaboratory.kleislilab
import cats.data.{Reader, ReaderT}
import cats.effect.IO
import cats.effect.unsafe.implicits.global

object ReaderMonadPractice extends App {
  type Name = String
  case class Bread(name: String)
  case class Cat(breed: Bread, name: Name)
  val catReader: ReaderT[IO, Cat, Name] = ReaderT[IO, Cat, Name](cat => IO.pure(cat.name))
  val catReaderUppercase: ReaderT[IO, Name, Name] = ReaderT[IO, Name, Name](name => IO.pure(name.toUpperCase()))
  val result = catReader
    .andThen(catReaderUppercase)
    .run(Cat(Bread("Otocolobus"), "garfield"))
  println(result.unsafeRunSync())
}
