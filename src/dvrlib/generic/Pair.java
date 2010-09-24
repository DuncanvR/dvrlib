/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Pair.java
 */

package dvrlib.generic;

public class Pair<A, B> {
   public A a;
   public B b;

   /**
    * Pair constructor.
    * @param a The first element in this pair.
    * @param b The second element in this pair.
    */
   public Pair(A a, B b) {
      this.a = a;
      this.b = b;
   }
}
