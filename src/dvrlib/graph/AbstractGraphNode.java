/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010-2012
 * AbstractGraphNode.java
 */

package dvrlib.graph;

import java.util.Iterator;

public abstract class AbstractGraphNode<Node extends AbstractGraphNode, NodeData, EdgeData> {
   public final int      index;
   public       NodeData data;

   /**
    * AbstractGraphNode constructor.
    * @param index The index of this node.
    * O(1).
    */
   public AbstractGraphNode(int index, NodeData data) {
      this.index = index;
      this.data  = data;
   }

   /**
    * Returns true if there is an edge from this node to given node, false otherwise.
    */
   public abstract boolean hasEdge(Node that);

   /**
    * Returns the data associated with the edge from this node to the given node.
    */
   public abstract EdgeData getEdge(Node that);

   /**
    * Sets the data associated with the edge from this node to the given node and returns the old data.
    */
   public abstract EdgeData setEdgeData(Node that, EdgeData data);

   /**
    * Returns the degree of this node.
    */
   public abstract int degree();

   /**
    * Returns an iterable to the edges of this node.
    */
   public abstract java.lang.Iterable<dvrlib.generic.Tuple<EdgeData, Node>> edgeIterable();
}
