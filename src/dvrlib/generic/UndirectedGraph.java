/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * UndirectedGraph.java
 */

package dvrlib.generic;

public class UndirectedGraph extends Graph {
   public UndirectedGraph(int nodeCount) {
      super(nodeCount);
   }

   /**
    * Returns true if there is an edge between vertices a and b, false otherwise.
    * O(1).
    */
   @Override
   public boolean hasEdge(int a, int b) {
      return (a < b ? super.hasEdge(a, b) : super.hasEdge(b, a));
   }

   /**
    * Returns the Edge between vertices a and b.
    * O(1).
    */
   @Override
   public Edge getEdge(int a, int b) {
      return (a < b ? super.getEdge(a, b) : super.getEdge(b, a));
   }

   /**
    * Adds an edge between vertices a and b.
    * @return true if the edge was removed, false otherwise.
    * O(e).
    */
   @Override
   public Edge addEdge(int a, int b) {
      if(a == b)
         return null;
      
      Edge e = (a < b ? super.addEdge(a, b) : super.addEdge(b, a));
      if(e != null)
         incDegree(a < b ? b : a);
      return e;
   }

   /**
    * Removes the edge between vertices a and b.
    * O(1).
    */
   @Override
   public Edge removeEdge(int a, int b) {
      if(a < b)
         return super.removeEdge(a, b);
      else
         return super.removeEdge(b, a);
   }

   @Override
   protected String printEdge(int a, int b) {
      return (a < b ? super.printEdge(a, b) : "-");
   }
}
