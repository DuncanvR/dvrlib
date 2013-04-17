/*
 * DvRlib - Graph
 * Duncan van Roermund, 2010-2013
 * ListGraph.java
 */

package dvrlib.graph;

import dvrlib.container.AbstractDisjointSetForest;
import dvrlib.generic.IterableOnce;
import dvrlib.generic.Pair;
import dvrlib.generic.Triple;

import java.util.Iterator;
import java.util.Map;
import java.util.HashSet;

/**
 * Graph class, based on an adjacency list implementation.
 * This makes it slow at inserting and checking for edges, but fast at retrieving neighbouring nodes, compared to an adjacency matrix approach.
 */
public class ListGraph<Id, NodeData, EdgeData> extends AbstractGraph<Id, ListGraphNode<Id, NodeData, EdgeData>, NodeData, EdgeData> {
   protected final HashSet<ListGraphNode<Id, NodeData, EdgeData>> nodes = new HashSet<ListGraphNode<Id, NodeData, EdgeData>>();
   protected final AbstractDisjointSetForest<Id, ListGraphNode<Id, NodeData, EdgeData>> map = new AbstractDisjointSetForest<Id, ListGraphNode<Id, NodeData, EdgeData>>() {
         @Override
         protected Pair<HashSet<Id>, ListGraphNode<Id, NodeData, EdgeData>> merge(Pair<HashSet<Id>, ListGraphNode<Id, NodeData, EdgeData>> t1,
                                                                                  Pair<HashSet<Id>, ListGraphNode<Id, NodeData, EdgeData>> t2) {
            // Merge outgoing edges of t2.b into t1.b
            for(Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>> t :
                  new IterableOnce<Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>>>(t2.b.outEdgesIterator())) {
               if(t.a != t1.b) {
                  if(hasEdge(t1.b, t.a))
                     replaceEdge(t1.b, t.a, mergeEdgeData(edge(t1.b, t.a), t.b));
                  else
                     addEdge(t1.b, t.a, t.b);
               }
            }
            // Merge incoming edges of t2.b into t1.b
            for(Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>> t :
                  new IterableOnce<Triple<ListGraphNode<Id, NodeData, EdgeData>, EdgeData, ListGraphNode<Id, NodeData, EdgeData>>>(t2.b.inEdgesIterator())) {
               if(t.a != t1.b) {
                  if(hasEdge(t.a, t1.b))
                     replaceEdge(t.a, t1.b, mergeEdgeData(edge(t.a, t1.b), t.b));
                  else
                     addEdge(t.a, t1.b, t.b);
               }
            }
            // Merge sets of nodes
            t1.a.addAll(t2.a);
            // Merge node data
            t1.b.data = mergeNodeData(t1.b.data, t2.b.data);
            // Remove node t2.b from the graph
            while(t2.b.inDegree() > 0) {
               ListGraph.this.removeEdge(t2.b.inEdges.iterator().next(), t2.b);
            }
            while(t2.b.outDegree() > 0) {
               ListGraph.this.removeEdge(t2.b, t2.b.outEdges.keySet().iterator().next());
            }
            nodes.remove(t2.b);
            // Return merged pair
            return t1;
         }
      };

   protected int edgeCount = 0;

   /**
    * Returns the number of nodes in this graph.
    */
   public int nodeCount() {
      return nodes.size();
   }

   /**
    * Returns the number of edges in this graph.
    */
   public int edgeCount() {
      return edgeCount;
   }

   /**
    * Calculates the maximum in-degree.
    * O(n).
    */
   @Override
   protected void calcMaxInDegree() {
      maxInDegree = 0;
      for(ListGraphNode n : nodes) {
         maxInDegree = Math.max(maxInDegree, n.inDegree());
      }
   }

   /**
    * Calculates the maximum out-degree.
    * O(n).
    */
   @Override
   protected void calcMaxOutDegree() {
      maxOutDegree = 0;
      for(ListGraphNode n : nodes) {
         maxOutDegree = Math.max(maxOutDegree, n.outDegree());
      }
   }

   /**
    * Adds a new node to this graph.
    * @param id The identifier of the new node.
    * @param nd The data that will be associated with the node.
    * @return The newly added node.
    * @throws IllegalArgumentException If the given identifier is already in use in this graph.
    */
   public ListGraphNode<Id, NodeData, EdgeData> add(Id id, NodeData nd) {
      if(contains(id))
         throw new IllegalArgumentException("Identifier " + id + " is already in use");
      ListGraphNode<Id, NodeData, EdgeData> node = new ListGraphNode<Id, NodeData, EdgeData>(id, this, nd);
      nodes.add(node);
      map.add(id, node);
      return node;
   }

   /**
    * Indicates whether this graph contains a node with the given identifier.
    * @param id The identifier to search for.
    * @return <code>true</code> if such a node exists, <code>false</code> otherwise.
    */
   public boolean contains(Id id) {
      return map.contains(id);
   }

   /**
    * Returns the identified node.
    */
   public ListGraphNode<Id, NodeData, EdgeData> node(Id id) {
      return map.retrieveSet(id).b;
   }

