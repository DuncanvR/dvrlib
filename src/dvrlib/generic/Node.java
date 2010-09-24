/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Node.java
 */

package dvrlib.generic;

public class Node extends AbstractNode {
   protected final Graph graph;
   protected final Edge edges[];
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
      edges = new Edge[graph.nodeCount];
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
   @Override
   public boolean hasEdge(AbstractNode that) {
      return hasEdge(that.index);
   }

   /**
    * Returns the edge to the node with the given index.
    * O(1).
    */
   public Edge getEdge(int index) {
      if(index < 0 || index >= graph.nodeCount)
         return null;
      return edges[index];
   }

   @Override
   public String toString() {
      String s = "dvrlib.generic.Node [";
      for(int i = 0; i < graph.nodeCount; i++) {
         if(edges[i] == null)
            s += "-,";
         else
            s += edges[i].b + ",";
      }
      return s + "]";
   }
}
