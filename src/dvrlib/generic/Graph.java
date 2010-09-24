/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Graph.java
 */

package dvrlib.generic;

public class Graph {
   public final int vertexCount;
   protected final int firstEdges[], lastEdges[], degrees[];
   protected final Edge edges[][];
   protected int edgeCount, firstEdge, lastEdge, maxDegree;

   /**
    * Graph constructor.
    * @param vertexCount The number of vertices in this graph.
    * O(1).
    */
   public Graph(final int vertexCount) {
      this.vertexCount =    vertexCount;
      firstEdges = new  int[vertexCount];
      lastEdges  = new  int[vertexCount];
      degrees    = new  int[vertexCount];
      edges      = new Edge[vertexCount][vertexCount];
      edgeCount  = 0;
      firstEdge  = vertexCount;
      lastEdge   = -1;
      maxDegree  = 0;
   }

   /**
    * Returns the number of edges in this graph.
    * O(1).
    */
   public int getEdgeCount() {
      return edgeCount;
   }

   /**
    * Returns true if there is an edge from vertex a to any other vertex, false otherwise.
    * O(1).
    */
   public boolean hasEdge(final int a) {
      return (getFirstEdge(a) != null);
   }

   /**
    * Returns true if there is an edge between vertices a and b, false otherwise.
    * O(1).
    */
   public boolean hasEdge(final int a, final int b) {
      return (getEdge(a, b) != null);
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
    * Returns the first edge from vertex a to any other vertex, or null if there is none.
    * O(1).
    */
   public Edge getFirstEdge(final int a) {
      return getEdge(a, firstEdges[a]);
   }

   /**
    * Returns the last edge from vertex a to any other vertex, or null if there is none.
    * O(1).
    */
   public Edge getLastEdge(final int a) {
      return getEdge(a, lastEdges[a]);
   }

   /**
    * Returns the edge between vertices a and b, or null if there is no such edge.
    * O(1).
    */
   public Edge getEdge(final int a, final int b) {
      // Sanity check
      if(a < 0 || a >= vertexCount || b < 0 || b >= vertexCount)
         return null;
      return edges[a][b];
   }

   /**
    * Adds an edge between vertices a and b.
    * @return true if the edge was added, false otherwise.
    * O(e).
    */
   public boolean addEdge(final int a, final int b) {
      // Sanity check
      if(a < 0 || a >= vertexCount || b < 0 || b >= vertexCount || hasEdge(a, b))
         return false;

      // Create new edge
      Edge e = new Edge(a, b);
      edges[a][b] = e;
      edgeCount++;
      incDegree(a);

      // Find the previous and next edges
      Edge p = getFirstEdge(a), n = getLastEdge(a);
      if(a < firstEdge) {
         // The new edge comes before the first edge in the graph
         // p == null, since there is no edge before it
         firstEdge = a;
         lastEdges[a] = b;
      }
      else {
         // The new edge comes after the first edge in the graph
         if(p == null) {
            // The new edge is the first one connected to vertex a
            firstEdges[a] = b;
            for(int i = a - 1; i >= 0 && p == null; i--) {
               p = getLastEdge(i);
            }
         }
         else if(b > p.b) {
            // The new edge comes after the first edge of vertex a
            while(p.next != null && a == p.a && b < p.b) {
               p = p.next;
            }
         }
         else {
            // The new edge comes before the first edge of vertex a
            p = p.previous;
         }
      }

      if(a > lastEdge) {
         // The new edge comes after the last edge in the graph
         // n == null, since there is no edge after it
         lastEdge = a;
         firstEdges[a] = b;
      }
      else {
         // The new edge comes before the last edge in the graph
         if(n == null) {
            // The new edge is the first one connected to vertex a
            lastEdges[a] = b;
            for(int i = a + 1; i < vertexCount && n == null; i++) {
               n = getFirstEdge(i);
            }
         }
         else if(b < n.b) {
            // The new edge comes before the last edge of vertex a
            while(n.previous != null && a == n.a && b > n.b) {
               n = n.previous;
            }
         }
         else {
            // The new edge comes after the last edge of vertex a
            n = n.next;
         }
      }
      
      // Set previous and next pointers
      e.previous = p;
      if(p == null || a != p.a || b < p.b)
         firstEdges[a] = b;
      if(p != null)
         p.next = e;

      e.next = n;
      if(n == null || a != n.a || b > n.b)
         lastEdges[a] = b;
      if(n != null)
         n.previous = e;

      return true;
   }

   /**
    * Removes the edge between vertices a and b.
    * O(1).
    */
   public void removeEdge(final int a, final int b) {
      // Sanity check
      if(a < 0 || a >= vertexCount || b < 0 || b >= vertexCount || !hasEdge(a, b))
         return;

      // Maintain data
      Edge e = getEdge(a, b);

      if(a == firstEdge) {
         // The old edge was the first edge in the graph
         if(e.next == null)
            firstEdge = vertexCount;
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

      if(firstEdges[a] == b) {
         if(e.next != null && e.next.a == a)
            firstEdges[a] = e.next.b;
         else
            firstEdges[a] = -1;
      }
      if(lastEdges[a] == b) {
         if(e.previous != null && e.previous.a == a)
            lastEdges[a] = e.previous.b;
         else
            lastEdges[a] = -1;
      }

      // Delete edge
      edges[a][b] = null;
      edgeCount--;
      if(degrees[a] == maxDegree) {
         // Invalidate maxDegree
         maxDegree = -1;
      }
      degrees[a]--;
   }

   /**
    * Returns the number of edges starting in vertex a.
    * O(1).
    */
   public int getDegree(final int a) {
      return degrees[a];
   }

   /**
    * Returns the largest degree.
    * O(v) if maxDegree was invalidated (by a call to removeEdge), O(1) otherwise.
    */
   public int getMaxDegree() {
      // If maxDegree has been invalidated, recalculate it
      if(maxDegree < 0) {
         maxDegree = 0;
         for(int i = 0; i < vertexCount; i++) {
            maxDegree = Math.max(maxDegree, degrees[i]);
         }
      }
      return maxDegree;
   }

   /**
    * Increases the degree of vertex a.
    */
   protected void incDegree(int a) {
      if(++degrees[a] > getMaxDegree());
         maxDegree = degrees[a];
   }

   /**
    * Prints this graph.
    */
   public void print() {
      for(int i = 0; i < vertexCount; i++) {
         System.out.print("\t[");
         for(int j = 0; j < vertexCount; j++) {
            System.out.print(printEdge(j, i) + " ");
         }
         System.out.println("] :d " + getDegree(i));
      }
   }
   
   protected String printEdge(int a, int b) {
      //return (hasEdge(a, b) ? "1" : "0");
      //*
      Edge e = getEdge(a, b); // Debug output
      return (e == null ? "0       " : "p" + (e.previous == null ? "-  " : e.previous.a + "," + e.previous.b) + "n" + (e.next == null ? "-  " : e.next.a + "," + e.next.b));
      //*/
   }
}
