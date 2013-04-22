/*
 * DvRlib - Generic
 * Duncan van Roermund, 2013
 * IdenticalTriple.java
 */

package dvrlib.generic;

public class IdenticalTriple<A> extends Triple<A, A, A> {
   /**
    * IdenticalTriple constructor.
    * @param a The first element in this triple.
    * @param b The second element in this triple.
    * @param c The third element in this triple.
    * @see Triple#Triple(java.lang.Object, java.lang.Object, java.lang.Object)
    */
   public IdenticalTriple(A a, A b, A c) {
      super(a, b, c);
   }
}
