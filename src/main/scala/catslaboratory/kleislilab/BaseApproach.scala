package catslaboratory.kleislilab

import cats.data.Kleisli
import cats.effect.IO
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging

object BaseApproach extends App with LazyLogging {

  type KleisliIO[A, B] = Kleisli[IO, A, B]

  val r = scala.util.Random

  val generate: Unit => Int = _ => r.nextInt(100)
  val process: Int => Int = v => (v * math.Pi).toInt
  val save: Int => Boolean = _ => true

  val generated: Int = generate()
  val processed: Int = process(generated)
  val saved: Boolean = save(processed)

  logger.info(s"Result 0 is: $saved")

  val combine_1: Unit => Boolean = _ => save(process(generate()))
  logger.info(s"Result 1 is: ${combine_1()}")

  val combine_2: Unit => Boolean = save compose process compose generate
  logger.info(s"Result 2 is: ${combine_2()}")
  //this is a bit difficult to read too as we are used to read from left to right

  //andThen version
  val combine_3: Unit => Boolean =
    generate
      .andThen(process)
      .andThen(save)
  logger.info(s"Result 3 is: ${combine_3()}")

  val generateIO: Unit => IO[Int] = _ => IO.pure(r.nextInt(100))
  val processIO: Int => IO[Double] = num => IO.pure(num * math.Pi)
  val saveIO: Double => IO[Boolean] = number => IO.pure(true)

  val kleisliCombine_1: KleisliIO[Unit, Boolean] = {
    val generateK: KleisliIO[Unit, Int] = Kleisli(generateIO)
    val processK: KleisliIO[Int, Double] = Kleisli(processIO)
    val saveK: KleisliIO[Double, Boolean] = Kleisli(saveIO)
    generateK andThen processK andThen saveK
  }
  logger.info(s"Kleilis example 1: ${kleisliCombine_1.run(()).unsafeRunSync()}")

  val kleisliCombine_2: Kleisli[IO, Unit, Boolean] = Kleisli(generateIO) >>> Kleisli(processIO) >>> Kleisli(saveIO)
  logger.info(s"Kleislis example 2: ${kleisliCombine_2.run(()).unsafeRunSync()}")

  val kleisliCombine_3: Kleisli[IO, Unit, Boolean] = Kleisli(generateIO)
    .andThen(processIO)
    .andThen(saveIO)
  logger.info(s"Kleislis example 3: ${kleisliCombine_3.run(()).unsafeRunSync()}")

  val optionStr = Kleisli { x: Int => Option(x.toString) }
  val optionDouble = Kleisli { s: String => Option(s.toDouble) }
  val result1: Kleisli[Option, Int, Double] = optionStr.andThen(optionDouble)
  println(s"Result: ${
    result1.run(42) match {
      case Some(value) => value
      case None => "Error"
    }
  }")

  val result2: Int => Option[Double] = (i: Int) => for {
    s <- optionStr.run(i)
    d <- optionDouble.run(s)
  } yield d
  println(s"Result: ${
    result2(43) match {
      case Some(value) => value
      case None => "Error"
    }
  }")

}
