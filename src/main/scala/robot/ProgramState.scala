package robot

case class ProgramState(tabletop: Tabletop, robot: Option[Robot])

object ProgramState {

  /**
   *
   * @param programState
   * @param command
   * @return the next program state and an optional output message
   */
  def processCommand(programState: ProgramState, command: Command): (ProgramState, Option[String]) = programState match {
    case ProgramState(tabletop, None) => processCommandWhenNoRobot(tabletop, command)
    case ProgramState(tabletop, Some(robot)) => processCommandWhenRobotExists(tabletop, robot, command)
  }

  private def processCommandWhenNoRobot(tabletop: Tabletop, command: Command): (ProgramState, Option[String]) = command match {
    case PlaceCommand(x, y, facing) => (ProgramState(tabletop, Some(Robot(x, y, facing))), None)
    case _ => (ProgramState(tabletop, None), None)
  }

  private def processCommandWhenRobotExists(tabletop: Tabletop, robot: Robot, command: Command): (ProgramState, Option[String]) = command match {
    case PlaceCommand(x, y, facing) => (ProgramState(tabletop, Some(Robot(x, y, facing))), None)
    case MoveCommand => (processMoveCommand(tabletop, robot), None)
    case LeftCommand => (processRotateLeftCommand(tabletop, robot), None)
    case RightCommand => (processRotateRightCommand(tabletop, robot), None)
    case ReportCommand => (ProgramState(tabletop, Some(robot)), Some(s"${robot.x},${robot.y},${robot.facing.toString.toUpperCase}"))
  }

  private def processMoveCommand(tabletop: Tabletop, robot: Robot): ProgramState = {
    val (nextX, nextY) = Facing.nextMovePosition(robot.facing, robot.x, robot.y)
    if(tabletop.isFallingWithin(nextX, nextY)) {
      ProgramState(tabletop, Some(Robot(nextX, nextY, robot.facing)))
    } else {
      ProgramState(tabletop, Some(robot))
    }
  }

  private def processRotateLeftCommand(tabletop: Tabletop, robot: Robot): ProgramState = {
    val nextFacing = Facing.leftFacing(robot.facing)
    ProgramState(tabletop, Some(robot.copy(facing = nextFacing)))
  }

  private def processRotateRightCommand(tabletop: Tabletop, robot: Robot): ProgramState = {
    val nextFacing = Facing.rightFacing(robot.facing)
    ProgramState(tabletop, Some(robot.copy(facing = nextFacing)))
  }
}
