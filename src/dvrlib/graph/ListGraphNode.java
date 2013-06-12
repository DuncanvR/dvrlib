/*
 * DvRlib - Graph
 * Duncan van Roermund, 2010-2012
 * ListGraphNode.java
 */

package dvrlib.graph;

import dvrlib.generic.Triple;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ListGraphNode<Id extends Comparable<Id>, NodeData, EdgeData> extends AbstractGraphNode<Id, ListGraphNode<Id, NodeData, EdgeData>, NodeData, EdgeData> {
   protected final ListGraph                                                graph;
   protected final HashMap<ListGraphNode<Id, NodeData, EdgeData>, EdgeData> outEdges = new HashMap<ListGraphNode<Id, NodeData, EdgeData>, EdgeData>();
   protected final HashSet<ListGraphNode<Id, NodeData, EdgeData>>           inEdges  = new HashSet<ListGraphNode<Id, NodeData, EdgeData>>();

   /**
    * ListGraphNode constructor.
    * @param id The identifier of this node.
    * @param graph The graph this node is in.
    * @param data The data that will be associated with this node.
    */
   public ListGraphNode(Id id, ListGraph graph, NodeData data) {
      super(id, data);
      this.graph = graph;
   }

   /**
    * Returns true if there is an edge from this node to the one given, false otherwise.
    * O(e).
    */
   @Override
   public boolean hasEdge(ListGraphNode<Id, NodeData, EdgeData> that) {
      if(that == null)
         return false;
      return outEdges.containsKey(that);
   }

   /**
    * Returns the data associated with the edge from this node to the given node.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   @Override
   public EdgeData edge(ListGraphNode<Id, NodeData, EdgeData> that) {
      return outEdges.get(that);
   }

   /**
    * Sets the data associated with the edge from this node to the given node and returns the old data.
    */
   @Override
   public EdgeData replaceEdge(ListGraphNode<Id, NodeData, EdgeData> that, EdgeData data) {
      return outEdges.put(that, data);
   }

   /**
    * Returns the number of edges coming into this node.
    * O(1).
    */
   @Override
   public int inDegree() {
      return inEdges.size();
   }

   /**
    * Returns the number of edges going out of this node.
    * O(1).
    */
   @Override
   public int outDegree() {
      return outEdges.size();
   }

   /**
    * Returns an iterator to the outgoing edges of this node.
    * O(1).
    */
   @Override
   public Iterator<Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>>> outEdgesIterator() {
      return new Iterator<Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>>>() {
            protected final Iterator<ListGraphNode<Id, NodeData, EdgeData>> it = outEdges.keySet().iterator();

            @Override
            public boolean hasNext() {
               return it.hasNext();
            }
            @Override
            public Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>> next() {
               ListGraphNode<Id, NodeData, EdgeData> n = it.next();
               return new Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>>(ListGraphNode.this, edge(n), n);
            }
            @Override
            public void remove() {
               throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
            }
         };
   }

   /**
    * Returns an iterator to the incoming edges of this node.
    * O(1).
    */
   @Override
   public Iterator<Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>>> inEdgesIterator() {
      return new Iterator<Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>>>() {
            protected final Iterator<ListGraphNode<Id, NodeData, EdgeData>> it = inEdges.iterator();

            @Override
            public boolean hasNext() {
               return it.hasNext();
            }
            @Override
            public Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>> next() {
               ListGraphNode<Id, NodeData, EdgeData> n = it.next();
               return new Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>>(n, n.edge(ListGraphNode.this), ListGraphNode.this);
            }
            @Override
            public void remove() {
               throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
            }
         };
   }

   @Override
   public String toString() {
      return "dvrlib.graph.ListGraphNode(" + data + ", " + inEdges.size() + " in-edges, " + outEdges.size() + " out-edges)";
   }
}
