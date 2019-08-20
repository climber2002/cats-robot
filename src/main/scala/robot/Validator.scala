package robot

object Validator {
  def validateCommand(programState: ProgramState, command: Command): ErrorOr[Command] = command match {
    case command: PlaceCommand => validatePlaceCommand(programState: ProgramState, command)
    case _ => Right(command)
  }

  private def validatePlaceCommand(programState: ProgramState, command: PlaceCommand): ErrorOr[Command] = {
    val tabletop = programState.tabletop
    if(tabletop.isFallingWithin(command.x, command.y)) {
      Right(command)
    } else {
      Left(s"The place point doesn't fall within the tabletop: ${command.x} ${command.y}")
    }
  }
}
