/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * AbstractGraph.java
 */

package dvrlib.generic;

import java.util.Collection;

public abstract class AbstractGraph<N extends AbstractNode, E extends AbstractEdge> {
   protected int nodeCount, edgeCount, maxDegree;

   /**
    * AbstractGraph constructor.
    * O(1).
    */
   public AbstractGraph(int nodeCount) {
      this.nodeCount = nodeCount;
      edgeCount = 0;
      maxDegree = 0;
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
    * Increases the degree of node a.
    * @see AbstractGraph#incDegree(dvrlib.generic.AbstractNode)
    * O(1).
    */
   protected void incDegree(int a) {
      incDegree(getNode(a));
   }

   /**
    * Increases the degree of the given node.
    */
   protected void incDegree(N node) {
      if(node != null) {
         if(++node.degree > getMaxDegree())
            maxDegree = node.degree;
      }
   }

   /**
    * Decreases the degree of node a.
    * @see AbstractGraph#decDegree(dvrlib.generic.AbstractNode)
    * O(1).
    */
   protected void decDegree(int a) {
      decDegree(getNode(a));
   }

   /**
    * Decreases the degree of the given node.
    * If the degree of the given node was equal to maxDegree, maxDegree will be invalidated.
    * O(1).
    */
   protected void decDegree(N node) {
      if(node != null)
         if(node.degree-- == maxDegree)
            maxDegree = -1;
   }

   /**
    * Returns the node at the given index.
    */
   public abstract N getNode(int index);

   /**
    * Returns the neighbouring nodes of the node with the given index.
    */
   public abstract Collection<AbstractNode> getNeighbours(int index);

   /**
    * Returns true if there is an edge between nodes a and b, false otherwise.
    * O(getEdge(int, int)).
    */
   public boolean hasEdge(int a, int b) {
      return (getEdge(a, b) != null);
   }

   /**
    * Returns the edge between the nodes with the given indices.
    */
   public abstract E getEdge(int a, int b);

   /**
    * Adds an edge between nodes a and b.
    * @return true if the edge was added, false otherwise.
    */
   public abstract E addEdge(int a, int b);

   /**
    * Adds an edge between the given nodes.
    * @see AbstractGraph#addEdge(int, int)
    */
   public E addEdge(N a, N b) {
      return addEdge(a.index, b.index);
   }

   /**
    * Removes the edge between nodes a and b.
    * @return true if the edge was removed, false otherwise.
    */
   public abstract E removeEdge(int a, int b);

   /**
    * Removes the edge between the given nodes.
    * @see AbstractGraph#removeEdge(int, int)
    */
   public E removeEdge(N a, N b) {
      return removeEdge(a.index, b.index);
   }

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
