/*
 * DvRlib - Generic
 * Duncan van Roermund, 2010-2013
 * ComparablePair.java
 */

package dvrlib.generic;

public class ComparablePair<A extends Comparable<A>, B extends Comparable<B>> extends Pair<A, B> implements Comparable<ComparablePair<A, B>> {
   /**
    * ComparablePair constructor.
    * @see Pair#Pair(java.lang.Object, java.lang.Object)
    */
   public ComparablePair(A a, B b) {
      super(a, b);
   }

   /**
    * Compares this pair to the given one.
    * @see java.lang.Comparable#compareTo(Object)
    */
   @Override
   public int compareTo(ComparablePair<A, B> that) {
      int cA = a.compareTo(that.a);
      return (cA == 0 ? b.compareTo(that.b) : cA);
   }
}
