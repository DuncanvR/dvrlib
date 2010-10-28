/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Graph.java
 */

package dvrlib.generic.graph;

import java.util.Collection;

public class Graph<E extends Edge> extends AbstractGraph<Node, E> implements EdgeCreator<E> {
   protected final Node<E> nodes[];
   protected final EdgeCreator<E> edgeCreator;
   protected int firstEdge, lastEdge;

   /**
    * Graph constructor, using itself as edgeCreator.
    * @param nodeCount The number of nodes in this graph.
    * @see Graph(int, dvrlib.generic.EdgeCreator)
    */
   public Graph(int nodeCount) {
      this(nodeCount, null);
   }

   /**
    * Graph constructor.
    * @param nodeCount   The number of nodes in this graph.
    * @param edgeCreator The class that will take care of the creation of edges.
    * O(1).
    */
   public Graph(int nodeCount, EdgeCreator<E> edgeCreator) {
      super(nodeCount);
      nodes = new Node[nodeCount];
      for(int i = 0; i < nodeCount; i++) {
         nodes[i] = new Node<E>(this, i);
      }
      this.edgeCreator = (edgeCreator == null ? this : edgeCreator);

      edgeCount  = 0;
      firstEdge  = nodeCount;
      lastEdge   = -1;
      maxDegree  = 0;
   }

   /**
    * Returns true if there is an edge from the given node to any other node, false otherwise.
    * O(1).
    */
   public boolean hasEdge(int a) {
      return (getFirstEdge(a) != null);
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
      Node<E> n = getNode(a);
      if(n == null)
         return null;
      return n.getEdge(n.first);
   }

   /**
    * Returns the last edge from node a to any other node, or null if there is none.
    * O(1).
    */
   public E getLastEdge(int a) {
      Node<E> n = getNode(a);
      if(n == null)
         return null;
      return n.getEdge(n.last);
   }

   /**
    * Returns the edge between nodes a and b, or null if there is no such edge.
    * O(1).
    */
   @Override
   public E getEdge(int a, int b) {
      // Sanity check
      if(a < 0 || a >= nodeCount || b < 0 || b >= nodeCount)
         return null;
      return getEdge(getNode(a), b);
   }

   /**
    * Returns the edge between nodes a and b, or null if there is no such edge.
    * O(1).
    */
   public E getEdge(Node<E> a, int b) {
      if(a == null)
         return null;
      return a.getEdge(b);
   }

   /**
    * Returns the node with the given index.
    * O(1).
    */
   @Override
   public Node<E> getNode(int index) {
      return nodes[index];
   }

   /**
    * Returns the neighbouring nodes of the node with the given index.
    * @see Node#getNeighbours()
    */
   @Override
   public Collection<AbstractNode> getNeighbours(int index) {
      return nodes[index].getNeighbours();
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
    * Adds and returns an edge between nodes a and b.
    * O(1) if the edge already existed, O(e) otherwise.
    */
   @Override
   public E addEdge(int a, int b) {
      // Sanity check
      if(a < 0 || a >= nodeCount || b < 0 || b >= nodeCount)
         return null;

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
      return e;
   }

   /**
    * Removes the edge between nodes a and b.
    * O(1).
    */
   @Override
   public E removeEdge(int a, int b) {
      // Sanity check
      if(a < 0 || a >= nodeCount || b < 0 || b >= nodeCount || !hasEdge(a, b))
         return null;

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

      return e;
   }

   /**
    * Returns the number of edges starting in node a.
    * O(1).
    */
   public int getDegree(int a) {
      return nodes[a].getDegree();
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

   /*
   @Override
   protected String printEdge(int a, int b) {
      Edge e = getEdge(a, b); // Debug output
      return (e == null ? "0       " : "p" + (e.previous == null ? "-  " : e.previous.a + "," + e.previous.b) + "n" + (e.next == null ? "-  " : e.next.a + "," + e.next.b));
   }
   //*/
}
