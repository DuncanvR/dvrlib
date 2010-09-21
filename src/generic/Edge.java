/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Edge.java
 */

package dvrlib.generic;

public class Edge {
   protected final int a, b;
   protected Edge previous, next;

   /**
    * Edge constructor.
    * @param a The index of the first vertex this edge is connected to.
    * @param b The index of the second vertex this edge is connected to.
    */
   public Edge(final int a, final int b) {
      this.a = a;
      this.b = b;
   }

   /**
    * Returns the index of the first vertex this edge is connected to.
    */
   public int getA() {
      return a;
   }

   /**
    * Returns the index of the second vertex this edge is connected to.
    */
   public int getB() {
      return b;
   }

   /**
    * Returns true if getNext() will return a valid pointer, false otherwise.
    */
   public boolean hasNext() {
      return (next != null);
   }

   /**
    * Returns the next Edge.
    */
   public Edge getNext() {
      return next;
   }
}
