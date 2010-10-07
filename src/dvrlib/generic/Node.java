/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Node.java
 */

package dvrlib.generic;

import java.util.LinkedList;
import java.util.Vector;

public class Node<E extends Edge> extends AbstractNode {
   protected final Graph graph;
   protected final Vector<E> edges;
   protected int first, last;

   /**
    * Node constructor.
    * @param graph A pointer to the graph this node is in.
    * @param index The index of this node.
    * O(1).
    */
   public Node(Graph graph, int index) {
      super(index);
      
      this.graph = graph;
      edges = new Vector<E>(graph.nodeCount);
      edges.setSize(graph.nodeCount);
      first = graph.nodeCount;
      last  = 0;
   }

   /**
    * Returns true if there is an edge from this node to the one with the given index, false otherwise.
    * O(1).
    */
   public boolean hasEdge(int index) {
      if(index < 0 || index >= graph.nodeCount)
         return false;
      return (getEdge(index) != null);
   }

   /**
    * Returns true if there is an edge from this node to the one given, false otherwise.
    * O(1).
    */
   public boolean hasEdge(AbstractNode that) {
      return hasEdge(that.index);
   }

   /**
    * Returns the edge to the node with the given index.
    * O(1).
    */
   public E getEdge(int index) {
      if(index < 0 || index >= graph.nodeCount)
         return null;
      return edges.get(index);
   }

   /**
    * Returns the neighbouring nodes of this node.
    * O(n) the number of nodes in the graph.
    */
   @Override
   public LinkedList<AbstractNode> getNeighbours() {
      LinkedList<AbstractNode> neighbours = new LinkedList<AbstractNode>();
      for(int i = first; i <= last; i++) {
         Edge e = edges.get(i);
         if(e != null)
            neighbours.add(graph.getNode(e.b));
      }
      return neighbours;
   }

   @Override
   public String toString() {
      String s = "dvrlib.generic.Node #" + index + " [";
      for(int i = 0; i < graph.nodeCount; i++) {
         if(edges.get(i) == null)
            s += "-,";
         else
            s += edges.get(i).b + ",";
      }
      return s + "]";
   }
}
