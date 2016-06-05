def abs(x: Double): Double =
  if (x < 0) -x else x


def sqrt(x: Double, y: Double, tolerance: Double): Double = {
  def isGoodEnough(): Boolean =
    if (abs((y * y) - x) > tolerance) false else true

  if (isGoodEnough()) y else sqrt(x, (y + x/y)/2, tolerance)
}

sqrt(16, 1, 0.001)
