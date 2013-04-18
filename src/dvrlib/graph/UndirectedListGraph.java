/*
 * DvRlib - Graph
 * Duncan van Roermund, 2010
 * UndirectedListGraph.java
 */

package dvrlib.graph;

public class UndirectedListGraph<NodeData, EdgeData> extends ListGraph<NodeData, EdgeData> {

   /**
    * UndirectedListGraph constructor.
    * @param nodeCount The number of nodes in this graph.
    */
   public UndirectedListGraph(int nodeCount) {
      super(nodeCount);
   }

   /**
    * Adds an edge between nodes a and b.
    * @return true when the edge was successfully added, false otherwise.
    * @see MatrixGraph#addEdge(int, int)
    * O(1) if the edge already existed, O(e) otherwise.
    */
   @Override
   public boolean addEdge(int a, int b, EdgeData ed) {
      if(a == b)
         return false;

      if(super.addEdge(a, b, ed)) {
         if(super.addEdge(b, a, ed)) {
            edgeCount--;
            return true;
         }
         else
            throw new IllegalStateException("Unable to add both edges " + a + "," + b + " and " + b + "," + a);
      }
      return false;
   }

   /**
    * Sets the data associated with the edge between nodes a and b, and returns the old data.
    * @return The old data associated with the indicated edge.
    * @see ListGraph#setEdgeData(int, int, java.lang.Object)
    */
   @Override
   public EdgeData replaceEdge(int a, int b, EdgeData ed) {
      EdgeData edA = super.replaceEdge(a, b, ed),
               edB = super.replaceEdge(b, a, ed);
      assert(edA == edB);
      return edA;
   }

   /**
    * Removes the edge between vertices a and b.
    * @see ListGraph#removeEdge(int, int)
    * O(1).
    */
   @Override
   public EdgeData removeEdge(int a, int b) {
      EdgeData edA = super.removeEdge(a, b);
      edgeCount++;
      EdgeData edB = super.removeEdge(b, a);
      assert(edA == edB);
      return edA;
   }

   @Override
   protected String printEdge(int a, int b) {
      return (a < b ? super.printEdge(a, b) : "-");
   }
}
