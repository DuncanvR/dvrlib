/*
 * DvRlib - Graph
 * Duncan van Roermund, 2010-2012
 * ListGraphNode.java
 */

package dvrlib.graph;

import dvrlib.generic.Pair;

import java.lang.Iterable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListGraphNode<NodeData, EdgeData> extends AbstractGraphNode<ListGraphNode<NodeData, EdgeData>, NodeData, EdgeData> {
   protected class EdgeIterator implements Iterator<Pair<EdgeData, ListGraphNode<NodeData, EdgeData>>> {
      protected Iterator<Map.Entry<ListGraphNode<NodeData, EdgeData>, EdgeData>> it;

      public EdgeIterator(Iterator<Map.Entry<ListGraphNode<NodeData, EdgeData>, EdgeData>> it) {
         this.it = it;
      }

      @Override
      public boolean hasNext() {
         return it.hasNext();
      }
      @Override
      public Pair<EdgeData, ListGraphNode<NodeData, EdgeData>> next() {
         Map.Entry<ListGraphNode<NodeData, EdgeData>, EdgeData> entry = it.next();
         return new Pair<EdgeData, ListGraphNode<NodeData, EdgeData>>(entry.getValue(), entry.getKey());
      }
      @Override
      public void remove() {
         throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
      }
   }

   protected final ListGraph                                        graph;
   protected final Map<ListGraphNode<NodeData, EdgeData>, EdgeData> inEdges, outEdges;

   /**
    * ListGraphNode constructor.
    * @param graph The graph this node is in.
    * @param index The index of this node.
    */
   public ListGraphNode(ListGraph graph, int index, NodeData data) {
      super(index, data);
      this.graph = graph;
      inEdges  = new HashMap<ListGraphNode<NodeData, EdgeData>, EdgeData>();
      outEdges = new HashMap<ListGraphNode<NodeData, EdgeData>, EdgeData>();
   }

   /**
    * Returns true if there is an edge from this node to the one given, false otherwise.
    * O(e).
    */
   @Override
   public boolean hasEdge(ListGraphNode<NodeData, EdgeData> that) {
      if(that == null)
         return false;
      return outEdges.containsKey(that);
   }

   /**
    * Returns the data associated with the edge from this node to the given node.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   @Override
   public EdgeData edge(ListGraphNode<NodeData, EdgeData> that) {
      return outEdges.get(that);
   }

   /**
    * Sets the data associated with the edge from this node to the given node and returns the old data.
    */
   @Override
   public EdgeData replaceEdge(ListGraphNode<NodeData, EdgeData> that, EdgeData data) {
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
    * Returns an iterable to the outgoing edges of this node.
    * O(1).
    */
   @Override
   public Iterable<Pair<EdgeData, ListGraphNode<NodeData, EdgeData>>> outEdgesIterable() {
      return new Iterable<Pair<EdgeData, ListGraphNode<NodeData, EdgeData>>>() {
         @Override
         public Iterator<Pair<EdgeData, ListGraphNode<NodeData, EdgeData>>> iterator() {
            return new EdgeIterator(outEdges.entrySet().iterator());
         }
      };
   }

   /**
    * Returns an iterable to the incoming edges of this node.
    * O(1).
    */
   @Override
   public Iterable<Pair<EdgeData, ListGraphNode<NodeData, EdgeData>>> inEdgesIterable() {
      return new Iterable<Pair<EdgeData, ListGraphNode<NodeData, EdgeData>>>() {
         @Override
         public Iterator<Pair<EdgeData, ListGraphNode<NodeData, EdgeData>>> iterator() {
            return new EdgeIterator(inEdges.entrySet().iterator());
         }
      };
   }
}
