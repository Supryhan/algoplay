package structures.di

import com.softwaremill.macwire._

package model.shunting {

  trait ShuntingModule {
    lazy val pointSwitcher = wire[PointSwitcher2]
    lazy val trainCarCoupler = wire[TrainCarCoupler2]
    lazy val trainShunter = wire[TrainShunter2]
  }

}

package model.loading {

  import structures.di.model.shunting.PointSwitcher2

  trait LoadingModule {
    lazy val craneController = wire[CraneController2]
    lazy val trainLoader = wire[TrainLoader2]

    def pointSwitcher: PointSwitcher2
  }

}

package model.station {

  import structures.di.model.loading.LoadingModule
  import structures.di.model.shunting.ShuntingModule

  trait StationModule extends ShuntingModule with LoadingModule {
    lazy val trainDispatch = wire[TrainDispatch2]
    lazy val trainStation = wire[TrainStation2]
  }

}

object Wiring3 extends App {

  import structures.di.model.loading.LoadingModule
  import structures.di.model.shunting.ShuntingModule
  import structures.di.model.station.StationModule

  val modules = new ShuntingModule
    with LoadingModule
    with StationModule

  modules.trainStation.prepareAndDispatchNextTrain
}