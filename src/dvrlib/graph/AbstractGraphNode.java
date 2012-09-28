/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010-2012
 * AbstractGraphNode.java
 */

package dvrlib.graph;

import dvrlib.generic.Tuple;

import java.lang.Iterable;

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
   public abstract EdgeData edge(Node that);

   /**
    * Sets the data associated with the edge from this node to the given node and returns the old data.
    */
   public abstract EdgeData replaceEdge(Node that, EdgeData data);

   /**
    * Returns the number of edges coming into this node.
    */
   public abstract int inDegree();

   /**
    * Returns the number of edges going out of this node.
    */
   public abstract int outDegree();

   /**
    * Returns an iterable to the outgoing edges of this node.
    */
   public abstract Iterable<Tuple<EdgeData, Node>> outEdgesIterable();

   /**
    * Returns an iterable to the incoming edges of this node.
    */
   public abstract Iterable<Tuple<EdgeData, Node>> inEdgesIterable();
}
