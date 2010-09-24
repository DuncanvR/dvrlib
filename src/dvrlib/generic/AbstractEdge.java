/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * AbstractEdge.java
 */

package dvrlib.generic;

public class AbstractEdge {
   public final int a, b;

   /**
    * AbstractEdge constructor.
    * @param a The index of the first node this edge is connected to.
    * @param b The index of the second node this edge is connected to.
    * O(1).
    */
   public AbstractEdge(int a, int b) {
      this.a = a;
      this.b = b;
   }

   @Override
   public String toString() {
      return "dvrlib.generic.AbstractEdge(" + a + ", " + b + ")";
   }
}
