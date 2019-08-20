package robot

import org.scalatest.{FunSpec, Matchers}

class CommandSpec extends FunSpec with Matchers {
  describe("parseCommand") {
    it("should parse commands for correct format") {
      Command.parseCommand("PLACE 2,3,NORTH") shouldEqual(Right(PlaceCommand(2, 3, North)))
      Command.parseCommand("PLACE 10,20,SOUTH") shouldEqual(Right(PlaceCommand(10, 20, South)))

      Command.parseCommand("LEFT") shouldEqual(Right(LeftCommand))
      Command.parseCommand("RIGHT") shouldEqual(Right(RightCommand))
      Command.parseCommand("REPORT") shouldEqual(Right(ReportCommand))
      Command.parseCommand("MOVE") shouldEqual(Right(MoveCommand))
    }

    it("should return Left if can't parse command") {
      Command.parseCommand("INVALID") shouldEqual(Left("Can't parse command: INVALID"))
    }
  }
}
