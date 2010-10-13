/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * HalfComparablePair.java
 */

package dvrlib.generic;

public class HalfComparablePair<A extends Comparable, B> extends Pair<A, B> implements Comparable<HalfComparablePair<A, B>> {

   /**
    * HalfComparablePair constructor.
    * @see Pair#Pair(java.lang.Object, java.lang.Object)
    */
   public HalfComparablePair(A a, B b) {
      super(a, b);
   }

   public int compareTo(HalfComparablePair<A, B> that) {
      return a.compareTo(that.a);
   }
}
