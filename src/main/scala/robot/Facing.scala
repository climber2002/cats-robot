package robot

sealed trait Facing

case object North extends Facing
case object South extends Facing
case object East extends Facing
case object West extends Facing

object Facing {
  def parseFacing(str: String): ErrorOr[Facing] = str.toUpperCase match {
    case "NORTH" => Right(North)
    case "SOUTH" => Right(South)
    case "EAST" => Right(East)
    case "WEST" => Right(West)
    case _ => Left(s"Can't parse facing: ${str}")
  }

  def nextMovePosition(facing: Facing, x: Int, y: Int): (Int, Int) = facing match {
    case North => (x, y + 1)
    case West => (x - 1, y)
    case South => (x, y - 1)
    case East => (x + 1, y)
  }

  def leftFacing(facing: Facing): Facing = facing match {
    case North => West
    case West => South
    case South => East
    case East => South
  }

  def rightFacing(facing: Facing): Facing = facing match {
    case North => East
    case West => North
    case South => West
    case East => South
  }
}