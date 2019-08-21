package robot

import org.scalatest.{FunSpec, Matchers}

class ProgramStateSpec extends FunSpec with Matchers {
  val tabletop = Tabletop()

  describe("processCommand") {
    describe("when there is no robot placed yet") {
      val programState = ProgramState(tabletop, None)

      it("should accept Place Command") {
        val (nextState, message) = ProgramState.processCommand(PlaceCommand(1, 2, North)).run(programState).value
        nextState.robot shouldEqual(Some(Robot(1, 2, North)))
      }

      it("should ignore other command") {
        ProgramState.processCommand(MoveCommand).run(programState).value shouldEqual((programState, None))
        ProgramState.processCommand(ReportCommand).run(programState).value shouldEqual((programState, None))
        ProgramState.processCommand(LeftCommand).run(programState).value shouldEqual((programState, None))
        ProgramState.processCommand(RightCommand).run(programState).value shouldEqual((programState, None))
      }
    }

    describe("when there is robot") {
      describe("when the robot is at edge") {
        val robot = Robot(0, 0, South)
        val programState = ProgramState(tabletop, Some(robot))

        it("should accept place command") {
          val (nextState, message) = ProgramState.processCommand(PlaceCommand(1, 2, North)).run(programState).value
          nextState.robot shouldEqual(Some(Robot(1, 2, North)))
          message shouldEqual(None)
        }

        it("should accept MOVE command but ignore") {
          val (nextState, message) = ProgramState.processCommand(MoveCommand).run(programState).value
          nextState.robot shouldBe(Some(Robot(0, 0, South)))
          message shouldEqual(None)
        }

        it("should accept LEFT command") {
          val (nextState, message) = ProgramState.processCommand(LeftCommand).run(programState).value
          nextState.robot shouldBe(Some(Robot(0, 0, East)))
          message shouldEqual(None)
        }

        it("should accept RIGHT command") {
          val (nextState, message) = ProgramState.processCommand(RightCommand).run(programState).value
          nextState.robot shouldBe(Some(Robot(0, 0, West)))
          message shouldEqual(None)
        }

        it("should accept REPORT command") {
          val (nextState, message) = ProgramState.processCommand(ReportCommand).run(programState).value
          nextState.robot shouldBe(Some(Robot(0, 0, South)))
          message shouldEqual(Some("0,0,SOUTH"))
        }
      }

      describe("when the robot is not at edge") {
        val robot = Robot(2, 2, South)
        val programState = ProgramState(tabletop, Some(robot))

        it("should accept place command") {
          val (nextState, message) = ProgramState.processCommand(PlaceCommand(1, 2, North)).run(programState).value
          nextState.robot shouldEqual(Some(Robot(1, 2, North)))
          message shouldEqual(None)
        }

        it("should accept MOVE command but ignore") {
          val (nextState, message) = ProgramState.processCommand(MoveCommand).run(programState).value
          nextState.robot shouldBe(Some(Robot(2, 1, South)))
          message shouldEqual(None)
        }

        it("should accept LEFT command") {
          val (nextState, message) = ProgramState.processCommand(LeftCommand).run(programState).value
          nextState.robot shouldBe(Some(Robot(2, 2, East)))
          message shouldEqual(None)
        }

        it("should accept RIGHT command") {
          val (nextState, message) = ProgramState.processCommand(RightCommand).run(programState).value
          nextState.robot shouldBe(Some(Robot(2, 2, West)))
          message shouldEqual(None)
        }

        it("should accept REPORT command") {
          val (nextState, message) = ProgramState.processCommand(ReportCommand).run(programState).value
          nextState.robot shouldBe(Some(Robot(2, 2, South)))
          message shouldEqual(Some("2,2,SOUTH"))
        }
      }
    }
  }
}
