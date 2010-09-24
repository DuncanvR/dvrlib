/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Graph.java
 */

package dvrlib.generic;

public class Graph extends AbstractGraph {
   protected final Node nodes[];
   protected int firstEdge, lastEdge;

   /**
    * Graph constructor.
    * @param nodeCount The number of vertices in this graph.
    * O(1).
    */
   public Graph(int nodeCount) {
      super(nodeCount);
      nodes = new Node[nodeCount];
      for(int i = 0; i < nodeCount; i++) {
         nodes[i] = new Node(this, i);
      }

      edgeCount  = 0;
      firstEdge  = nodeCount;
      lastEdge   = -1;
      maxDegree  = 0;
   }

   /**
    * Returns true if there is an edge from node a to any other node, false otherwise.
    * O(1).
    */
   public boolean hasEdge(int a) {
      return (getFirstEdge(a) != null);
   }

   /**
    * Returns the first edge in this graph, or null if there is none.
    * O(1).
    */
   public Edge getFirstEdge() {
      return getFirstEdge(firstEdge);
   }

   /**
    * Returns the last edge in this graph, or null if there is none.
    * O(1).
    */
   public Edge getLastEdge() {
      return getFirstEdge(lastEdge);
   }

   /**
    * Returns the first edge from node a to any other node, or null if there is none.
    * O(1).
    */
   public Edge getFirstEdge(int a) {
      Node n = getNode(a);
      if(n == null)
         return null;
      return getEdge(n, n.first);
   }

   /**
    * Returns the last edge from node a to any other node, or null if there is none.
    * O(1).
    */
   public Edge getLastEdge(int a) {
      Node n = getNode(a);
      if(n == null)
         return null;
      return getEdge(n, n.last);
   }

   /**
    * Returns the edge between nodes a and b, or null if there is no such edge.
    * O(1).
    */
   public Edge getEdge(int a, int b) {
      // Sanity check
      if(a < 0 || a >= nodeCount || b < 0 || b >= nodeCount)
         return null;
      return getEdge(getNode(a), b);
   }

   /**
    * Returns the edge between nodes a and b, or null if there is no such edge.
    * O(1).
    */
   public Edge getEdge(Node a, int b) {
      if(a == null)
         return null;
      return a.getEdge(b);
   }

   /**
    * Returns the node with the given index.
    * O(1).
    */
   public Node getNode(int index) {
      return nodes[index];
   }

   /**
    * Adds an edge between vertices a and b.
    * @return true if the edge was added, false otherwise.
    * O(e).
    */
   public Edge addEdge(int a, int b) {
      // Sanity check
      if(a < 0 || a >= nodeCount || b < 0 || b >= nodeCount)
         return null;

      Edge e = getEdge(a, b);
      if(e == null) {
         // Create new edge
         e = new Edge(a, b);

         // Find the previous and next edges
         Edge p = getFirstEdge(a), n = getLastEdge(a);
         if(a < firstEdge) {
            // The new edge comes before the first edge in the graph
            // p == null, since there is no edge before it
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
            // n == null, since there is no edge after it
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
         nodes[a].edges[b] = e;
         edgeCount++;
         incDegree(a);
      }
      return e;
   }

   /**
    * Removes the edge between vertices a and b.
    * O(1).
    */
   public Edge removeEdge(int a, int b) {
      // Sanity check
      if(a < 0 || a >= nodeCount || b < 0 || b >= nodeCount || !hasEdge(a, b))
         return null;

      // Maintain data
      Edge e = getEdge(a, b);

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
            nodes[a].first = -1;
      }
      if(nodes[a].last == b) {
         if(e.previous != null && e.previous.a == a)
            nodes[a].last = e.previous.b;
         else
            nodes[a].last = -1;
      }

      if(nodes[a].getDegree() == maxDegree) {
         // Invalidate maxDegree
         maxDegree = -1;
      }
      nodes[a].degree--;

      // Delete edge
      nodes[a].edges[b] = null;
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
    * Returns the largest degree.
    * O(v) if maxDegree was invalidated (by a call to removeEdge), O(1) otherwise.
    */
   @Override
   public int getMaxDegree() {
      // If maxDegree has been invalidated, recalculate it
      if(maxDegree < 0) {
         maxDegree = 0;
         for(int i = 0; i < nodeCount; i++) {
            maxDegree = Math.max(maxDegree, nodes[i].getDegree());
         }
      }
      return maxDegree;
   }

/*   @Override
   protected String printEdge(int a, int b) {
      Edge e = getEdge(a, b); // Debug output
      return (e == null ? "0       " : "p" + (e.previous == null ? "-  " : e.previous.a + "," + e.previous.b) + "n" + (e.next == null ? "-  " : e.next.a + "," + e.next.b));
   }*/
}
