/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010
 * MatrixGraph.java
 */

package dvrlib.graph;

import java.util.Iterator;

/**
 * Graph class, using fixed sized arrays to keep track of edges.
 * This makes it fast at inserting and checking for edges, but slow at retreiving neighbouring nodes.
 */
public class MatrixGraph<E extends Edge> extends AbstractGraph<MatrixGraphNode> implements EdgeCreator<E> {
   protected final MatrixGraphNode<E> nodes[];
   protected final EdgeCreator<E> edgeCreator;
   protected int firstEdge, lastEdge;

   /**
    * MatrixGraph constructor, using itself as edgeCreator.
    * @param nodeCount The number of nodes in this graph.
    * @see MatrixGraph(int, dvrlib.generic.EdgeCreator)
    */
   public MatrixGraph(int nodeCount) {
      this(nodeCount, null);
   }

   /**
    * MatrixGraph constructor.
    * @param nodeCount   The number of nodes in this graph.
    * @param edgeCreator The class that will take care of the creation of edges.
    * O(1).
    */
   public MatrixGraph(int nodeCount, EdgeCreator<E> edgeCreator) {
      super(nodeCount);
      nodes = new MatrixGraphNode[nodeCount];
      for(int i = 0; i < nodeCount; i++) {
         nodes[i] = new MatrixGraphNode<E>(this, i);
      }
      this.edgeCreator = (edgeCreator == null ? this : edgeCreator);

      edgeCount = 0;
      firstEdge = nodeCount;
      lastEdge  = -1;
      maxDegree = 0;
   }

   /**
    * Returns true if there is an edge between nodes a and b, false otherwise.
    * O(1).
    */
   @Override
   public boolean hasEdge(int a, int b) {
      return (getEdge(a, b) != null);
   }

    /**
    * Returns the first edge in this graph, or null if there is none.
    * O(1).
    */
   public E getFirstEdge() {
      return getFirstEdge(firstEdge);
   }

   /**
    * Returns the last edge in this graph, or null if there is none.
    * O(1).
    */
   public E getLastEdge() {
      return getLastEdge(lastEdge);
   }

   /**
    * Returns the first edge from node a to any other node, or null if there is none.
    * O(1).
    */
   public E getFirstEdge(int a) {
      MatrixGraphNode<E> n = getNode(a);
      if(n == null)
         return null;
      return n.getEdge(n.first);
   }

   /**
    * Returns the last edge from node a to any other node, or null if there is none.
    * O(1).
    */
   public E getLastEdge(int a) {
      MatrixGraphNode<E> n = getNode(a);
      if(n == null)
         return null;
      return n.getEdge(n.last);
   }

   /**
    * Returns the edge between nodes a and b, or null if there is no such edge.
    * O(1).
    */
   public E getEdge(int a, int b) {
      // Sanity check
      if(!checkIndex(a) || !checkIndex(b))
         return null;
      return getEdge(getNode(a), b);
   }

   /**
    * Returns the edge between nodes a and b, or null if there is no such edge.
    * O(1).
    */
   public E getEdge(MatrixGraphNode<E> a, int b) {
      if(a == null)
         return null;
      return a.getEdge(b);
   }

   /**
    * Returns the node with the given index.
    * O(1).
    */
   @Override
   public MatrixGraphNode<E> getNode(int index) {
      return nodes[index];
   }

   /**
    * Creates and returns a new edge between the nodes with the given indices.
    * @see dvrlib.generic.EdgeCreator#newEdge(int, int)
    * O(1).
    */
   @Override
   public E newEdge(int a, int b) {
      return (E) new Edge(a, b);
   }