   /**
    * Removes the given node from this graph and returns its associated data.
    * @return The old data that was associated with the node.
    * @see ListGraph#remove(java.lang.Object)
    */
   @Override
   public NodeData remove(ListGraphNode<Id, NodeData, EdgeData> node) {
      if(node.inDegree() == maxInDegree)
         maxInDegree = -1;
      while(node.inDegree() > 0) {
         removeEdge(node.inEdges.iterator().next(), node);
      }
      if(node.outDegree() == maxOutDegree)
         maxOutDegree = -1;
      while(node.outDegree() > 0) {
         removeEdge(node, node.outEdges.keySet().iterator().next());
      }
      nodes.remove(node);
      map.remove(node.id);
      return node.data;
   }

   /**
    * Merges the nodes <code>a</code> and <code>b</code> into the returned node.
    */
   @Override
   public void merge(ListGraphNode<Id, NodeData, EdgeData> a, ListGraphNode<Id, NodeData, EdgeData> b) {
      map.union(a.id, b.id);
   }

   /**
    * Returns an iterator to the nodes of this graph.
    */
   @Override
   public Iterator<ListGraphNode<Id, NodeData, EdgeData>> nodeIterator() {
      return nodes.iterator();
   }

   /**
    * Returns an iterator to the data of the nodes of this graph.
    * @see ListGraph#nodeIterator()
    */
   @Override
   public Iterator<NodeData> nodeDataIterator() {
      return new Iterator<NodeData>() {
         protected Iterator<ListGraphNode<Id, NodeData, EdgeData>> it = nodes.iterator();

         @Override
         public boolean hasNext() {
            return it.hasNext();
         }
         @Override
         public NodeData next() {
            return it.next().data;
         }
         @Override
         public void remove() {
            throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
         }
      };
   }

   /**
    * Returns true if there is an edge between nodes <code>a</code> and <code>b</code>, false otherwise.
    * @return <code>true</code> if the edge exists, <code>false</code> otherwise.
    * O(1).
    */
   @Override
   public boolean hasEdge(ListGraphNode<Id, NodeData, EdgeData> a, ListGraphNode<Id, NodeData, EdgeData> b) {
      return a.hasEdge(b);
   }

   /**
    * Returns the data associated with the edge between nodes <code>a</code> and <code>b</code>.
    * @return The data associated with the edge.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    * @see AbstractGraph#edge(AbstractGraphNode, AbstractGraphNode)
    */
   @Override
   public EdgeData edge(ListGraphNode<Id, NodeData, EdgeData> a, ListGraphNode<Id, NodeData, EdgeData> b) throws IllegalArgumentException {
      if(a == null || b == null || !a.hasEdge(b))
         throw new IllegalArgumentException("Edge " + a + "," + b + " does not exist");
      return a.edge(b);
   }

   /**
    * Adds an edge from node <code>a</code> to <code>b</code>.
    * @param ed The data that will be associated with the edge.
    * @return <code>true</code> if the edge was successfully added, <code>false</code> otherwise.
    * @see AbstractGraph#addEdge(AbstractGraphNode, AbstractGraphNode, java.lang.Object)
    * O(1) if the edge already existed, O(e) otherwise.
    */
   @Override
   public boolean addEdge(ListGraphNode<Id, NodeData, EdgeData> a, ListGraphNode<Id, NodeData, EdgeData> b, EdgeData ed) {
      if(a != null && b != null && !a.hasEdge(b)) {
         a.outEdges.put(b, ed);
         b.inEdges.add(a);
         edgeCount++;
         if(a.outDegree() > maxOutDegree())
            maxOutDegree = a.outDegree();
         if(b.inDegree() > maxInDegree())
            maxInDegree = b.inDegree();
         return true;
      }
      return false;
   }

   /**
    * Sets the data associated with the edge between nodes <code>a</code> and <code>b</code>, and returns the old data.
    * @return The old data that was associated with the edge.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    * @see AbstractGraph#replaceEdge(AbstractGraphNode, AbstractGraphNode, java.lang.Object)
    */
   @Override
   public EdgeData replaceEdge(ListGraphNode<Id, NodeData, EdgeData> a, ListGraphNode<Id, NodeData, EdgeData> b, EdgeData ed) throws IllegalArgumentException {
      if(a == null || b == null || !a.hasEdge(b))
         throw new IllegalArgumentException("Edge " + a + "," + b + " does not exist");
      return a.replaceEdge(b, ed);
   }

   /**
    * Removes the edge between nodes a and b.
    * @return The data that was associated with the edge.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    * @see AbstractGraph#removeEdge(AbstractGraphNode, AbstractGraphNode)
    * O(1).
    */
   @Override
   public EdgeData removeEdge(ListGraphNode<Id, NodeData, EdgeData> a, ListGraphNode<Id, NodeData, EdgeData> b) throws IllegalArgumentException {
      if(a == null || b == null || !a.hasEdge(b))
         throw new IllegalArgumentException("Edge " + a + "," + b + " does not exist");
      edgeCount--;
      if(maxInDegree <= b.inDegree())
         maxInDegree = -1;
      if(maxOutDegree <= a.outDegree())
         maxOutDegree = -1;
      b.inEdges.remove(a);
      return a.outEdges.remove(b);
   }

   @Override
   public String toString() {
      return "dvrlib.graph.ListGraph(" + nodeCount() + ")[" + edgeCount() + "]";
   }
}
