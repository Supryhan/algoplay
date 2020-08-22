package structures.di.model

import structures.di.model.loading.TrainLoader2
import structures.di.model.shunting.TrainShunter2

package object station {

  class TrainDispatch2()

  class TrainStation2(trainShunter: TrainShunter2, trainLoader: TrainLoader2, trainDispatch: TrainDispatch2) {
    def prepareAndDispatchNextTrain() = {
      println(s">>>TrainDispatch[hash]:${trainDispatch.hashCode}")
      println(s">>>TrainShunter[hash]:${trainShunter.hashCode}")
      println(s">>>TrainLoader[hash]:${trainLoader.hashCode}")
      1
    }
  }

}
