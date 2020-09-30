package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._

  @Test def `contains is implemented`: Unit = {
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
  }

  @Test def `contains for singletonSet`: Unit = {
    assert(contains(singletonSet(1), 1))
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remvoe the
   * @Ignore annotation.
   */
  @Test def `singleton set one contains one`: Unit = {

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

  //@Ignore("not ready yet")

  @Test def `union contains all elements of each set`: Unit = {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  @Test def `intersect contains only elements of every set`: Unit = {
    new TestSets {
      val su1 = union(s1, s2)
      val su2 = union(s2, s3)
      val si1 = intersect(su1, su2)
      assert(contains(si1, 2), "Intersect 1")
      assert(!contains(si1, 1), "Intersect 2")
    }
  }

  @Test def `diff contains only elements of first set`: Unit = {
    new TestSets {
      val su1 = union(s1, s2)
      val su2 = union(s2, s3)
      val si1 = diff(su1, su2)
      assert(contains(si1, 1), "Diff 1")
      assert(!contains(si1, 2), "Diff 2")
    }
  }

  @Test def `filter`: Unit = {
    new TestSets {
      val su1 = union(s1, s2)
      val su2 = union(s2, s3)
      val su3 = union(su1, su2)

      assert(contains(FunSets.filter(su3, (x: Int) => x % 2 == 1), 1), "Filter 1")
      assert(!contains(FunSets.filter(su3, (x: Int) => x % 2 == 1), 2), "Filter 2")
    }
  }

  @Test def `forall`: Unit = {
    new TestSets {
      val sfa1 = union(s1, s3)
      val sfa2 = union(s2, s3)

      assert(FunSets.forall(sfa1, (x: Int) => x % 2 == 1), "forall 1")
      assert(!FunSets.forall(sfa2, (x: Int) => x % 2 == 1), "forall 2")
    }
  }

  @Test def `exists`: Unit = {
    new TestSets {
      val sfa1 = union(s1, s3)
      val sfa2 = union(s2, s3)

      assert(FunSets.exists(sfa1, (x: Int) => x % 2 == 1), "exists 1")
      assert(FunSets.exists(sfa2, (x: Int) => x % 2 == 1), "exists 2")
    }
  }

  @Test def `map`: Unit = {
    new TestSets {
      val sfa1 = union(s1, s3)
      val sfa2 = union(s2, s3)

      val sm1 = FunSets.map(sfa1, _+1)
      val sm2 = FunSets.map(sfa2, _+1)

      assert(FunSets.forall(sm1, (x: Int) => x % 2 == 0), "exists 1")
      assert(FunSets.exists(sm2, (x: Int) => x % 2 == 1), "exists 2")
    }
  }

  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
