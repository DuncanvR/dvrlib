/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * AbstractNode.java
 */

package dvrlib.generic.graph;

import java.util.Iterator;

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
    * Returns an iterator to the neighbouring nodes.
    */
   public abstract Iterator<N> neighbourIterator();

   /**
    * Returns the degree of this node.
    */
   public abstract int getDegree();
}
