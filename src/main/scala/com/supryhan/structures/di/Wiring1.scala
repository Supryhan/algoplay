package com.supryhan.structures.di

import com.supryhan.structures.di.model.{CraneController, PointSwitcher, TrainCarCoupler, TrainDispatch, TrainLoader, TrainShunter, TrainStation}

object Wiring1 extends App {
  implicit lazy val pointSwitcher = new PointSwitcher
  implicit lazy val trainCarCoupler = new TrainCarCoupler
  implicit lazy val trainShunter = new TrainShunter
  implicit lazy val craneController = new CraneController
  implicit lazy val trainLoader = new TrainLoader
  implicit lazy val trainDispatch = new TrainDispatch
  lazy val trainStation = new TrainStation
  println(trainStation.prepareAndDispatchNextTrain)
}
