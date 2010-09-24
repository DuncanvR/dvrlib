/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Edge.java
 */

package dvrlib.generic;

public class Edge extends AbstractEdge {
   protected Edge previous, next;

   /**
    * Edge constructor.
    * @param a The index of the first vertex this edge is connected to.
    * @param b The index of the second vertex this edge is connected to.
    * O(1).
    */
   public Edge(int a, int b) {
      super(a, b);
   }

   /**
    * Returns a pointer to the previous edge.
    * O(1).
    */
   public Edge getPrevious() {
      return previous;
   }

   /**
    * Returns a pointer to the next edge.
    * O(1).
    */
   public Edge getNext() {
      return next;
   }
}
