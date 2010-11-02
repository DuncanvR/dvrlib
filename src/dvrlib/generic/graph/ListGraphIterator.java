/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * ListGraphIterator.java
 */

package dvrlib.generic.graph;

public class ListGraphIterator implements java.util.Iterator<ListGraphNode> {
   protected final ListGraph graph;
   protected int node = 0;

   public ListGraphIterator(ListGraph graph) {
      this.graph = graph;
   }

   @Override
   public boolean hasNext() {
      return (node < graph.nodeCount);
   }

   @Override
   public ListGraphNode next() {
      return graph.nodes[node++];
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
   }

}
