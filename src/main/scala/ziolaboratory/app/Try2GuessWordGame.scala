package ziolaboratory.app

import zio.Console.{printLine, readLine}
import zio.Random.nextIntBounded
import zio.{Console, ExitCode, Random, URIO, ZIO, ZIOAppDefault}
import java.io.IOException

object Try2GuessWordGame extends ZIOAppDefault {

  def run: ZIO[Any, Nothing, Unit] = {
    for {
      _ <- printLine("Welcome to ZIO Hangman!")
//      name <- getName
//      word <- chooseWord
//      _ <- gameLoop(State.initial(name, word))
    } yield ()
  }.orDie


//  sealed abstract case class Name private (name: String)
//  object Name {
//    def make(name: String): Option[Name] =
//      if (name.nonEmpty) Some(new Name(name) {}) else None
//  }
//
//  sealed abstract case class Guess private (char: Char)
//  object Guess {
//    def make(str: String): Option[Guess] =
//      Some(str.toList).collect { case c :: Nil if c.isLetter => new Guess(c.toLower) {} }
//  }
//
//  sealed abstract case class Word private (word: String) {
//    val length: Int                   = word.length
//    def contains(char: Char): Boolean = word.contains(char)
//    def toList: List[Char]            = word.toList
//    def toSet: Set[Char]              = word.toSet
//  }
//  object Word {
//    def make(word: String): Option[Word] =
//      if (word.nonEmpty && word.forall(_.isLetter)) Some(new Word(word.toLowerCase) {}) else None
//  }
//
//  sealed abstract case class State private (name: Name, guesses: Set[Guess], word: Word) {
//    def failuresCount: Int  = (guesses.map(_.char) -- word.toSet).size
//    def playerLost: Boolean = failuresCount > 5
//    def playerWon: Boolean  = (word.toSet -- guesses.map(_.char)).isEmpty
//    def addGuess(guess: Guess): State = new State(name, guesses + guess, word) {}
//  }
//  object State {
//    def initial(name: Name, word: Word): State = new State(name, Set.empty, word) {}
//  }
//
//  sealed trait GuessResult
//  object GuessResult {
//    case object Won       extends GuessResult
//    case object Lost      extends GuessResult
//    case object Correct   extends GuessResult
//    case object Incorrect extends GuessResult
//    case object Unchanged extends GuessResult
//  }
//
//  def getUserInput(message: String): ZIO[Console, IOException, String] =
//    printLine(message) *> readLine
//
//  lazy val getName: ZIO[Console, IOException, Name] =
//    for {
//      input <- getUserInput("What's your name?")
//      name  <- ZIO.fromOption(Name.make(input))
//        .orElse(printLine("Invalid input. Please try again...") *> getName)
//    } yield name
//
//  lazy val chooseWord: URIO[Random, Word] =
//    for {
//      index <- nextIntBounded(words.length)
//      word  <- ZIO.fromOption(words.lift(index).flatMap(Word.make))
//        .orDieWith(_ => new Error("Boom!"))
//    } yield word
//
//  def renderState(state: State): ZIO[Console, IOException, Unit] = {
//    val hangman = ZIO.succeed(hangmanStages(state.failuresCount))
//    val wordStr =
//      state.word.toList.map { c =>
//        if (state.guesses.map(_.char).contains(c)) s" $c " else "   "
//      }.mkString
//    val line    = List.fill(state.word.length)(" - ").mkString
//    val guesses = s"Guesses: ${state.guesses.map(_.char).mkString(", ")}"
//
//    hangman.flatMap { stage =>
//      printLine {
//        s"""
//           |$stage
//           |
//           |$wordStr
//           |$line
//           |
//           |$guesses
//           |""".stripMargin
//      }
//    }
//  }
//
//  lazy val getGuess: ZIO[Console, IOException, Guess] =
//    for {
//      input <- getUserInput("What's your next guess?")
//      guess <- ZIO.fromOption(Guess.make(input))
//        .orElse(printLine("Invalid input. Please try again...") *> getGuess)
//    } yield guess
//
//  def analyzeNewGuess(oldState: State, newState: State, guess: Guess): GuessResult =
//    if (oldState.guesses.contains(guess)) GuessResult.Unchanged
//    else if (newState.playerWon) GuessResult.Won
//    else if (newState.playerLost) GuessResult.Lost
//    else if (oldState.word.contains(guess.char)) GuessResult.Correct
//    else GuessResult.Incorrect
//
//  def gameLoop(oldState: State): ZIO[Console, IOException, Unit] =
//    for {
//      _         <- renderState(oldState)
//      guess     <- getGuess
//      newState   = oldState.addGuess(guess)
//      result     = analyzeNewGuess(oldState, newState, guess)
//      _ <- result match {
//        case GuessResult.Won =>
//          printLine(s"Congratulations ${newState.name.name}! You won!") *> renderState(newState)
//        case GuessResult.Lost =>
//          printLine(s"Sorry ${newState.name.name}! You lost! Word was: ${newState.word.word}") *> renderState(newState)
//        case GuessResult.Correct =>
//          printLine(s"Good guess, ${newState.name.name}!") *> gameLoop(newState)
//        case GuessResult.Incorrect =>
//          printLine(s"Bad guess, ${newState.name.name}!") *> gameLoop(newState)
//        case GuessResult.Unchanged =>
//          printLine(s"${newState.name.name}, you've already tried that letter!") *> gameLoop(newState)
//      }
//    } yield ()
}
