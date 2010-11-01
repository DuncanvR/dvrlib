/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * UndirectedListGraph.java
 */

package dvrlib.generic.graph;

public class UndirectedListGraph<E extends Edge> extends ListGraph {

   /**
    * UndirectedListGraph constructor, using itself as edgeCreator.
    * @param nodeCount The number of nodes in this graph.
    * @see UndirectedList(int, dvrlib.generic.EdgeCreator)
    */
   public UndirectedListGraph(int nodeCount) {
      super(nodeCount);
   }

   /**
    * Returns true if there is an edge between nodes a and b, false otherwise.
    * O(1).
    */
   @Override
   public boolean hasEdge(int a, int b) {
      return (a < b ? super.hasEdge(a, b) : super.hasEdge(b, a));
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

      return (a < b ? super.addEdge(a, b) : super.addEdge(b, a));
   }

   /**
    * Removes the edge between vertices a and b.
    * @see MatrixGraph#removeEdge(int, int)
    * O(1).
    */
   @Override
   public boolean removeEdge(int a, int b) {
      return (a < b ? super.removeEdge(a, b) : super.removeEdge(b, a));
   }

   @Override
   protected String printEdge(int a, int b) {
      return (a < b ? super.printEdge(a, b) : "-");
      //return (a < b ? super.printEdge(a, b) : "-       ");
   }
}
