/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Edge.java
 */

package dvrlib.generic;

public class Edge {
   public final int a, b;
   protected Edge previous, next;

   /**
    * Edge constructor.
    * @param a The index of the first vertex this edge is connected to.
    * @param b The index of the second vertex this edge is connected to.
    * O(1).
    */
   public Edge(final int a, final int b) {
      this.a = a;
      this.b = b;
   }

   /**
    * Returns true if getPrevious will return a valid pointer, false otherwise.
    * O(1).
    */
   public boolean hasPrevious() {
      return (previous != null);
   }

   /**
    * Returns a pointer to the previous edge.
    * O(1).
    */
   public Edge getPrevious() {
      return previous;
   }

   /**
    * Returns true if getNext() will return a valid pointer, false otherwise.
    * O(1).
    */
   public boolean hasNext() {
      return (next != null);
   }

   /**
    * Returns a pointer to the next edge.
    * O(1).
    */
   public Edge getNext() {
      return next;
   }
}
