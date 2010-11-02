/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * UndirectedListGraph.java
 */

package dvrlib.generic.graph;

public class UndirectedListGraph extends ListGraph {

   /**
    * UndirectedListGraph constructor, using itself as edgeCreator.
    * @param nodeCount The number of nodes in this graph.
    * @see UndirectedList(int, dvrlib.generic.EdgeCreator)
    */
   public UndirectedListGraph(int nodeCount) {
      super(nodeCount);
   }

   /**
    * Adds and returns an edge between nodes a and b.
    * If the edge already existed, that edge is returned.
    * @see MatrixGraph#addEdge(int, int)
    * O(1) if the edge already existed, O(e) otherwise.
    */
   @Override
   public boolean addEdge(int a, int b) {
      if(a == b)
         return false;

      if(super.addEdge(a, b)) {
         if(super.addEdge(b, a)) {
            edgeCount--;
            return true;
         }
         else
            super.removeEdge(a, b);
      }
      return false;
   }

   /**
    * Removes the edge between vertices a and b.
    * @see MatrixGraph#removeEdge(int, int)
    * O(1).
    */
   @Override
   public boolean removeEdge(int a, int b) {
      if(super.removeEdge(a, b)) {
         if(super.removeEdge(b, a)) {
            edgeCount++;
            return true;
         }
         else
            super.addEdge(a, b);
      }
      return false;
   }

   @Override
   protected String printEdge(int a, int b) {
      return (a < b ? super.printEdge(a, b) : "-");
      //return (a < b ? super.printEdge(a, b) : "-       ");
   }
}
