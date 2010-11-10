/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010
 * ListGraphNode.java
 */

package dvrlib.graph;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class ListGraphNode extends AbstractNode<ListGraphNode> {
   protected final ListGraph graph;
   protected final Collection<ListGraphNode> neighbours;

   /**
    * ListGraphNode constructor.
    * @param graph The graph this node is in.
    * @param index The index of this node.
    */
   public ListGraphNode(ListGraph graph, int index) {
      super(index);

      this.graph = graph;
      neighbours = new LinkedList<ListGraphNode>();
   }

   /**
    * Returns true if there is an edge from this node to the one given, false otherwise.
    * O(e).
    */
   @Override
   public boolean hasEdge(ListGraphNode that) {
      if(that == null)
         return false;
      return neighbours.contains(that);
   }

   /**
    * Returns the degree of this node.
    * O(1).
    */
   @Override
   public int getDegree() {
      return neighbours.size();
   }

   /**
    * Returns an iterator to the neighbouring nodes of this node.
    * O(1).
    */
   @Override
   public Iterator<ListGraphNode> neighbourIterator() {
      return neighbours.iterator();
   }
}
