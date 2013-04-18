/*
 * DvRlib - Generic
 * Duncan van Roermund, 2010 - 2012
 * Pair.java
 */

package dvrlib.generic;

public class Pair<A> extends Tuple<A, A> {
   /**
    * Pair constructor.
    * @param a The first element in this pair.
    * @param b The second element in this pair.
    * @see Tuple#Tuple(java.lang.Object, java.lang.Object)
    */
   public Pair(A a, A b) {
      super(a, b);
   }
}