   /**
    * Adds an edge between nodes a and b.
    * @return true if an edge was added, false otherwise.
    * O(1) if the edge already existed, O(e) otherwise.
    */
   @Override
   public boolean addEdge(int a, int b) {
      // Sanity check
      if(!checkIndex(a) || !checkIndex(b))
         return false;

      E e = getEdge(a, b);
      if(e == null) {
         // Create new edge
         e = edgeCreator.newEdge(a, b);

         // Find the previous and next edges
         Edge p = getFirstEdge(a), n = getLastEdge(a);
         if(a < firstEdge) {
            // The new edge comes before the first edge in the graph
            // p == null, since there are no edges at this node
            firstEdge = a;
            nodes[a].last = b;
         }
         else {
            // The new edge comes after the first edge in the graph
            if(p == null) {
               // The new edge is the first one connected to node a
               for(int i = a - 1; p == null && i >= 0; i--) {
                  p = getLastEdge(i);
               }
            }
            else if(b > p.b) {
               // The new edge comes after the first edge of node a
               while(p.next != null && a == p.next.a && b > p.next.b) {
                  p = p.next;
               }
            }
            else {
               // The new edge comes before the first edge of node a
               p = p.previous;
            }
         }

         if(a > lastEdge) {
            // The new edge comes after the last edge in the graph
            // n == null, since there are no edges at this node
            lastEdge = a;
            nodes[a].first = b;
         }
         else {
            // The new edge comes before the last edge in the graph
            if(n == null) {
               // The new edge is the first one connected to node a
               for(int i = a + 1; n == null && i < nodeCount; i++) {
                  n = getFirstEdge(i);
               }
            }
            else if(b < n.b) {
               // The new edge comes before the last edge of node a
               while(n.previous != null && a == n.previous.a && b < n.previous.b) {
                  n = n.previous;
               }
            }
            else {
               // The new edge comes after the last edge of node a
               n = n.next;
            }
         }

         // Set previous and next pointers
         e.previous = p;
         if(p == null || a != p.a || b < p.b)
            nodes[a].first = b;
         if(p != null)
            p.next = e;

         e.next = n;
         if(n == null || a != n.a || b > n.b)
            nodes[a].last = b;
         if(n != null)
            n.previous = e;

         // Add edge
         nodes[a].edges.set(b, e);
         edgeCount++;
         incDegree(a);
      }
      return true;
   }

   /**
    * Removes the edge between nodes a and b.
    * @return true if the edge was deleted, false otherwise.
    * O(1).
    */
   @Override
   public boolean removeEdge(int a, int b) {
      // Sanity check
      if(!checkIndex(a) || !checkIndex(b) || !hasEdge(a, b))
         return false;

      // Maintain data
      E e = getEdge(a, b);

      if(a == firstEdge) {
         // The old edge was the first edge in the graph
         if(e.next == null)
            firstEdge = nodeCount;
         else
            firstEdge = e.next.a;
      }
      if(a == lastEdge) {
         // The old edge was the last edge in the graph
         if(e.previous == null)
            lastEdge = -1;
         else
            lastEdge = e.previous.a;
      }

      // Update previous and next pointers
      if(e.previous != null)
         e.previous.next = e.next;
      if(e.next != null)
         e.next.previous = e.previous;

      if(nodes[a].first == b) {
         if(e.next != null && e.next.a == a)
            nodes[a].first = e.next.b;
         else
            nodes[a].first = nodeCount;
      }
      if(nodes[a].last == b) {
         if(e.previous != null && e.previous.a == a)
            nodes[a].last = e.previous.b;
         else
            nodes[a].last = -1;
      }

      // Delete edge
      nodes[a].edges.set(b, null);
      decDegree(a);
      edgeCount--;

      return true;
   }

   /**
    * Returns the number of edges starting in node a.
    * O(1).
    */
   @Override
   public int getDegree(int a) {
      return nodes[a].getDegree();
   }

   /**
    * Increases the degree of node a.
    * @see AbstractGraph#incDegree(dvrlib.generic.AbstractNode)
    * O(1).
    */
   protected void incDegree(int a) {
      incDegree(getNode(a));
   }

   /**
    * Increases the degree of the given node.
    * O(getMaxDegree()).
    */
   protected void incDegree(MatrixGraphNode node) {
      if(node != null) {
         if(++node.degree > getMaxDegree())
            maxDegree = node.degree;
      }
   }

   /**
    * Decreases the degree of node a.
    * @see AbstractGraph#decDegree(dvrlib.generic.AbstractNode)
    * O(1).
    */
   protected void decDegree(int a) {
      decDegree(getNode(a));
   }

   /**
    * Decreases the degree of the given node.
    * If the degree of the given node was equal to maxDegree, maxDegree will be invalidated.
    * O(1).
    */
   protected void decDegree(MatrixGraphNode node) {
      if(node != null)
         if(node.degree-- == maxDegree)
            maxDegree = -1;
   }

   /**
    * Calculates the maximum degree.
    * O(n).
    */
   @Override
   protected void calcMaxDegree() {
      maxDegree = 0;
      for(int i = 0; i < nodeCount; i++) {
         maxDegree = Math.max(maxDegree, nodes[i].getDegree());
      }
   }

   @Override
   public Iterator<MatrixGraphNode> iterator() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   /*
   @Override
   protected String printEdge(int a, int b) {
      Edge e = getEdge(a, b); // Debug output
      return (e == null ? "0       " : "p" + (e.previous == null ? "-  " : e.previous.a + "," + e.previous.b) + "n" + (e.next == null ? "-  " : e.next.a + "," + e.next.b));
   }
   //*/
}
