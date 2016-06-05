object test {
  def concatOcc(occ: List[(Char, Int)]): List[Char] = occ match {
    case Nil => Nil
    case (ch, f) :: xs => (for (i <- 1 to f) yield ch).toList ++ concatOcc(xs)
  }

  def subset(elems: List[Char]): List[List[Char]] = elems match {
    case Nil => List(List())
    case x :: xs =>
      val remaining = subset(xs)
      remaining.map(subsets => x :: subsets) ++ remaining
  }

  subset(List('a', 'b'))

  subset(concatOcc(List(('a', 2), ('b', 2)))).distinct

  subset(concatOcc(List(('a', 2), ('b', 2)))).distinct.map(ls => ls.groupBy(x => x).map{case(k, ys) => (k, ys.length)}.toList)


}