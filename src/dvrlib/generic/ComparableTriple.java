/*
 * DvRlib - Generic
 * Duncan van Roermund, 2013
 * ComparableTriple.java
 */

package dvrlib.generic;

public class ComparableTriple<A extends Comparable<A>, B extends Comparable<B>, C extends Comparable<C>>
             extends Triple<A, B, C> implements Comparable<ComparableTriple<A, B, C>> {
   /**
    * ComparableTriple constructor.
    * @param a The first element in this triple.
    * @param b The second element in this triple.
    * @param c The third element in this triple.
    * @see Triple#Triple(java.lang.Object, java.lang.Object, java.lang.Object)
    */
   public ComparableTriple(A a, B b, C c) {
      super(a, b, c);
   }

   /**
    * Compares this triple to the given one.
    * @see java.lang.Comparable#compareTo(Object)
    */
   @Override
   public int compareTo(ComparableTriple<A, B, C> that) {
      int cmp = a.compareTo(that.a);
      if(cmp != 0)
         return cmp;
      cmp = b.compareTo(that.b);
      if(cmp != 0)
         return cmp;
      return c.compareTo(that.c);
   }
}
