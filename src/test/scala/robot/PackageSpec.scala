package robot

import org.scalatest.{FunSpec, Matchers}

class PackageSpec extends FunSpec with Matchers {
  describe("parseInt") {
    it("should parse Int") {
      parseInt("20") should be(Right(20))
    }

    it("should return Left if can't parse") {
      parseInt("abc") should be(Left("Can't parse to Int: abc"))
    }
  }
}
