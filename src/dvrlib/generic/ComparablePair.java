/*
 * DvRlib - Generic
 * Duncan van Roermund, 2010-2013
 * ComparablePair.java
 */

package dvrlib.generic;

public class ComparablePair<A extends Comparable<A>, B extends Comparable<B>>
             extends Pair<A, B> implements Comparable<ComparablePair<A, B>> {
   /**
    * ComparablePair constructor.
    * @param a The first element in this pair.
    * @param b The second element in this pair.
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
      int cmp = a.compareTo(that.a);
      if(cmp != 0)
         return cmp;
      return b.compareTo(that.b);
   }
}
