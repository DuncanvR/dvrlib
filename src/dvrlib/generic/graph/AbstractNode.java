/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * AbstractNode.java
 */

package dvrlib.generic.graph;

import java.util.Collection;

public abstract class AbstractNode<N extends AbstractNode> {
   public final int index;

   /**
    * AbstractNode constructor.
    * @param index The index of this node.
    * O(1).
    */
   public AbstractNode(int index) {
      this.index = index;
   }

   /**
    * Returns true if there is an edge from this node to given node, false otherwise.
    */
   public abstract boolean hasEdge(N that);

   /**
    * Returns the neighbouring nodes of this node.
    */
   public abstract Collection<N> getNeighbours();

   /**
    * Returns the degree of this node.
    */
   public abstract int getDegree();
}
