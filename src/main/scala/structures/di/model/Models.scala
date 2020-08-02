package structures.di.model

class PointSwitcher()

class TrainCarCoupler()

class TrainShunter(implicit pointSwitcher: PointSwitcher, trainCarCoupler: TrainCarCoupler)

class CraneController()

class TrainLoader(implicit craneController: CraneController, pointSwitcher: PointSwitcher)

class TrainDispatch()

class TrainStation(implicit trainShunter: TrainShunter, trainLoader: TrainLoader, trainDispatch: TrainDispatch) {
  def prepareAndDispatchNextTrain() = println(s">go!")
}