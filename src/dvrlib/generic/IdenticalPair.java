/*
 * DvRlib - Generic
 * Duncan van Roermund, 2012-2013
 * IdenticalPair.java
 */

package dvrlib.generic;

public class IdenticalPair<A> extends Pair<A, A> {
   /**
    * IdenticalPair constructor.
    * @param a The first element in this pair.
    * @param b The second element in this pair.
    * @see Pair#Pair(java.lang.Object, java.lang.Object)
    */
   public IdenticalPair(A a, A b) {
      super(a, b);
   }
}
