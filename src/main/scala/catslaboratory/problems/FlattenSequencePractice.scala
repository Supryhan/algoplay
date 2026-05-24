package catslaboratory.problems

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Try

/**
 * Practice file for Scala nested structures:
 *
 *   - Option
 *   - Either
 *   - Future
 *   - map
 *   - flatMap
 *   - flatten
 *   - Future.sequence
 *   - manual sequence for Option and Either
 *
 * Suggested file name:
 *   FlattenSequencePractice.scala
 *
 * Suggested object name:
 *   FlattenSequencePractice
 *
 * How to use this file:
 *   1. Change the package name at the top of the file.
 *   2. Run this object as a normal Scala application.
 *   3. Each task currently contains a TODO implementation.
 *   4. Replace the placeholder implementations with real code.
 *   5. Re-run the file and compare actual results with expected results.
 */
object FlattenSequencePractice extends App {

  def check[A](taskName: String, actual: A, expected: A): Unit = {
    val status = if (actual == expected) "OK" else "FAIL"

    println()
    println(s"[$status] $taskName")
    println(s"  actual:   $actual")
    println(s"  expected: $expected")
  }

  // ---------------------------------------------------------------------------
  // Task 1. Extract existing values from List[Option[String]]
  // ---------------------------------------------------------------------------

  /**
   * Input:
   *   List(Some("Sam"), None, Some("Gary"), Some("Alex"), None)
   *
   * Expected:
   *   List("Sam", "Gary", "Alex")
   *
   * Goal:
   *   Understand that List[Option[A]].flatten removes None
   *   and extracts values from Some(value).
   */
  val task1Names: List[Option[String]] =
    List(Some("Sam"), None, Some("Gary"), Some("Alex"), None)

  def task1UsingFlatten(names: List[Option[String]]): List[String] = {
    // TODO: Replace this placeholder with names.flatten
    Nil
  }

  def task1UsingFlatMapIdentity(names: List[Option[String]]): List[String] = {
    // TODO: Replace this placeholder with names.flatMap(identity)
    Nil
  }

  def task1UsingCollect(names: List[Option[String]]): List[String] = {
    // TODO: Replace this placeholder with:
    //   names.collect { case Some(value) => value }
    Nil
  }

  def runTask1(): Unit = {
    val expected = List("Sam", "Gary", "Alex")

    check("Task 1.1 - List[Option[String]].flatten", task1UsingFlatten(task1Names), expected)
    check("Task 1.2 - List[Option[String]].flatMap(identity)", task1UsingFlatMapIdentity(task1Names), expected)
    check("Task 1.3 - List[Option[String]].collect", task1UsingCollect(task1Names), expected)
  }

  // ---------------------------------------------------------------------------
  // Task 2. Parse numbers and ignore invalid values
  // ---------------------------------------------------------------------------

  /**
   * Input:
   *   List("10", "abc", "25", "", "7", "x")
   *
   * Expected:
   *   List(10, 25, 7)
   *
   * Goal:
   *   Practice:
   *
   *     values.map(f).flatten
   *
   *   and then rewrite it as:
   *
   *     values.flatMap(f)
   */
  val task2RawNumbers: List[String] =
    List("10", "abc", "25", "", "7", "x")

  def parseIntSafe(value: String): Option[Int] = {
    // TODO: Convert String to Int safely.
    // Hint:
    //   Try(value.toInt).toOption
    None
  }

  def task2UsingMapThenFlatten(rawNumbers: List[String]): List[Int] = {
    val parsed: List[Option[Int]] = rawNumbers.map(parseIntSafe)

    // TODO: Replace this placeholder with parsed.flatten
    Nil
  }

  def task2UsingFlatMap(rawNumbers: List[String]): List[Int] = {
    // TODO: Replace this placeholder with rawNumbers.flatMap(parseIntSafe)
    Nil
  }

  def runTask2(): Unit = {
    val expected = List(10, 25, 7)

    check("Task 2.1 - parse with map + flatten", task2UsingMapThenFlatten(task2RawNumbers), expected)
    check("Task 2.2 - parse with flatMap", task2UsingFlatMap(task2RawNumbers), expected)
  }

  // ---------------------------------------------------------------------------
  // Task 3. Collect results from several Future[Option[String]] values
  // ---------------------------------------------------------------------------

  /**
   * Existing users:
   *   1 -> "Sam"
   *   2 -> "Gary"
   *   3 -> "Alex"
   *
   * Input ids:
   *   1 to 10
   *
   * Expected:
   *   List("Sam", "Gary", "Alex")
   *
   * Goal:
   *   Understand the transformation:
   *
   *     IndexedSeq[Future[Option[String]]]
   *
   *   into:
   *
   *     Future[IndexedSeq[Option[String]]]
   *
   *   through Future.sequence.
   *
   *   Then use flatten on IndexedSeq[Option[String]].
   */
  val task3Users: Map[Int, String] =
    Map(
      1 -> "Sam",
      2 -> "Gary",
      3 -> "Alex"
    )

  def getUserName(id: Int): Future[Option[String]] =
    Future.successful(task3Users.get(id))

  def task3FetchExistingNames(ids: Range): Future[List[String]] = {
    val futures: IndexedSeq[Future[Option[String]]] =
      ids.map(getUserName)

    val sequenced: Future[IndexedSeq[Option[String]]] =
      Future.sequence(futures)

    sequenced.map { values: IndexedSeq[Option[String]] =>
      // TODO: Replace this placeholder with values.flatten.toList
      List.empty[String]
    }
  }

