/*
 * DvRLib - Generic
 * Duncan van Roermund, 2012
 * ComparableTuple.java
 */

package dvrlib.generic;

public class ComparableTuple<A extends Comparable, B extends Comparable> extends Tuple<A, B> implements Comparable<ComparableTuple<A, B>> {

   /**
    * ComparableTuple constructor.
    * @see Tuple#Tuple(java.lang.Object, java.lang.Object)
    */
   public ComparableTuple(A a, B b) {
      super(a, b);
   }

   public int compareTo(ComparableTuple<A, B> that) {
      int cA = a.compareTo(that.a);
      return (cA == 0 ? b.compareTo(that.b) : cA);
   }
}
