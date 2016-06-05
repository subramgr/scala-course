package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val abs = (x: Int) => if (x < 0) -x else x
    val boundedSet = (bound: Int) => (x:Int) => abs(x) <= bound
    val seqSet = (lo: Int, hi: Int) => (x: Int) => x >= lo && x <= hi
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains elements present in both set") {
    new TestSets {
      val s = boundedSet(2)
      val is = intersect((x: Int) => x % 2 == 0, s)
      assert(is(2), "intersect set should contain 2")
      assert(is(-2), "intersect set should contain -2")
      assert(is(0), "intersect set should contain 0")
      assert(!is(-1), "intersect set should not contain -1")
      assert(!is(1), "intersect set should not contain 1")
    }
  }

  test("diff contains element present only in the first set") {
    new TestSets {
      val b3 = boundedSet(3)
      val b2 = boundedSet(2)
      val d = diff(b3, b2)
      assert(d(3), "diff should contain 3")
      assert(d(-3), "diff should contain -3")
      assert(!d(2), "diff should not contain 2")
      assert(!d(-2), "diff should not contain -2")
      assert(!d(0), "diff should not contain 0")
    }
  }

  test("filter should retain a set depending upon the filter function") {
    new TestSets {
      val s6 = seqSet(1, 6)
      val ev = filter(s6, (x) => x % 2 == 0)
      assert(ev(2), "filter should contain 2")
      assert(ev(4), "filter should contain 4")
      assert(ev(6), "filter should contain 6")
      assert(!ev(1), "filter should not contain 1")
      assert(!ev(3), "filter should not contain 3")
      assert(!ev(5), "filter should not contain 5")
    }
  }

  test("forall should return true if all the elems of a set satisfies the input predicate") {
    new TestSets {
      val s10 = seqSet(1, 10)
      assert(forall(s10, (x) => x <= 10), "all elems should be less than 10")
      assert(!forall(s10, (x) => x % 2 == 0), "all elems are not even")
    }
  }

  test("exists should return true if there exist an element in the set that satisfies the predicate") {
    new TestSets {
      val s25 = seqSet(5, 25)
      assert(exists(s25, (x) => x == 22), "22 should exist in the range 5 to 25")
      assert(!exists(s25, x => false), "none exists")
      assert(!exists(s25, x => x < 5 && x > 25), "out of range")
    }
  }

  test("map should map the the set to the other set depending upon the mapping function") {
    new TestSets {
      val n3 = seqSet(1, 3)
      val d3 = map(n3, (x) => x * 2)
      assert(d3(2), "2 should be present in the mapped set")
      assert(d3(4), "4 should be present in the mapped set")
      assert(d3(6), "6 should be present in the mapped set")
      assert(!d3(1) && !d3(3), "1, 3 should not be present")
    }
  }

  test("toString should print the contents of the set") {
    new TestSets {
      val n3 = seqSet(1, 3)
      assert(FunSets.toString(n3).equals("{1,2,3}"), "should be same")
      assert(FunSets.toString(map(n3, (x) => x * 2)).equals("{2,4,6}"), "should be same")
    }
  }

}
