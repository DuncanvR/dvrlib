/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010
 * UndirectedListGraph.java
 */

package dvrlib.graph;

public class UndirectedListGraph<NodeData, EdgeData> extends ListGraph<NodeData, EdgeData> {

   /**
    * UndirectedListGraph constructor, using itself as edgeCreator.
    * @param nodeCount The number of nodes in this graph.
    * @see UndirectedList(int, dvrlib.generic.EdgeCreator)
    */
   public UndirectedListGraph(int nodeCount) {
      super(nodeCount);
   }

   /**
    * Adds an edge between nodes a and b.
    * @see MatrixGraph#addEdge(int, int)
    * @return true when the edge was successfully added, false otherwise.
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
            throw new IllegalStateException();
      }
      return false;
   }

   /**
    * Sets the data associated with the edge between nodes a and b, and returns the old data.
    * @see ListGraph#setEdgeData(int, int, java.lang.Object)
    */
   @Override
   public EdgeData setEdgeData(int a, int b, EdgeData ed) {
      EdgeData r = super.setEdgeData(a, b, ed);
      if(r != super.setEdgeData(b, a, ed))
         throw new IllegalStateException("Data in removed edges was not equal");
      return r;
   }

   /**
    * Removes the edge between vertices a and b.
    * @see ListGraph#removeEdge(int, int)
    * O(1).
    */
   @Override
   public EdgeData removeEdge(int a, int b) {
      EdgeData r = super.removeEdge(a, b);
      edgeCount++;
      if(r != super.removeEdge(b, a))
         throw new IllegalStateException("Data in removed edges was not equal");
      return r;
   }

   @Override
   protected String printEdge(int a, int b) {
      return (a < b ? super.printEdge(a, b) : "-");
   }
}
