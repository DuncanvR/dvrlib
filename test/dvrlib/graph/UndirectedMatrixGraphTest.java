/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010
 * UndirectedMatrixGraphTest.java
 */

package dvrlib.graph;

import static org.junit.Assert.*;

public class UndirectedMatrixGraphTest extends MatrixGraphTest {

   @Override
   public MatrixGraph<Edge> newInstance(int nodeCount) {
      return new UndirectedMatrixGraph<Edge>(nodeCount);
   }

   @Override
   public void testHasEdge() {
      instance = newInstance(10);
      for(int i = 1; i < instance.nodeCount; i += 2) {
         for(int j = 0; j < i; j += 2) {
            instance.addEdge(i, j);
         }
      }

      // Test hasEdge(int, int)
      for(int i = 0; i < instance.nodeCount; i++) {
         for(int j = 0; j < instance.nodeCount; j++) {
            assertEquals((i % 2 == 0 && j % 2 == 1 && i < j) || (i % 2 == 1 && j % 2 == 0 && i > j), instance.hasEdge(i, j));
         }
      }

      // Test getFirstEdge(int) and getLastEdge(int)
      for(int i = 0; i < instance.nodeCount; i++) {
         Edge f = ((UndirectedMatrixGraph) instance).getFirstEdge(i), l = ((UndirectedMatrixGraph) instance).getLastEdge(i);
         if(i % 2 == 0) {
            assertNotNull(f);
            assertNotNull(l);
            assertEquals(i + 1, f.b);
            assertEquals(instance.nodeCount - 1, l.b);
         }
         else {
            assertNull(f);
            assertNull(l);
         }
      }
   }

   @Override
   protected void assertNeighbours(int edges, int neighbours) {
      assertEquals(edges * 2, neighbours);
   }

   @Override
   public void testDegrees() {
      instance = newInstance(6);
      assertDegrees(new int[]{0,0,0,0,0,0});
      assertTrue(instance.addEdge(0, 1));
      assertDegrees(new int[]{1,1,0,0,0,0});
      assertTrue(instance.addEdge(2, 1));
      assertDegrees(new int[]{1,2,1,0,0,0});
      assertTrue(instance.addEdge(2, 3));
      assertDegrees(new int[]{1,2,2,1,0,0});
      assertTrue(instance.addEdge(2, 5));
      assertDegrees(new int[]{1,2,3,1,0,1});
      assertTrue(instance.addEdge(4, 5));
      assertDegrees(new int[]{1,2,3,1,1,2});

      instance = newInstance(5);
      assertDegrees(new int[]{0,0,0,0,0});
      assertTrue(instance.addEdge(0, 1));
      assertDegrees(new int[]{1,1,0,0,0});
      assertTrue(instance.addEdge(2, 3));
      assertDegrees(new int[]{1,1,1,1,0});
      assertTrue(instance.addEdge(0, 4));
      assertDegrees(new int[]{2,1,1,1,1});
      assertTrue(instance.addEdge(2, 4));
      assertDegrees(new int[]{2,1,2,1,2});
      assertTrue(instance.addEdge(0, 3));
      assertDegrees(new int[]{3,1,2,2,2});
      assertTrue(instance.removeEdge(2, 4));
      assertDegrees(new int[]{3,1,1,2,1});
      assertTrue(instance.removeEdge(2, 3));
      assertDegrees(new int[]{3,1,0,1,1});
      assertTrue(instance.removeEdge(0, 1));
      assertDegrees(new int[]{2,0,0,1,1});
      assertTrue(instance.removeEdge(0, 4));
      assertDegrees(new int[]{1,0,0,1,0});
      assertTrue(instance.removeEdge(0, 3));
      assertDegrees(new int[]{0,0,0,0,0});
   }
}
