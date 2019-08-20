import scala.util.Try

package object robot {
  type ErrorOr[A] = Either[String, A]

  def parseInt(str: String): ErrorOr[Int] = {
    val parsedInt = Try(str.toInt)
    parsedInt.toEither.left.map(_ => s"Can't parse to Int: ${str}")
  }
}
