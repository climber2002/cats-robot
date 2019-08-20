package robot

import kaleidoscope._

sealed trait Command

case class PlaceCommand(x: Int, y: Int, facing: Facing) extends Command
case object MoveCommand extends Command
case object LeftCommand extends Command
case object RightCommand extends Command
case object ReportCommand extends Command

object Command {
  def parseCommand(s: String): ErrorOr[Command] = s.toUpperCase match {
    case r"""PLACE ${x}@(\d+),${y}@(\d+),${facing}@(.*)""" => parsePlaceCommand(x, y, facing)
    case "MOVE" => Right(MoveCommand)
    case "LEFT" => Right(LeftCommand)
    case "RIGHT" => Right(RightCommand)
    case "REPORT" => Right(ReportCommand)
    case _ => Left(s"Can't parse command: ${s}")
  }

  private def parsePlaceCommand(xStr: String, yStr: String, facingStr: String): ErrorOr[PlaceCommand] = for {
    x <- parseInt(xStr)
    y <- parseInt(yStr)
    facing <- Facing.parseFacing(facingStr)
  } yield PlaceCommand(x, y, facing)
}