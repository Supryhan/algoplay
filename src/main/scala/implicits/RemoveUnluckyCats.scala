package implicits

class RemoveUnluckyCats {
  //Write a Truthy typeclass, which allows to call
  def trueOrNot():Boolean = ??? //function on any type

  case class Train(name: String) {
    def is13: Boolean = name.contains("13")
  }

  case class Cat(age: Int, color: String) {
    def isBlack: Boolean = color == "black"
  }

  val trains = Seq(Train("k2839"), Train("f13"))
  val cats = Seq(Cat(10, "black"), Cat(5, "white"), Cat(13, "pink"))

  // TODO: Implement type-class here




//  trains.removeUnlucky      //shoud be List(Train(k2839))
//  cats.removeUnlucky        //shoud be List(Cat(5,white))
}


class X {
  def removeUnlucky() = ???
}