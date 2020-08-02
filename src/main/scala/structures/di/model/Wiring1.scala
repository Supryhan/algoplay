package structures.di

import structures.di.model._

object Wiring1 extends App {
  implicit lazy val pointSwitcher = new PointSwitcher
  implicit lazy val trainCarCoupler = new TrainCarCoupler
  implicit lazy val trainShunter = new TrainShunter
  implicit lazy val craneController = new CraneController
  implicit lazy val trainLoader = new TrainLoader
  implicit lazy val trainDispatch = new TrainDispatch
  implicit lazy val trainStation = new TrainStation
  trainStation.prepareAndDispatchNextTrain()
}
