package robot

import org.scalatest.{FunSpec, Matchers}

class ValidatorSpec extends FunSpec with Matchers {
  val programState = ProgramState(Tabletop(), Some(Robot(1, 2, North)))

  describe("validateCommand") {
    it("should validate valid PlaceCommand") {
      Validator.validateCommand(programState, PlaceCommand(3, 3, South)) shouldEqual(Right(PlaceCommand(3, 3, South)))
    }

    it("should invalidate invalid PlaceCommand") {
      Validator.validateCommand(programState, PlaceCommand(5, 2, North)) shouldEqual(Left("The place point doesn't fall within the tabletop: 5 2"))
    }
  }
}
