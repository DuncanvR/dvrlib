/*
 * DvRlib - Generic
 * Duncan van Roermund, 2012-2013
 * ComparableIdenticalPair.java
 */

package dvrlib.generic;

public class ComparableIdenticalPair<A extends Comparable<A>> extends ComparablePair<A, A> {
   /**
    * ComparableIdenticalPair constructor.
    * @see ComparablePair#ComparablePair(java.lang.Object, java.lang.Object)
    */
   public ComparableIdenticalPair(A a, A b) {
      super(a, b);
   }
}
