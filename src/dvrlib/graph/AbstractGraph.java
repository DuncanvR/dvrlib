/*
 * DvRlib - Graph
 * Duncan van Roermund, 2010-2012
 * AbstractGraph.java
 */

package dvrlib.graph;

import dvrlib.generic.Tuple;

import java.lang.Iterable;

public abstract class AbstractGraph<Node extends AbstractGraphNode<Node, NodeData, EdgeData>, NodeData, EdgeData> {
   protected int nodeCount, edgeCount = 0, maxInDegree = 0, maxOutDegree = 0;

   /**
    * AbstractGraph constructor.
    * O(1).
    */
   public AbstractGraph(int nodeCount) {
      this.nodeCount = nodeCount;
   }

   /**
    * Returns true if the given index is valid for this graph, false otherwise.
    * O(1).
    */
   public boolean checkIndex(int a) {
      return (a >= 0 && a < nodeCount);
   }

   /**
    * Returns the number of nodes in this graph.
    * O(1).
    */
   public int nodeCount() {
      return nodeCount;
   }

   /**
    * Returns the number of edges in this graph.
    * O(1).
    */
   public int edgeCount() {
      return edgeCount;
   }

   /**
    * Returns the number of edges coming into the given node.
    * Returns -1 if the node index is out of bounds.
    * O(1).
    */
   public int inDegree(int a) {
      AbstractGraphNode n = node(a);
      if(n == null)
         return -1;
      else
         return n.inDegree();
   }

   /**
    * Returns the number of edges going out of the given node.
    * Returns -1 if the node index is out of bounds.
    * O(1).
    */
   public int outDegree(int a) {
      AbstractGraphNode n = node(a);
      if(n == null)
         return -1;
      else
         return n.outDegree();
   }

   /**
    * Returns the largest in-degree.
    * @see AbstractGraph#inDegree(int)
    * @see AbstractGraph#calcMaxInDegree()
    * O(calcMaxInDegree()) if maxInDegree was invalidated, O(1) otherwise.
    */
   public int maxInDegree() {
      if(maxInDegree < 0)
         calcMaxInDegree();
      return maxInDegree;
   }

   /**
    * Returns the largest out-degree.
    * @see AbstractGraph#outDegree(int)
    * @see AbstractGraph#calcMaxOutDegree()
    * O(calcMaxOutDegree()) if maxOutDegree was invalidated, O(1) otherwise.
    */
   public int maxOutDegree() {
      if(maxOutDegree < 0)
         calcMaxOutDegree();
      return maxOutDegree;
   }

   /**
    * Calculates the maximum in-degree.
    */
   protected abstract void calcMaxInDegree();

   /**
    * Calculates the maximum out-degree.
    */
   protected abstract void calcMaxOutDegree();

   /**
    * Returns the node at the given index.
    * May return null if the node index is out of bounds.
    */
   public abstract Node node(int index);

   /**
    * Returns an iterable to the nodes of this graph.
    */
   public abstract Iterable<Node> nodeIterable();

   /**
    * Returns an iterable to the data of the nodes of this graph.
    * @see AbstractGraph#nodesIterable()
    */
   public abstract Iterable<NodeData> nodeDataIterable();

   /**
    * Returns an iterable to the outgoing edges of the given node.
    */
   public abstract Iterable<Tuple<EdgeData, Node>> outEdgesIterable(int index);

   /**
    * Returns an iterable to the incoming edges of the given node.
    */
   public abstract Iterable<Tuple<EdgeData, Node>> inEdgesIterable(int index);

   /**
    * Returns true if there is an edge between nodes a and b, false otherwise.
    */
   public abstract boolean hasEdge(int a, int b);

   /**
    * Returns the data associated with the edge between nodes a and b.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   public abstract EdgeData edge(int a, int b) throws IllegalArgumentException;

   /**
    * Adds an edge between nodes a and b.
    * @return true if the edge was added, false otherwise.
    */
   public abstract boolean addEdge(int a, int b, EdgeData ed);

   /**
    * Sets the data associated with the edge between nodes a and b, and returns the old data.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   public abstract EdgeData replaceEdge(int a, int b, EdgeData ed) throws IllegalArgumentException;

   /**
    * Removes the edge between nodes a and b.
    * @return The data that was associated with the edge.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   public abstract EdgeData removeEdge(int a, int b) throws IllegalArgumentException;

   @Override
   public String toString() {
      return "dvrlib.generic.AbstractGraph(" + nodeCount + ")[" + edgeCount + "]";
   }

   /**
    * Prints this graph.
    */
   public void print() {
      System.out.println(this);
      for(int i = 0; i < nodeCount; i++) {
         System.out.print("\t[");
         for(int j = 0; j < nodeCount; j++) {
            System.out.print(printEdge(i, j) + " ");
         }
         System.out.println("] :d " + node(i).outDegree());
      }
   }

   protected String printEdge(int a, int b) {
      return (hasEdge(a, b) ? "1" : "0");
   }
}
