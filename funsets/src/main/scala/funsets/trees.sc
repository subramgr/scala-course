object trees {
  val t1 = new NonEmpty(3, new Empty, new Empty)
  println(t1)
}

abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int):Boolean
  def union(other: IntSet): IntSet
  def filter(p: Int => Boolean): IntSet =
    filterAcc(p, new Empty)
  def filterAcc(p: Int => Boolean, acc: IntSet): IntSet
}

class Empty extends IntSet {
  def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
  def contains(x: Int): Boolean = false
  def union(other: IntSet) = other
  def filterAcc(p: Int => Boolean, acc: IntSet): IntSet = acc
  override def toString(): String = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def incl(x: Int): IntSet = {
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this
  }

  def contains(x: Int): Boolean = {
    if (x == elem) true
    else if (x < elem) left contains x
    else right contains x
  }

  def union(other: IntSet): IntSet = {
    ((left union right) union other) incl elem
  }

  def filterAcc(p: Int => Boolean, acc: IntSet): IntSet = {
    if (p(elem)) (left union right).filterAcc(p, new NonEmpty(elem, new Empty, new Empty))
    else left.filterAcc(p, acc) union right.filterAcc(p, new Empty)
  }

  override def toString(): String = {
    "{" + elem + left + right + "}"
  }
}