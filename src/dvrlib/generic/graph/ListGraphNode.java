/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * ListGraphNode.java
 */

package dvrlib.generic.graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;

public class ListGraphNode extends AbstractNode<ListGraphNode> {
   protected final ListGraph graph;
   protected final Collection<ListGraphNode> neighbours;

   public ListGraphNode(ListGraph graph, int index) {
      super(index);

      this.graph = graph;
      neighbours = new LinkedList<ListGraphNode>();
   }

   @Override
   public boolean hasEdge(ListGraphNode that) {
      if(that == null)
         return false;
      return neighbours.contains(that);
   }

   @Override
   public Collection<ListGraphNode> getNeighbours() {
      return neighbours;
   }

   @Override
   public int getDegree() {
      return neighbours.size();
   }
}
