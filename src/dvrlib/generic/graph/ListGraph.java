/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * ListGraph.java
 */

package dvrlib.generic.graph;

import java.util.Iterator;

/**
 * Graph class, using lists to keep track of edges.
 * This makes it slow at inserting and checking for edges, but fast at retreiving neighbouring nodes.
 */
public class ListGraph extends AbstractGraph<ListGraphNode> {
   protected final ListGraphNode nodes[];

   /**
    * ListGraph constructor.
    * @param nodeCount The number of nodes in this graph.
    */
   public ListGraph(int nodeCount) {
      super(nodeCount);
      nodes = new ListGraphNode[nodeCount];
      for(int i = 0; i < nodeCount; i++) {
         nodes[i] = new ListGraphNode(this, i);
      }
   }

   /**
    * Calculates the maximum degree.
    * O(n).
    */
   @Override
   protected void calcMaxDegree() {
      maxDegree = 0;
      for(int i = 0; i < nodes.length; i++) {
         maxDegree = Math.max(maxDegree, nodes[i].getDegree());
      }
   }

   /**
    * Returns the node with the given index.
    * O(1).
    */
   @Override
   public ListGraphNode getNode(int index) {
      if(checkIndex(index))
         return nodes[index];
      else
         return null;
   }

   /**
    * Returns true if there is an edge between nodes a and b, false otherwise.
    * O(1).
    */
   @Override
   public boolean hasEdge(int a, int b) {
      ListGraphNode n = getNode(a);
      if(n == null)
         return false;
      else
         return n.hasEdge(getNode(b));
   }

   /**
    * Adds an edge between nodes a and b.
    * @return true if an edge was added, false otherwise.
    * O(1) if the edge already existed, O(e) otherwise.
    */
   @Override
   public boolean addEdge(int a, int b) {
      ListGraphNode na = getNode(a), nb = getNode(b);
      if(na == null || nb == null)
         return false;
      else {
         na.neighbours.add(nb);
         edgeCount++;
         if(na.getDegree() > maxDegree)
            maxDegree = na.getDegree();
         return true;
      }
   }

   /**
    * Removes the edge between nodes a and b.
    * @return true if the edge was deleted, false otherwise.
    * O(1).
    */
   @Override
   public boolean removeEdge(int a, int b) {
      ListGraphNode na = getNode(a), nb = getNode(b);
      if(na == null || nb == null)
         return false;
      else {
         boolean removed = na.neighbours.remove(nb);
         if(removed) {
            edgeCount--;
            if(maxDegree <= na.getDegree() + 1)
               maxDegree = -1;
         }
         return removed;
      }
   }

   @Override
   public int getDegree(int a) {
      ListGraphNode na = getNode(a);
      if(na == null)
         return -1;
      else
         return na.getDegree();
   }

   @Override
   public Iterator<ListGraphNode> iterator() {
      return new ListGraphIterator(this);
   }
}
