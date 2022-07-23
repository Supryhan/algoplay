package catslaboratory.kleislilab
import cats.data.{Reader, ReaderT}
import cats.effect.IO
import cats.effect.unsafe.implicits.global

object ReaderMonadPractice extends App {
  type Name = String
  case class Bread(name: String)
  case class Cat(breed: Bread, name: Name)
  val cat1 = ReaderT[IO, Cat, Name]{cat => IO.pure(cat.name)}
  val result = cat1.andThen(name => IO.pure(name.toUpperCase())).run(Cat(Bread("Otocolobus"), "garfield"))
  println(result.unsafeRunSync())
}
