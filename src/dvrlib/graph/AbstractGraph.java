/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010-2012
 * AbstractGraph.java
 */

package dvrlib.graph;

import java.lang.Iterable;

public abstract class AbstractGraph<Node extends AbstractGraphNode<Node, NodeData, EdgeData>, NodeData, EdgeData> {
   protected int nodeCount, edgeCount = 0, maxDegree = 0;

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
    * Returns the number of edges starting in node a.
    * Returns -1 if the node index is out of bounds.
    * O(1).
    */
   public int degree(int a) {
      AbstractGraphNode n = getNode(a);
      if(n == null)
         return -1;
      else
         return n.degree();
   }

   /**
    * Returns the largest degree.
    * O(calcMaxDegree()) if maxDegree was invalidated, O(1) otherwise.
    */
   public int maxDegree() {
      if(maxDegree < 0)
         calcMaxDegree();
      return maxDegree;
   }

   /**
    * Calculates the maximum degree.
    */
   protected abstract void calcMaxDegree();

   /**
    * Returns the node at the given index.
    * May return null if the node index is out of bounds.
    */
   public abstract Node getNode(int index);

   /**
    * Returns an iterable to the nodes of this graph.
    */
   public abstract Iterable<Node> nodeIterable();

   /**
    * Returns an iterable to the data of the nodes of this graph.
    * @see dvrlib.graph.AbstractGraph#nodesIterable()
    */
   public abstract Iterable<NodeData> nodeDataIterable();

   /**
    * Returns an iterable to the edges of the given node.
    */
   public abstract Iterable<dvrlib.generic.Tuple<EdgeData, Node>> edgeIterable(int index);

   /**
    * Returns true if there is an edge between nodes a and b, false otherwise.
    */
   public abstract boolean hasEdge(int a, int b);

   /**
    * Returns the data associated with the edge between nodes a and b.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   public abstract EdgeData getEdge(int a, int b) throws IllegalArgumentException;

   /**
    * Sets the data associated with the edge between nodes a and b, and returns the old data.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   public abstract EdgeData setEdgeData(int a, int b, EdgeData ed) throws IllegalArgumentException;

   /**
    * Adds an edge between nodes a and b.
    * @return true if the edge was added, false otherwise.
    */
   public abstract boolean addEdge(int a, int b, EdgeData ed);

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
         System.out.println("] :d " + getNode(i).degree());
      }
   }

   protected String printEdge(int a, int b) {
      return (hasEdge(a, b) ? "1" : "0");
   }
}
