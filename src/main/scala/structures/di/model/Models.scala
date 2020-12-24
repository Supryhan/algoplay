package structures.di.model

class PointSwitcher()

class TrainCarCoupler()

class TrainShunter(implicit pointSwitcher: PointSwitcher, trainCarCoupler: TrainCarCoupler)

class CraneController()

class TrainLoader(implicit craneController: CraneController, pointSwitcher: PointSwitcher)

class TrainDispatch()

class TrainStation(implicit trainShunter: TrainShunter, trainLoader: TrainLoader, trainDispatch: TrainDispatch) {
  def prepareAndDispatchNextTrain(): Unit = {
    println(s">>>TrainDispatch[hash]:${trainDispatch.hashCode}")
    println(s">>>TrainShunter[hash]:${trainShunter.hashCode}")
    println(s">>>TrainLoader[hash]:${trainLoader.hashCode}")
    println(s">>>TrainStation[hash]:${this.hashCode}")
    println()
  }
}