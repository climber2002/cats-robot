package robot

import cats.data.State

case class ProgramState(tabletop: Tabletop, robot: Option[Robot])

object ProgramState {

  def processCommand(command: Command): State[ProgramState, Option[String]] =
    command match {
      case PlaceCommand(x, y, facing) => processPlaceCommand(x, y, facing)
      case MoveCommand                => processMoveCommand
      case LeftCommand                => processRotateLeftCommand
      case RightCommand               => processRotateRightCommand
      case ReportCommand              => processReportCommand
    }

  private def processPlaceCommand(
    x: Int,
    y: Int,
    facing: Facing
  ): State[ProgramState, Option[String]] = State { state =>
    (ProgramState(state.tabletop, Some(Robot(x, y, facing))), None)
  }

  private def processMoveCommand: State[ProgramState, Option[String]] = State {
    case ProgramState(tabletop, None) => (ProgramState(tabletop, None), None)
    case ProgramState(tabletop, Some(robot)) =>
      val (nextX, nextY) =
        Facing.nextMovePosition(robot.facing, robot.x, robot.y)
      if (tabletop.isFallingWithin(nextX, nextY)) {
        (ProgramState(tabletop, Some(Robot(nextX, nextY, robot.facing))), None)
      } else {
        (ProgramState(tabletop, Some(robot)), None)
      }
  }

  private def processRotateLeftCommand: State[ProgramState, Option[String]] =
    State {
      case ProgramState(tabletop, None) => (ProgramState(tabletop, None), None)
      case ProgramState(tabletop, Some(robot)) =>
        val nextFacing = Facing.leftFacing(robot.facing)
        (ProgramState(tabletop, Some(robot.copy(facing = nextFacing))), None)
    }

  private def processRotateRightCommand: State[ProgramState, Option[String]] =
    State {
      case ProgramState(tabletop, None) => (ProgramState(tabletop, None), None)
      case ProgramState(tabletop, Some(robot)) =>
        val nextFacing = Facing.rightFacing(robot.facing)
        (ProgramState(tabletop, Some(robot.copy(facing = nextFacing))), None)
    }

  private def processReportCommand: State[ProgramState, Option[String]] =
    State {
      case ProgramState(tabletop, None) => (ProgramState(tabletop, None), None)
      case ProgramState(tabletop, Some(robot)) =>
        (
          ProgramState(tabletop, Some(robot)),
          Some(s"${robot.x},${robot.y},${robot.facing.toString.toUpperCase}")
        )
    }

}
