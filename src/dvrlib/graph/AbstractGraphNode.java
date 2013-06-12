/*
 * DvRlib - Graph
 * Duncan van Roermund, 2010-2013
 * AbstractGraphNode.java
 */

package dvrlib.graph;

import dvrlib.generic.Triple;

import java.util.Iterator;

public abstract class AbstractGraphNode<Id extends Comparable<Id>, Node extends AbstractGraphNode, NodeData, EdgeData>
      implements Comparable<AbstractGraphNode<Id, Node, NodeData, EdgeData>> {
   public final Id       id;
   public       NodeData data;

   /**
    * AbstractGraphNode constructor.
    * @param id The identifier of this node.
    * @param data The data that will be associated with this node.
    * O(1).
    */
   public AbstractGraphNode(Id id, NodeData data) {
      this.id   = id;
      this.data = data;
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
    * Returns an iterator to the outgoing edges of this node.
    */
   public abstract Iterator<Triple<Node, EdgeData, Node>> outEdgesIterator();

   /**
    * Returns an iterator to the incoming edges of this node.
    */
   public abstract Iterator<Triple<Node, EdgeData, Node>> inEdgesIterator();

   /**
    * Compares this object with the specified object for order.
    */
   public int compareTo(AbstractGraphNode<Id, Node, NodeData, EdgeData> that) {
      return id.compareTo(that.id);
   }
}
