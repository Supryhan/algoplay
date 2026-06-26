package com.supryhan.catslaboratory.kleislilab

import cats.effect.unsafe.implicits.global
import com.supryhan.catslaboratory.kleislilab.WriterMonadPractice
import org.specs2.execute.PendingUntilFixed
import org.specs2.mutable.SpecWithJUnit

class WriterMonadPracticeTest extends SpecWithJUnit with PendingUntilFixed {
  "WriterMonadPractice" should {
    "get false" in {
      WriterMonadPractice.writer4.run.unsafeRunSync()._2 mustEqual(false)
    }.pendingUntilFixed("for now")
  }
}
