/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Graph.java
 */

package dvrlib.generic;

public class Graph {
   protected final int vertexCount, firsts[], degrees[];
   protected final Edge edges[][];
   protected int edgeCount, maxDegree;

   /**
    * Graph constructor.
    * @param vertexCount The number of vertices in this graph.
    * O(1).
    */
   public Graph(final int vertexCount) {
      this.vertexCount =   vertexCount;
      firsts    = new  int[vertexCount];
      degrees   = new  int[vertexCount];
      edges     = new Edge[vertexCount][vertexCount];
      edgeCount = 0;
      maxDegree = 0;
   }

   /**
    * Returns the number of vertices in this graph.
    * O(1).
    */
   public int getVertexCount() {
      return vertexCount;
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
      return (getEdge(a) != null);
   }

   /**
    * Returns true if there is an edge between vertices a and b, false otherwise.
    * O(1).
    */
   public boolean hasEdge(final int a, final int b) {
      return (getEdge(a, b) != null);
   }

   /**
    * Returns the first edge in this graph, or null if there are none.
    * O(v).
    */
   public Edge getFirstEdge() {
      Edge e = null;
      for(int i = 0; i < vertexCount && e == null; i++) {
         e = getEdge(i);
      }
      return e;
   }

   /**
    * Returns an edge from vertex a to any other vertex, or null if there is no such edge.
    * O(1).
    */
   public Edge getEdge(final int a) {
      // Sanity check
      if(a < 0 || a >= vertexCount)
         return null;
      return edges[a][firsts[a]];
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
      degrees[a]++;
      incMaxDegree();

      // Maintain data
      Edge n = getEdge(a);
      if(n == null) {
         // The new edge is the only one at vertex a
         firsts[a] = b;
         // Find next edge at higher vertices
         for(int i = a + 1; i < vertexCount && n == null; i++) {
            n = getEdge(i);
         }
      }
      else {
         // The next edge is at the same vertex
         if(b < firsts[a])
            firsts[a] = b;
         // Find next edge at this vertex
         while(a == n.a && n.next != null && b > n.next.b) {
            n = n.next;
         }
      }

      // Set next pointer
      e.next = n;

      if(n == null) {
         // There is no next edge
         // Find previous edge at lower vertices
         Edge p = getEdge(a - 1);
         for(int i = a - 2; i >= 0 && p == null; i--) {
            p = getEdge(i);
         }

         e.previous = p;
         if(p != null) {
            // There is a previous edge
            p.next = e;
         }
      }
      else {
         // There is a next edge
         e.previous = n.previous;
         if(n.previous != null) {
            // There is a previous edge
            e.previous.next = e;
         }
         n.previous = e;
      }

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
      if(e.previous != null)
         e.previous.next = e.next;
      if(e.next != null)
         e.next.previous = e.previous;
      if(firsts[a] == b) {
         if(e.next != null && e.next.a == a)
            firsts[a] = e.next.b;
         else
            firsts[a] = 0;
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
    * Increases the value of maxDegree.
    * @see Graph#getMaxDegree()
    */
   protected void incMaxDegree() {
      maxDegree = getMaxDegree() + 1;
   }

   /**
    * Prints this graph.
    */
   public void print() {
      for(int i = 0; i < vertexCount; i++) {
         System.out.print("    [");
         for(int j = 0; j < vertexCount; j++) {
            System.out.print(printEdge(i, j) + " ");
         }
         System.out.println("] : " + getDegree(i));
      }
   }
   
   protected String printEdge(int a, int b) {
      return (hasEdge(a, b) ? "1" : "0");
   }
}
