/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010
 * MatrixGraphNode.java
 */

package dvrlib.graph;

import java.util.Iterator;
import java.util.Vector;

public class MatrixGraphNode<E extends Edge> extends AbstractGraphNode<MatrixGraphNode> {
   protected final MatrixGraph graph;
   protected final Vector<E> edges;
   protected int first, last = 0, degree = 0;

   /**
    * MatrixGraphNode constructor.
    * @param graph A pointer to the graph this node is in.
    * @param index The index of this node.
    * O(1).
    */
   public MatrixGraphNode(MatrixGraph graph, int index) {
      super(index);

      this.graph = graph;
      edges = new Vector<E>(graph.nodeCount);
      edges.setSize(graph.nodeCount);
      first = graph.nodeCount;
   }

   /**
    * Returns true if there is an edge from this node to the one with the given index, false otherwise.
    * O(1).
    */
   public boolean hasEdge(int index) {
      return (getEdge(index) != null);
   }

   /**
    * Returns true if there is an edge from this node to the one given, false otherwise.
    * O(1).
    */
   @Override
   public boolean hasEdge(MatrixGraphNode that) {
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
    * Returns the degree of this node.
    * O(1).
    */
   @Override
   public int getDegree() {
      return degree;
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

   /**
    * Returns an iterator to the neighbouring nodes of this node.
    * O(n) the number of nodes in the graph.
    */
   @Override
   public Iterator<MatrixGraphNode> neighbourIterator() {
      throw new UnsupportedOperationException("Not supported yet.");

      /*LinkedList<MatrixGraphNode> neighbours = new LinkedList<MatrixGraphNode>();
      for(int i = first; i <= last; i++) {
         Edge e = edges.get(i);
         if(e != null)
            neighbours.add(graph.getNode(e.b));
      }//*/
   }
}
