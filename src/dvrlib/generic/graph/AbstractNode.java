/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * AbstractNode.java
 */

package dvrlib.generic.graph;

import java.util.Collection;

public abstract class AbstractNode {
   public final int index;
   protected int degree;

   /**
    * AbstractNode constructor.
    * @param index The index of this node.
    * O(1).
    */
   public AbstractNode(int index) {
      this.index = index;
      degree = 0;
   }

   /**
    * Returns true if there is an edge from this node to given node, false otherwise.
    */
   public abstract boolean hasEdge(AbstractNode that);

   public abstract Collection<AbstractNode> getNeighbours();

   /**
    * Returns the degree of this node.
    * O(1).
    */
   public int getDegree() {
      return degree;
   }
}
