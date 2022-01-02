package zio.app

class Try2GuessWordGame {
  sealed abstract case class Name private (name: String)
  object Name {
    def make(name: String): Option[Name] =
      if (name.nonEmpty)
        Some(new Name(name) {})
      else
        None
  }
  sealed abstract case class Guess private (char: Char)
  object Guess {
    def make(str: String): Option[Guess] =
      Some(str.toList)
      .collect {
        case c :: Nil if c.isLetter => new Guess(c.toLower) {}
      }
  }
  sealed abstract case class Word private (word: String) {
    val length: Int                   = word.length
    def contains(char: Char): Boolean = word.contains(char)
    def toList: List[Char]            = word.toList
    def toSet: Set[Char]              = word.toSet
  }
  object Word {
    def make(word: String): Option[Word] =
      if (word.nonEmpty && word.forall(_.isLetter))
        Some(new Word(word.toLowerCase) {})
      else
        None
  }
  sealed abstract case class State private (name: Name, guesses: Set[Guess], word: Word) {
    def failuresCount: Int            = (guesses.map(_.char) -- word.toSet).size
    def playerLost: Boolean           = failuresCount > 5
    def playerWon: Boolean            = (word.toSet -- guesses.map(_.char)).isEmpty
    def addGuess(guess: Guess): State = new State(name, guesses + guess, word) {}
  }
  object State {
    def initial(name: Name, word: Word): State = new State(name, Set.empty, word) {}
  }
  sealed trait GuessResult
  object GuessResult {
    case object Won       extends GuessResult
    case object Lost      extends GuessResult
    case object Correct   extends GuessResult
    case object Incorrect extends GuessResult
    case object Unchanged extends GuessResult
  }


}

//object Hangman extends App {
//  def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] = ???
//
//  import zio.console._
//
//  def getUserInput(message: String): ZIO[Console, IOException, String] = {
//    putStrLn(message)
//    getStrLn
//  }
//}