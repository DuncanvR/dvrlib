/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * UndirectedGraph.java
 */

package dvrlib.generic.graph;

import java.util.Collection;

public class UndirectedGraph<E extends Edge> extends Graph<E> {

   /**
    * UndirectedGraph constructor, using itself as edgeCreator.
    * @param nodeCount The number of nodes in this graph.
    * @see UndirectedGraph(int, dvrlib.generic.EdgeCreator)
    */
   public UndirectedGraph(int nodeCount) {
      this(nodeCount, null);
   }

   /**
    * UndirectedGraph constructor.
    * @param nodeCount   The number of nodes in this graph.
    * @param edgeCreator The class that will take care of the creation of edges.
    * @see Graph#Graph(int, dvrlib.generic.EdgeCreator)
    * O(1)
    */
   public UndirectedGraph(int nodeCount, EdgeCreator<E> edgeCreator) {
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
   public E addEdge(int a, int b) {
      if(a == b)
         return null;

      E e = (a < b ? super.addEdge(a, b) : super.addEdge(b, a));
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
   public E removeEdge(int a, int b) {
      E e = (a < b ? super.removeEdge(a, b) : super.removeEdge(b, a));
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
