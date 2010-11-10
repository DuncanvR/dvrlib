/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010
 * AbstractGraph.java
 */

package dvrlib.graph;

import java.util.Iterator;

public abstract class AbstractGraph<N extends AbstractGraphNode> implements Iterable<N> {
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
   public int getNodeCount() {
      return nodeCount;
   }

   /**
    * Returns the number of edges in this graph.
    * O(1).
    */
   public int getEdgeCount() {
      return edgeCount;
   }

   /**
    * Returns the number of edges starting in node a.
    */
   public abstract int getDegree(int a);

   /**
    * Returns the largest degree.
    * O(calcMaxDegree()) if maxDegree was invalidated, O(1) otherwise.
    */
   public int getMaxDegree() {
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
    */
   public abstract N getNode(int index);

   /**
    * Returns an iterator to the neighbouring nodes of the given node.
    */
   public Iterator<N> neighbourIterator(int index) {
      return getNode(index).neighbourIterator();
   }

   /**
    * Returns true if there is an edge between nodes a and b, false otherwise.
    */
   public abstract boolean hasEdge(int a, int b);

   /**
    * Adds an edge between nodes a and b.
    * @return true if the edge was added, false otherwise.
    */
   public abstract boolean addEdge(int a, int b);

   /**
    * Removes the edge between nodes a and b.
    * @return true if the edge was removed, false otherwise.
    */
   public abstract boolean removeEdge(int a, int b);

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
         System.out.println("] :d " + getNode(i).getDegree());
      }
   }

   protected String printEdge(int a, int b) {
      return (hasEdge(a, b) ? "1" : "0");
   }
}
