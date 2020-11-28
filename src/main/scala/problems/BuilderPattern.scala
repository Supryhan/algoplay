package problems

object BuilderPattern extends App {
  sealed abstract class Preparation
  case object Neat extends Preparation
  case object OnTheRocks extends Preparation
  case object WithWater extends Preparation

  sealed abstract class Glass
  case object Short extends Glass
  case object Tall extends Glass
  case object Tulip extends Glass

  case class OrderOfScotch(brand: String,
                           mode: Preparation,
                           isDouble: Boolean,
                           glass: Option[Glass])

  sealed class TRUE
  sealed class FALSE

  class ScotchBuilder[HB, HM, HD](val theBrand: Option[String],
                                  val theMode: Option[Preparation],
                                  val theDoubleStatus: Option[Boolean],
                                  val theGlass: Option[Glass]) {
    def withBrand(b: String): ScotchBuilder[TRUE, HM, HD] =
      new ScotchBuilder[TRUE, HM, HD](Some(b), theMode, theDoubleStatus, theGlass)

    def withMode(p: Preparation): ScotchBuilder[HB, TRUE, HD] =
      new ScotchBuilder[HB, TRUE, HD](theBrand, Some(p), theDoubleStatus, theGlass)

    def isDouble(b: Boolean): ScotchBuilder[HB, HM, TRUE] =
      new ScotchBuilder[HB, HM, TRUE](theBrand, theMode, Some(b), theGlass)

    def withGlass(g: Glass): ScotchBuilder[HB, HM, HD] =
      new ScotchBuilder[HB, HM, HD](theBrand, theMode, theDoubleStatus, Some(g))
  }

  implicit def enableBuild(builder: ScotchBuilder[TRUE, TRUE, TRUE]) =
    new {
      def build(): OrderOfScotch =
        OrderOfScotch(
          builder.theBrand.get,
          builder.theMode.get,
          builder.theDoubleStatus.get,
          builder.theGlass
        )
    }

  def builder = new ScotchBuilder[FALSE, FALSE, FALSE](None, None, None, None)

  println(builder.withBrand("b").withGlass(Short).withMode(Neat).isDouble(true).build().toString)
}
