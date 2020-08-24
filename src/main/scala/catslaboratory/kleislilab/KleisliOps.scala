package catslaboratory.kleislilab

import cats.data.Kleisli
import cats.effect.IO

object KleisliOps {
  type KleisliIO[A, B] = Kleisli[IO, A, B]

  implicit class CreateKleisliIOFromFunction1[A, B](f: A => B) {
    def toKleisliIO: KleisliIO[A, B] = KleisliIO.instance((x: A) => IO(f(x)))
  }

  object KleisliIO {
    def instance[A, B](f: A => IO[B]): KleisliIO[A, B] = new KleisliIO(f)
  }

}
