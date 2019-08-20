package robot

import org.scalatest.{FunSpec, Matchers}

class TabletopSpec extends FunSpec with Matchers {
  val tabletop = new Tabletop

  describe("isFallingWithin") {
    it("should return false if x < 0") {
      tabletop.isFallingWithin(-1, 0) should be(false)
    }

    it("should return false if y < 0") {
      tabletop.isFallingWithin(1, -1) should be(false)
    }

    it("should return true if x = 0 and y = 0") {
      tabletop.isFallingWithin(0, 0) should be(true)
    }

    it("should return false if x > xDimension") {
      tabletop.isFallingWithin(5, 0) should be(false)
    }

    it("should return false if y > yDimension") {
      tabletop.isFallingWithin(1, 5) should be(false)
    }
  }
}
