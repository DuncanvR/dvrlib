/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * GraphTest.java
 */

package dvrlib.generic;

import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {
   Graph instance;

   public Graph newInstance(int nodeCount) {
      return new Graph(nodeCount);
   }

   @Test
   public void testGetEdgeCount() {
      instance = newInstance(3);
      assertEquals(0, instance.getEdgeCount());
      instance.addEdge(0, 1);
      assertEquals(1, instance.getEdgeCount());
      instance.addEdge(2, 1);
      assertEquals(2, instance.getEdgeCount());
      instance.removeEdge(2, 0);
      assertEquals(2, instance.getEdgeCount());
      instance.removeEdge(2, 1);
      assertEquals(1, instance.getEdgeCount());
      instance.removeEdge(0, 1);
      assertEquals(0, instance.getEdgeCount());
   }

   @Test
   public void testHasEdge() {
      int nodeCount = 10;
      instance = new Graph(nodeCount);
      for(int i = 1; i < nodeCount; i += 2) {
         for(int j = 0; j < i; j += 2) {
            instance.addEdge(i, j);
         }
      }

      // Test hasEdge(int)
      for(int i = 0; i < nodeCount; i++) {
         assertEquals((i % 2 == 1), instance.hasEdge(i));
      }

      // Test hasEdge(int, int)
      for(int i = 0; i < nodeCount; i++) {
         for(int j = 0; j < nodeCount; j++) {
            assertEquals((i % 2 == 1 && j % 2 == 0 && j < i), instance.hasEdge(i, j));
         }
      }

      // Test getFirstEdge(int) and getLastEdge(int)
      for(int i = 0; i < nodeCount; i++) {
         Edge f = instance.getFirstEdge(i), l = instance.getLastEdge(i);
         if(i % 2 == 1) {
            assertNotNull(f);
            assertNotNull(l);
            assertEquals(0, f.b);
            assertEquals(i - 1, l.b);
         }
         else {
            assertNull(f);
            assertNull(l);
         }
      }
   }

   @Test
   public void testEdges() {
      instance = newInstance(6);
      Edge edges[] = new Edge[5];
      edges[0] = instance.addEdge(0, 1);
      edges[1] = instance.addEdge(2, 1);
      edges[2] = instance.addEdge(2, 3);
      edges[3] = instance.addEdge(5, 2);
      edges[4] = instance.addEdge(5, 4);
      assertEdges(edges);

      edges[4] = instance.addEdge(5, 4);
      edges[3] = instance.addEdge(5, 2);
      edges[2] = instance.addEdge(2, 3);
      edges[1] = instance.addEdge(2, 1);
      edges[0] = instance.addEdge(0, 1);
      assertEdges(edges);

      edges[3] = instance.addEdge(5, 2);
      edges[0] = instance.addEdge(0, 1);
      edges[2] = instance.addEdge(2, 3);
      edges[4] = instance.addEdge(5, 4);
      edges[1] = instance.addEdge(2, 1);
      assertEdges(edges);
   }

   public void assertEdges(Edge edges[]) {
      // Test getFirstEdge() and getLastEdge()
      assertEquals(edges[0], instance.getFirstEdge());
      assertEquals(edges[edges.length - 1], instance.getLastEdge());

      // Test getEdge(int, int)
      for(Edge e : edges) {
         assertEquals(e, instance.getEdge(e.a, e.b));
      }

      assertNeighbours(edges);

      // Test previous and next pointers
      assertNull(edges[0].previous);
      for(int i = 1; i < edges.length - 1; i++) {
         assertEquals(edges[i - 1], edges[i].previous);
         assertEquals(edges[i], edges[i - 1].next);
         assertEquals(edges[i], edges[i + 1].previous);
         assertEquals(edges[i + 1], edges[i].next);
      }
      assertNull(edges[edges.length - 1].next);

      // Test removeEdge(int, int)
      for(Edge e : edges) {
         assertEquals(e, instance.removeEdge(e.a, e.b));
      }
   }

   public void assertNeighbours(Edge edges[]) {
      // Test getNeighbours(int)
      for(int i = 0; i < instance.getNodeCount(); i++) {
         Collection<AbstractNode> neighbours = instance.getNeighbours(i);
         for(Edge e : edges) {
            if(i == e.a)
               assertTrue(neighbours.remove(instance.getNode(e.b)));
         }
         assertTrue(neighbours.isEmpty());
      }
   }

   @Test
   public void testDegrees() {
      instance = newInstance(6);
      assertDegrees(0, new int[]{0,0,0,0,0,0});
      instance.addEdge(0, 1);
      assertDegrees(1, new int[]{1,0,0,0,0,0});
      instance.addEdge(2, 1);
      assertDegrees(1, new int[]{1,0,1,0,0,0});
      instance.addEdge(2, 3);
      assertDegrees(2, new int[]{1,0,2,0,0,0});
      instance.addEdge(2, 5);
      assertDegrees(3, new int[]{1,0,3,0,0,0});
      instance.addEdge(4, 5);
      assertDegrees(3, new int[]{1,0,3,0,1,0});

      instance = newInstance(5);
      assertDegrees(0, new int[]{0,0,0,0,0});
      instance.addEdge(0, 1);
      assertDegrees(1, new int[]{1,0,0,0,0});
      instance.addEdge(2, 3);
      assertDegrees(1, new int[]{1,0,1,0,0});
      instance.addEdge(0, 4);
      assertDegrees(2, new int[]{2,0,1,0,0});
      instance.addEdge(2, 4);
      assertDegrees(2, new int[]{2,0,2,0,0});
      instance.addEdge(0, 3);
      assertDegrees(3, new int[]{3,0,2,0,0});
      instance.removeEdge(2, 4);
      assertDegrees(3, new int[]{3,0,1,0,0});
      instance.removeEdge(2, 3);
      assertDegrees(3, new int[]{3,0,0,0,0});
      instance.removeEdge(0, 1);
      assertDegrees(2, new int[]{2,0,0,0,0});
      instance.removeEdge(0, 4);
      assertDegrees(1, new int[]{1,0,0,0,0});
      instance.removeEdge(0, 3);
      assertDegrees(0, new int[]{0,0,0,0,0});
   }

   public void assertDegrees(int max, int ds[]) {
      // Test getDegree(int)
      for(int i = 0; i < ds.length; i++) {
         assertEquals(ds[i], instance.getDegree(i));
      }
      // Test getMaxDegree()
      assertEquals(max, instance.getMaxDegree());
   }
}