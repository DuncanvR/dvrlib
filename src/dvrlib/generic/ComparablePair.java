/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * ComparablePair.java
 */

package dvrlib.generic;

public class ComparablePair<A extends Comparable, B extends Comparable> extends Pair<A, B> implements Comparable<ComparablePair<A, B>> {

   /**
    * ComparablePair constructor.
    * @see Pair#Pair(java.lang.Object, java.lang.Object)
    */
   public ComparablePair(A a, B b) {
      super(a, b);
   }

   public int compareTo(ComparablePair<A, B> that) {
      int cA = a.compareTo(that.a);
      return (cA == 0 ? b.compareTo(that.b) : cA);
   }
}
