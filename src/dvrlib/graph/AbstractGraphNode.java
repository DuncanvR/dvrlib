/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010
 * AbstractGraphNode.java
 */

package dvrlib.graph;

import java.util.Iterator;

public abstract class AbstractGraphNode<N extends AbstractGraphNode> {
   public final int index;

   /**
    * AbstractGraphNode constructor.
    * @param index The index of this node.
    * O(1).
    */
   public AbstractGraphNode(int index) {
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
