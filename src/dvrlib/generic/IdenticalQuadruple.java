/*
 * DvRlib - Generic
 * Duncan van Roermund, 2013
 * IdenticalQuadruple.java
 */

package dvrlib.generic;

public class IdenticalQuadruple<A> extends Quadruple<A, A, A, A> {
   /**
    * IdenticalQuadruple constructor.
    * @param a The first element in this quadruple.
    * @param b The second element in this quadruple.
    * @param c The third element in this quadruple.
    * @param d The fourth element in this quadruple.
    * @see Quadruple#Quadruple(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object)
    */
   public IdenticalQuadruple(A a, A b, A c, A d) {
      super(a, b, c, d);
   }
}
