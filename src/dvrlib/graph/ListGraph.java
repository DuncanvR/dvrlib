/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010-2012
 * ListGraph.java
 */

package dvrlib.graph;

import dvrlib.generic.Tuple;

import java.lang.Iterable;
import java.util.Iterator;
import java.util.Map;

/**
 * Graph class, using lists to keep track of edges.
 * This makes it slow at inserting and checking for edges, but fast at retreiving neighbouring nodes.
 */
public class ListGraph<NodeData, EdgeData> extends AbstractGraph<ListGraphNode<NodeData, EdgeData>, NodeData, EdgeData> {
   protected final ListGraphNode<NodeData, EdgeData> nodes[];

   /**
    * ListGraph constructor.
    * @param nodeCount The number of nodes in this graph.
    */
   @SuppressWarnings("unchecked")
   public ListGraph(int nodeCount) {
      super(nodeCount);
      nodes = new ListGraphNode[nodeCount];
      for(int i = 0; i < nodeCount; i++) {
         nodes[i] = new ListGraphNode<NodeData, EdgeData>(this, i, null);
      }
   }

   /**
    * Calculates the maximum in-degree.
    * O(n).
    */
   @Override
   protected void calcMaxInDegree() {
      maxInDegree = 0;
      for(int i = 0; i < nodes.length; i++) {
         maxInDegree = Math.max(maxInDegree, nodes[i].inDegree());
      }
   }

   /**
    * Calculates the maximum out-degree.
    * O(n).
    */
   @Override
   protected void calcMaxOutDegree() {
      maxOutDegree = 0;
      for(int i = 0; i < nodes.length; i++) {
         maxOutDegree = Math.max(maxOutDegree, nodes[i].outDegree());
      }
   }

   /**
    * Returns the node with the given index, or null if the index is out of bounds.
    * O(1).
    */
   @Override
   public ListGraphNode<NodeData, EdgeData> node(int index) {
      if(checkIndex(index))
         return nodes[index];
      else
         return null;
   }

   /**
    * Returns true if there is an edge between nodes a and b, false otherwise.
    * O(1).
    */
   @Override
   public boolean hasEdge(int a, int b) {
      ListGraphNode<NodeData, EdgeData> n = node(a);
      if(n == null)
         return false;
      else
         return n.hasEdge(node(b));
   }

   /**
    * Returns the data associated with the edge between nodes a and b.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    * @see AbstractGraph#edge(int, int)
    */
   @Override
   public EdgeData edge(int a, int b) {
      ListGraphNode<NodeData, EdgeData> na = node(a), nb = node(b);
      if(na == null || nb == null || !na.hasEdge(nb))
         throw new IllegalArgumentException("Edge " + a + "," + b + " does not exist");
      return na.edge(nb);
   }

   /**
    * Adds an edge between nodes a and b.
    * @return true if the edge was added, false otherwise.
    * O(1) if the edge already existed, O(e) otherwise.
    */
   @Override
   public boolean addEdge(int a, int b, EdgeData ed) {
      if(a == b)
         return false;
      ListGraphNode<NodeData, EdgeData> na = node(a), nb = node(b);
      if(na != null && nb != null && !na.hasEdge(nb)) {
         na.outEdges.put(nb, ed);
         nb.inEdges.put(na, ed);
         edgeCount++;
         if(na.outDegree() > maxOutDegree())
            maxOutDegree = na.outDegree();
         if(nb.inDegree() > maxInDegree())
            maxInDegree = nb.inDegree();
         return true;
      }
      return false;
   }

   /**
    * Sets the data associated with the edge between nodes a and b, and returns the old data.
    * @return The old data associated with the indicated edge.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   @Override
   public EdgeData replaceEdge(int a, int b, EdgeData ed) throws IllegalArgumentException {
      ListGraphNode<NodeData, EdgeData> na = node(a), nb = node(b);
      if(na == null || nb == null || !na.hasEdge(nb))
         throw new IllegalArgumentException("Edge " + a + "," + b + " does not exist");
      EdgeData edA = na.replaceEdge(nb, ed),
               edB = nb.replaceEdge(na, ed);
      assert(edA == edB);
      return edA;
   }

   /**
    * Removes the edge between nodes a and b.
    * @return true if the edge was deleted, false otherwise.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    * @see AbstractGraph#removeEdge(int, int)
    * O(1).
    */
   @Override
   public EdgeData removeEdge(int a, int b) throws IllegalArgumentException {
      ListGraphNode<NodeData, EdgeData> na = node(a), nb = node(b);
      if(na == null || nb == null || !na.hasEdge(nb))
         throw new IllegalArgumentException("Edge " + a + "," + b + " does not exist");
      edgeCount--;
      if(maxInDegree <= nb.inDegree())
         maxInDegree = -1;
      if(maxOutDegree <= na.outDegree())
         maxOutDegree = -1;
      EdgeData edA = na.outEdges.remove(nb),
               edB = nb.inEdges.remove(na);
      assert(edA == edB);
      return edA;
   }

   /**
    * Returns an iterable to the nodes of this graph.
    */
   @Override
   public Iterable<ListGraphNode<NodeData, EdgeData>> nodeIterable() {
      return new Iterable<ListGraphNode<NodeData, EdgeData>>() {
         @Override
         public Iterator<ListGraphNode<NodeData, EdgeData>> iterator() {
            return new Iterator<ListGraphNode<NodeData, EdgeData>>() {
               protected int node = 0;

               @Override
               public boolean hasNext() {
                  return (node < nodeCount);
               }
               @Override
               public ListGraphNode<NodeData, EdgeData> next() {
                  return nodes[node++];
               }
               @Override
               public void remove() {
                  throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
               }
            };
         }
      };
   }

   /**
    * Returns an iterable to the data of the nodes of this graph.
    * @see dvrlib.graph.ListGraph#nodeIterable()
    */
   @Override
   public Iterable<NodeData> nodeDataIterable() {
      return new Iterable<NodeData>() {
         @Override
         public Iterator<NodeData> iterator() {
            return new Iterator<NodeData>() {
               protected int node = 0;

               @Override
               public boolean hasNext() {
                  return (node < nodeCount);
               }
               @Override
               public NodeData next() {
                  return nodes[node++].data;
               }
               @Override
               public void remove() {
                  throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
               }
            };
         }
      };
   }

   /**
    * Returns an iterable to the outgoing edges of the given node.
    */
   @Override
   public Iterable<Tuple<EdgeData, ListGraphNode<NodeData, EdgeData>>> outEdgesIterable(int index) {
      return node(index).outEdgesIterable();
   }

   /**
    * Returns an iterable to the incoming edges of the given node.
    */
   @Override
   public Iterable<Tuple<EdgeData, ListGraphNode<NodeData, EdgeData>>> inEdgesIterable(int index) {
      return node(index).inEdgesIterable();
   }
}
