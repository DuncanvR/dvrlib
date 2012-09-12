/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010 - 2012
 * ComparablePair.java
 */

package dvrlib.generic;

public class ComparablePair<A extends Comparable> extends ComparableTuple<A, A> {
   /**
    * ComparablePair constructor.
    * @see ComparableTuple#ComparableTuple(java.lang.Object, java.lang.Object)
    */
   public ComparablePair(A a, A b) {
      super(a, b);
   }
}