  def runTask3(): Unit = {
    val actual: List[String] =
      Await.result(task3FetchExistingNames(1 to 10), 5.seconds)

    val expected = List("Sam", "Gary", "Alex")

    check("Task 3 - Future.sequence + flatten", actual, expected)
  }

  // ---------------------------------------------------------------------------
  // Task 4. Strict sequence for Option: all or nothing
  // ---------------------------------------------------------------------------

  /**
   * There are two different ideas:
   *
   * 1. flatten for List[Option[A]]
   *
   *    Example:
   *      List(Some("host"), Some("port"), None).flatten
   *
   *    Result:
   *      List("host", "port")
   *
   * 2. sequence for List[Option[A]]
   *
   *    This means all or nothing.
   *    If at least one element is None, the whole result is None.
   *
   *    Example:
   *      sequenceOptions(List(Some("host"), Some("port"), None))
   *
   *    Result:
   *      None
   *
   *    Example:
   *      sequenceOptions(List(Some("host"), Some("port"), Some("user")))
   *
   *    Result:
   *      Some(List("host", "port", "user"))
   */
  case class AppConfig(host: String, port: Int, user: String)

  def buildConfig(host: Option[String], port: Option[Int], user: Option[String]): Option[AppConfig] = {
    // TODO: Replace this placeholder with a for-comprehension.
    //
    // Hint:
    //   for {
    //     h <- host
    //     p <- port
    //     u <- user
    //   } yield AppConfig(h, p, u)
    None
  }

  def flattenOptions[A](values: List[Option[A]]): List[A] = {
    // TODO: Replace this placeholder with values.flatten
    Nil
  }

  def sequenceOptions[A](values: List[Option[A]]): Option[List[A]] = {
    // TODO: Implement strict all-or-nothing sequence.
    //
    // Hint:
    //   values.foldRight(Option(List.empty[A])) { (current, accumulated) =>
    //     for {
    //       value <- current
    //       result <- accumulated
    //     } yield value :: result
    //   }
    None
  }

  def runTask4(): Unit = {
    val config1 = buildConfig(Some("localhost"), Some(8080), Some("admin"))
    val config2 = buildConfig(Some("localhost"), Some(8080), None)

    check(
      "Task 4.1 - build AppConfig when all values are present",
      config1,
      Some(AppConfig("localhost", 8080, "admin"))
    )

    check(
      "Task 4.2 - fail AppConfig when one value is missing",
      config2,
      None
    )

    val valuesWithMissingValue: List[Option[String]] =
      List(Some("host"), Some("port"), None)

    val valuesWithoutMissingValue: List[Option[String]] =
      List(Some("host"), Some("port"), Some("user"))

    check(
      "Task 4.3 - flatten List[Option[String]] and ignore None",
      flattenOptions(valuesWithMissingValue),
      List("host", "port")
    )

    check(
      "Task 4.4 - sequence List[Option[String]] with None",
      sequenceOptions(valuesWithMissingValue),
      None
    )

    check(
      "Task 4.5 - sequence List[Option[String]] without None",
      sequenceOptions(valuesWithoutMissingValue),
      Some(List("host", "port", "user"))
    )
  }

  // ---------------------------------------------------------------------------
  // Task 5. Validate values with Either and collect successful results
  // ---------------------------------------------------------------------------

  /**
   * Input:
   *   List("20", "17", "abc", "35")
   *
   * Validation rules:
   *   - if the value is not a number, return Left("Not a number: <value>")
   *   - if the number is less than 18, return Left("Too young: <value>")
   *   - otherwise return Right(age)
   *
   * Part 1:
   *   Collect only valid ages and ignore errors.
   *
   * Expected:
   *   List(20, 35)
   *
   * Part 2:
   *   Implement strict sequence for Either.
   *   If at least one element is Left(error), the whole result is Left(error).
   *
   * Expected for List("20", "17", "abc", "35"):
   *   Left("Too young: 17")
   */
  val task5RawAges: List[String] =
    List("20", "17", "abc", "35")

  def validateAge(value: String): Either[String, Int] = {
    // TODO: Implement validation.
    //
    // Hint:
    //   Try(value.toInt).toOption match {
    //     case None => Left(s"Not a number: $value")
    //     case Some(age) if age < 18 => Left(s"Too young: $age")
    //     case Some(age) => Right(age)
    //   }
    Left("TODO")
  }

  def collectValidAges(rawAges: List[String]): List[Int] = {
    // TODO: Replace this placeholder with:
    //   rawAges.map(validateAge).collect { case Right(age) => age }
    Nil
  }

  def sequenceEither[E, A](values: List[Either[E, A]]): Either[E, List[A]] = {
    // TODO: Implement strict all-or-nothing sequence for Either.
    //
    // Hint:
    //   values.foldRight(Right(List.empty[A]): Either[E, List[A]]) { (current, accumulated) =>
    //     for {
    //       value <- current
    //       result <- accumulated
    //     } yield value :: result
    //   }
    Right(Nil)
  }

  def traverseEither[E, A, B](values: List[A])(f: A => Either[E, B]): Either[E, List[B]] = {
    // TODO: Replace this placeholder with:
    //   sequenceEither(values.map(f))
    Right(Nil)
  }

  def runTask5(): Unit = {
    check(
      "Task 5.1 - collect only valid ages",
      collectValidAges(task5RawAges),
      List(20, 35)
    )

    check(
      "Task 5.2 - sequence Either values and stop on first error",
      traverseEither(task5RawAges)(validateAge),
      Left("Too young: 17")
    )
  }

  // ---------------------------------------------------------------------------
  // Run all tasks
  // ---------------------------------------------------------------------------

  runTask1()
  runTask2()
  runTask3()
  runTask4()
  runTask5()
}