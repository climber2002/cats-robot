package robot

import cats.effect.IO

object RobotApp extends App {

  def mainLoop(programState: ProgramState): IO[Unit] = for {
    _ <- IOFunctions.putStrLn("Please type your command")
    str <- IOFunctions.getLine
    command = Command.parseCommand(str).flatMap(cmd => Validator.validateCommand(programState, cmd))
    state <-  command match {
      case Left(errorMessage) => for {
        _ <- IOFunctions.putStrLn(errorMessage)
      } yield(programState)

      case Right(command) => for {
        (nextState, message) <- IO(ProgramState.processCommand(programState, command))
        _ <- message.map(info => IOFunctions.putStrLn(info)).getOrElse(IO.pure(()))
      } yield nextState
    }
    _ <- mainLoop(state)
  } yield ()

  mainLoop(ProgramState(Tabletop(), None)).unsafeRunSync()
}
