import org.scalatest._

import cats._
import cats.data._
import cats.implicits._

class ShowSpec extends FlatSpec with Matchers {
  "Printable" should "format cat" in {
    Show[String].show("String") should be ("String")
  }
}