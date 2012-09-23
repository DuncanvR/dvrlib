/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010-2012
 * ListGraphNode.java
 */

package dvrlib.graph;

import dvrlib.generic.Tuple;

import java.lang.Iterable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListGraphNode<NodeData, EdgeData> extends AbstractGraphNode<ListGraphNode<NodeData, EdgeData>, NodeData, EdgeData> {
   protected final ListGraph                    graph;
   protected final Map<ListGraphNode, EdgeData> neighbours;

   /**
    * ListGraphNode constructor.
    * @param graph The graph this node is in.
    * @param index The index of this node.
    */
   public ListGraphNode(ListGraph graph, int index, NodeData data) {
      super(index, data);
      this.graph = graph;
      neighbours = new HashMap();
   }

   /**
    * Returns true if there is an edge from this node to the one given, false otherwise.
    * O(e).
    */
   @Override
   public boolean hasEdge(ListGraphNode that) {
      if(that == null)
         return false;
      return neighbours.containsKey(that);
   }

   /**
    * Returns the data associated with the edge from this node to the given node.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   @Override
   public EdgeData getEdge(ListGraphNode that) {
      return neighbours.get(that);
   }

   /**
    * Sets the data associated with the edge from this node to the given node and returns the old data.
    */
   public EdgeData setEdgeData(ListGraphNode<NodeData, EdgeData> that, EdgeData data) {
      return neighbours.put(that, data);
   }

   /**
    * Returns the degree of this node.
    * O(1).
    */
   @Override
   public int degree() {
      return neighbours.size();
   }

   /**
    * Returns an iterable to the edges of this node.
    * O(1).
    */
   @Override
   public Iterable<Tuple<EdgeData, ListGraphNode<NodeData, EdgeData>>> edgeIterable() {
      return new Iterable<Tuple<EdgeData, ListGraphNode<NodeData, EdgeData>>>() {
         @Override
         public Iterator<Tuple<EdgeData, ListGraphNode<NodeData, EdgeData>>> iterator() {
            return new Iterator<Tuple<EdgeData, ListGraphNode<NodeData, EdgeData>>>() {
               protected Iterator<Map.Entry<ListGraphNode, EdgeData>> it = neighbours.entrySet().iterator();

               @Override
               public boolean hasNext() {
                  return it.hasNext();
               }
               @Override
               public Tuple<EdgeData, ListGraphNode<NodeData, EdgeData>> next() {
                  Map.Entry<ListGraphNode, EdgeData> entry = it.next();
                  return new Tuple<EdgeData, ListGraphNode<NodeData, EdgeData>>(entry.getValue(), entry.getKey());
               }
               @Override
               public void remove() {
                  throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
               }
            };
         }
      };
   }
}
