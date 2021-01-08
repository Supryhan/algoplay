package structures
package di
package model

import structures.di.model.shunting.PointSwitcher2

package object loading {

  class CraneController2()

  class TrainLoader2(craneController: CraneController2, pointSwitcher: PointSwitcher2)

}
