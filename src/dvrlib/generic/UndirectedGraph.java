/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * UndirectedGraph.java
 */

package dvrlib.generic;

import java.util.Collection;

public class UndirectedGraph extends Graph {

   /**
    * UndirectedGraph constructor.
    * @param nodeCount The number of nodes in this graph.
    * @see Graph#Graph(int)
    * O(1)
    */
   public UndirectedGraph(int nodeCount) {
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
    * Returns the edge between nodes a and b.
    * O(1).
    */
   @Override
   public Edge getEdge(int a, int b) {
      return (a < b ? super.getEdge(a, b) : super.getEdge(b, a));
   }

   /**
    * Returns the neighbouring nodes of the node with the given index.
    * @see Node#getNeighbours()
    */
   @Override
   public Collection<AbstractNode> getNeighbours(int index) {
      Collection<AbstractNode> neighbours = nodes[index].getNeighbours();
      for(int i = 0; i < nodeCount; i++) {
         if(nodes[i].hasEdge(index))
            neighbours.add(getNode(i));
      }
      return neighbours;
   }

   /**
    * Adds and returns an edge between nodes a and b.
    * If the edge already existed, that edge is returned.
    * @see Graph#addEdge(int, int)
    * O(1) if the edge already existed, O(e) otherwise.
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
    * @see Graph#removeEdge(int, int)
    * O(1).
    */
   @Override
   public Edge removeEdge(int a, int b) {
      Edge e = (a < b ? super.removeEdge(a, b) : super.removeEdge(b, a));
      if(e != null)
         decDegree(a < b ? b : a);
      return e;
   }

   @Override
   protected String printEdge(int a, int b) {
      return (a < b ? super.printEdge(a, b) : "-");
      //return (a < b ? super.printEdge(a, b) : "-       ");
   }
}
