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
}