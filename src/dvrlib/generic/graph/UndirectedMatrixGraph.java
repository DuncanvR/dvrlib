/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * UndirectedMatrixGraph.java
 */

package dvrlib.generic.graph;

import java.util.Collection;

public class UndirectedMatrixGraph<E extends Edge> extends MatrixGraph<E> {

   /**
    * UndirectedMatrixGraph constructor, using itself as edgeCreator.
    * @param nodeCount The number of nodes in this graph.
    * @see UndirectedMatrixGraph(int, dvrlib.generic.EdgeCreator)
    */
   public UndirectedMatrixGraph(int nodeCount) {
      this(nodeCount, null);
   }

   /**
    * UndirectedMatrixGraph constructor.
    * @param nodeCount   The number of nodes in this graph.
    * @param edgeCreator The class that will take care of the creation of edges.
    * @see MatrixGraph#MatrixGraph(int, dvrlib.generic.EdgeCreator)
    * O(1)
    */
   public UndirectedMatrixGraph(int nodeCount, EdgeCreator<E> edgeCreator) {
      super(nodeCount, edgeCreator);
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
    * Returns the edge between nodes a and b.
    * O(1).
    */
   @Override
   public E getEdge(int a, int b) {
      return (a < b ? super.getEdge(a, b) : super.getEdge(b, a));
   }

   /**
    * Returns the neighbouring nodes of the node with the given index.
    * @see Node#getNeighbours()
    */
   @Override
   public Collection<MatrixGraphNode> getNeighbours(int index) {
      Collection<MatrixGraphNode> neighbours = nodes[index].getNeighbours();
      for(int i = 0; i < nodeCount; i++) {
         if(nodes[i].hasEdge(index))
            neighbours.add(getNode(i));
      }
      return neighbours;
   }

   /**
    * Adds an edge between nodes a and b.
    * @see MatrixGraph#addEdge(int, int)
    * O(1) if the edge already existed, O(e) otherwise.
    */
   @Override
   public boolean addEdge(int a, int b) {
      if(a == b)
         return false;

      boolean added = (a < b ? super.addEdge(a, b) : super.addEdge(b, a));
      if(added)
         incDegree(a < b ? b : a);
      return added;
   }

   /**
    * Removes the edge between vertices a and b.
    * @see MatrixGraph#removeEdge(int, int)
    * O(1).
    */
   @Override
   public boolean removeEdge(int a, int b) {
      boolean removed = (a < b ? super.removeEdge(a, b) : super.removeEdge(b, a));
      if(removed)
         decDegree(a < b ? b : a);
      return removed;
   }

   @Override
   protected String printEdge(int a, int b) {
      return (a < b ? super.printEdge(a, b) : "-");
      //return (a < b ? super.printEdge(a, b) : "-       ");
   }
}
