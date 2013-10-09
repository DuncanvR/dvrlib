/*
 * DvRlib - Graph
 * Copyright (C) Duncan van Roermund, 2010-2013
 * AbstractGraphNode.java
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
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

   @Override
   public int hashCode() {
      return 234 + 89 * id.hashCode();
   }
}
