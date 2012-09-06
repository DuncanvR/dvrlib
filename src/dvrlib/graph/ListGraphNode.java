/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010
 * ListGraphNode.java
 */

package dvrlib.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListGraphNode<NodeData, EdgeData> extends AbstractGraphNode<ListGraphNode, NodeData, EdgeData> {
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
   public EdgeData setEdgeData(ListGraphNode that, EdgeData data) {
      return neighbours.put(that, data);
   }

   /**
    * Returns the degree of this node.
    * O(1).
    */
   @Override
   public int getDegree() {
      return neighbours.size();
   }

   /**
    * Returns an iterator to the neighbouring nodes of this node.
    * O(1).
    */
   @Override
   public Iterator<Map.Entry<ListGraphNode, EdgeData>> neighbourIterator() {
      return neighbours.entrySet().iterator();
   }
}
