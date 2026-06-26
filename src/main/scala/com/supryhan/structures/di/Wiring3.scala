package com.supryhan.structures.di

import com.softwaremill.macwire._
import com.supryhan.structures.di.station.StationModule

package shunting {

  import com.supryhan.structures.di.model.shunting.{
    PointSwitcher2,
    TrainCarCoupler2,
    TrainShunter2
  }

  trait ShuntingModule {
    lazy val pointSwitcher = wire[PointSwitcher2]
    lazy val trainCarCoupler = wire[TrainCarCoupler2]
    lazy val trainShunter = wire[TrainShunter2]
  }

}

package loading {

  import com.supryhan.structures.di.model.loading.{
    CraneController2,
    TrainLoader2
  }
  import com.supryhan.structures.di.model.shunting.PointSwitcher2

  trait LoadingModule {
    lazy val craneController = wire[CraneController2]
    lazy val trainLoader = wire[TrainLoader2]

    def pointSwitcher: PointSwitcher2
  }

}

package station {

  import com.supryhan.structures.di.model.station.{
    TrainDispatch2,
    TrainStation2
  }
  import com.supryhan.structures.di.loading.LoadingModule
  import com.supryhan.structures.di.shunting.ShuntingModule

  trait StationModule extends ShuntingModule with LoadingModule {
    lazy val trainDispatch = wire[TrainDispatch2]
    lazy val trainStation = wire[TrainStation2]
  }

}

object Wiring3 extends App {

  val modules = new StationModule {}

  modules.trainStation.prepareAndDispatchNextTrain
}