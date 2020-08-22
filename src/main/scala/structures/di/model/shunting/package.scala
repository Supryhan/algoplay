package structures.di.model

package object shunting {

  class PointSwitcher2()

  class TrainCarCoupler2()

  class TrainShunter2(pointSwitcher: PointSwitcher2, trainCarCoupler: TrainCarCoupler2)

}