package robot

import org.scalatest.{FunSpec, Matchers}

import cats._
import cats.syntax.either._
import cats.implicits._

class FacingSpec extends FunSpec with Matchers {
  describe("parseFacing") {
    it("should parse lower case") {
      Facing.parseFacing("north") should be(Right(North))
      Facing.parseFacing("south") should be(Right(South))
      Facing.parseFacing("west") should be(Right(West))
      Facing.parseFacing("east") should be(Right(East))
    }

    it("should parse upper case") {
      Facing.parseFacing("NORTH") should be(Right(North))
      Facing.parseFacing("SOUTH") should be(Right(South))
      Facing.parseFacing("WEST") should be(Right(West))
      Facing.parseFacing("EAST") should be(Right(East))
    }

    it("should return Left if can't parse") {
      Facing.parseFacing("Invalid") should be(Left("Can't parse facing: Invalid"))
    }
  }
}
