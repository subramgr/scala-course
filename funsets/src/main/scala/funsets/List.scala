package funsets

/**
  * Created by gsubra1 on 5/29/16.
  */
trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Nil[T] extends List[T] {
  def isEmpty: Boolean = true

  def tail: List[T] = throw new NoSuchElementException("Nil.tail")

  def head: T = throw new NoSuchElementException("Nil.head")
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}