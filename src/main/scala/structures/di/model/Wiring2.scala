package structures.di.model

object Wiring2 extends App {

  import com.softwaremill.macwire._

  implicit lazy val pointSwitcher = wire[PointSwitcher]
  implicit lazy val trainCarCoupler = wire[TrainCarCoupler]
  implicit lazy val trainShunter = wire[TrainShunter]

  implicit lazy val craneController = wire[CraneController]
  implicit lazy val trainLoader = wire[TrainLoader]

  // note the def instead of lazy val
  implicit def trainDispatch = wire[TrainDispatch]

  // the stations share all services except the train dispatch,
  // for which a new instance is create on each usage
  lazy val trainStationEast = wire[TrainStation]
  lazy val trainStationWest = wire[TrainStation]

  trainStationEast.prepareAndDispatchNextTrain()
  trainStationWest.prepareAndDispatchNextTrain()
}