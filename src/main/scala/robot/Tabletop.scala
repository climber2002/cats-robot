package robot

class Tabletop(xDimension: Int = 5, yDimension: Int = 5) {
  def isFallingWithin(x: Int, y: Int): Boolean = {
    x >= 0 && x < xDimension && y >= 0 &&  y < yDimension
  }
}
