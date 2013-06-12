/*
 * DvRlib - Graph
 * Duncan van Roermund, 2010-2013
 * AbstractGraph.java
 */

package dvrlib.graph;

import dvrlib.generic.Triple;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractGraph<Id extends Comparable<Id>, Node extends AbstractGraphNode<Id, Node, NodeData, EdgeData>, NodeData, EdgeData> {
   protected int maxInDegree = 0, maxOutDegree = 0;

   /**
    * Returns the number of nodes in this graph.
    */
   public abstract int nodeCount();

   /**
    * Returns the number of edges in this graph.
    */
   public abstract int edgeCount();

   /**
    * Returns the largest in-degree.
    * @see AbstractGraph#inDegree(int)
    * @see AbstractGraph#calcMaxInDegree()
    * O(calcMaxInDegree()) if maxInDegree was invalidated, O(1) otherwise.
    */
   public int maxInDegree() {
      if(maxInDegree < 0)
         calcMaxInDegree();
      return maxInDegree;
   }

   /**
    * Returns the largest out-degree.
    * @see AbstractGraph#outDegree(int)
    * @see AbstractGraph#calcMaxOutDegree()
    * O(calcMaxOutDegree()) if maxOutDegree was invalidated, O(1) otherwise.
    */
   public int maxOutDegree() {
      if(maxOutDegree < 0)
         calcMaxOutDegree();
      return maxOutDegree;
   }

   /**
    * Calculates the maximum in-degree.
    */
   protected abstract void calcMaxInDegree();

   /**
    * Calculates the maximum out-degree.
    */
   protected abstract void calcMaxOutDegree();

   /**
    * Adds a new node to this graph.
    * @param id The identifier of the new node.
    * @param nd The data that will be associated with the node.
    * @return The newly added node.
    */
   public abstract Node add(Id id, NodeData nd);

   /**
    * Adds a new node to this graph, using <code>null</code> for the associated data.
    * @param id The identifier of the new node.
    * @return The newly added node.
    * @see AbstractGraph#add(java.lang.Object, java.lang.Object)
    */
   public Node add(Id id) {
      return add(id, null);
   }

   /**
    * Indicates whether this graph contains a node with the given identifier.
    * @param id The identifier to search for.
    * @return <code>true</code> if such a node exists, <code>false</code> otherwise.
    */
   public abstract boolean contains(Id id);

   /**
    * Returns the identified node.
    */
   public abstract Node node(Id id);

   /**
    * Removes the given node from this graph and returns its associated data.
    * @param id The identifier of the node that is to be removed.
    * @return The old data that was associated with the node.
    */
   public NodeData remove(Id id) {
      return remove(node(id));
   }
   public abstract NodeData remove(Node node);

   /**
    * Clears all nodes and edges from this graph.
    */
   public abstract void clear();

   /**
    * Merges the nodes <code>a</code> and <code>b</code> into one.
    */
   public void merge(Id a, Id b) {
      merge(node(a), node(b));
   }
   public abstract void merge(Node a, Node b);

   /**
    * Merges the data associated with two nodes that are being merged.
    * @return <code>null</code> by default, override to make meaningful.
    * @see AbstractGraph#mergeNodes(int, int)
    */
   protected NodeData mergeNodeData(NodeData a, NodeData b) {
      return null;
   }

   /**
    * Merges the data associated with two edges that are being merged.
    * @return <code>null</code> by default, override to make meaningful.
    * @see AbstractGraph#mergeNodes(int, int)
    */
   protected EdgeData mergeEdgeData(EdgeData a, EdgeData b) {
      return null;
   }

   /**
    * Returns an iterator to the nodes of this graph.
    */
   public abstract Iterator<Node> nodeIterator();

   /**
    * Returns an iterator to the data of the nodes of this graph.
    * @see AbstractGraph#nodesIterator()
    */
   public abstract Iterator<NodeData> nodeDataIterator();

   /**
    * Returns an iterator to all the outgoing edges of this graph.
    */
   public Iterator<Triple<Node, EdgeData, Node>> outEdgesIterator() {
      return new Iterator<Triple<Node, EdgeData, Node>>() {
            protected final Iterator<Node> nodeIt = nodeIterator();
            protected       Iterator<Triple<Node, EdgeData, Node>> edgeIt = null;

            @Override
            public boolean hasNext() {
               while(edgeIt == null || !edgeIt.hasNext()) {
                  if(!nodeIt.hasNext())
                     return false;
                  edgeIt = nodeIt.next().outEdgesIterator();
               }
               return true;
            }
            @Override
            public Triple<Node, EdgeData, Node> next() {
               if(!hasNext())
                  throw new NoSuchElementException();
               return edgeIt.next();
            }
            @Override
            public void remove() {
               throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
            }
         };
   }

   /**
    * Returns an iterator to the outgoing edges of the given node.
    */
   public Iterator<Triple<Node, EdgeData, Node>> outEdgesIterator(Id id) {
      return node(id).outEdgesIterator();
   }

   /**
    * Returns an iterator to all the incoming edges of this graph.
    */
   public Iterator<Triple<Node, EdgeData, Node>> inEdgesIterator() {
      return new Iterator<Triple<Node, EdgeData, Node>>() {
            protected final Iterator<Node> nodeIt = nodeIterator();
            protected       Iterator<Triple<Node, EdgeData, Node>> edgeIt = null;

            @Override
            public boolean hasNext() {
               while(edgeIt == null || !edgeIt.hasNext()) {
                  if(!nodeIt.hasNext())
                     return false;
                  edgeIt = nodeIt.next().inEdgesIterator();
               }
               return true;
            }
            @Override
            public Triple<Node, EdgeData, Node> next() {
               if(!hasNext())
                  throw new NoSuchElementException();
               return edgeIt.next();
            }
            @Override
            public void remove() {
               throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
            }
            };
   }

   /**
    * Returns an iterator to the incoming edges of the given node.
    */
   public Iterator<Triple<Node, EdgeData, Node>> inEdgesIterator(Id id) {
      return node(id).inEdgesIterator();
   }

   /**
    * Returns true if there is an edge between nodes <code>a</code> and <code>b</code>, false otherwise.
    * @return <code>true</code> if the edge exists, <code>false</code> otherwise.
    */
   public boolean hasEdge(Id a, Id b) {
      return hasEdge(node(a), node(b));
   }
   public abstract boolean hasEdge(Node a, Node b);

   /**
    * Returns the data associated with the edge between nodes <code>a</code> and <code>b</code>.
    * @return The data associated with the edge.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   public EdgeData edge(Id a, Id b) throws IllegalArgumentException {
      return edge(node(a), node(b));
   }
   public abstract EdgeData edge(Node a, Node b) throws IllegalArgumentException;

   /**
    * Adds an edge from node <code>a</code> to <code>b</code>.
    * @param ed The data that will be associated with the edge.
    * @return <code>true</code> if the edge was successfully added, <code>false</code> otherwise.
    */
   public boolean addEdge(Id a, Id b, EdgeData ed) {
      return addEdge(node(a), node(b), ed);
   }
   public abstract boolean addEdge(Node a, Node b, EdgeData ed);

   /**
    * Adds an edge from node <code>a</code> to <code>b</code>, using <code>null</code> for the associated data.
    * @return <code>true</code> if the edge was added, <code>false</code> otherwise.
    * @see AbstractGraph#addEdge(AbstractGraphNode, AbstractGraphNode, java.lang.Object)
    */
   public boolean addEdge(Id a, Id b) {
      return addEdge(node(a), node(b));
   }
   protected boolean addEdge(Node a, Node b) {
      return addEdge(a, b, null);
   }

   /**
    * Sets the data associated with the edge between nodes a and b, and returns the old data.
    * @return The old data that was associated with the edge.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   public EdgeData replaceEdge(Id a, Id b, EdgeData ed) throws IllegalArgumentException {
      return replaceEdge(node(a), node(b), ed);
   }
   public abstract EdgeData replaceEdge(Node a, Node b, EdgeData ed) throws IllegalArgumentException;

   /**
    * Removes the edge between nodes a and b.
    * @return The data that was associated with the edge.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    */
   public EdgeData removeEdge(Id a, Id b) throws IllegalArgumentException {
      return removeEdge(node(a), node(b));
   }
   public abstract EdgeData removeEdge(Node a, Node b) throws IllegalArgumentException;

   @Override
   public String toString() {
      return "dvrlib.graph.AbstractGraph(" + nodeCount() + ")[" + edgeCount() + "]";
   }
}
