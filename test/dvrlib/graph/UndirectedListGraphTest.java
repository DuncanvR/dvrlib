/*
 * DvRLib - Graph
 * Duncan van Roermund, 2010
 * UndirectedListGraphTest.java
 */

package dvrlib.graph;

import static org.junit.Assert.*;

public class UndirectedListGraphTest extends ListGraphTest {

   @Override
   public AbstractGraph newInstance(int nodeCount) {
      return new UndirectedListGraph(nodeCount);
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

      assertTrue(instance.removeEdge(3, 2));
      assertDegrees(new int[]{1,2,2,0,1,2});
      assertTrue(instance.removeEdge(1, 0));
      assertDegrees(new int[]{0,1,2,0,1,2});
      assertTrue(instance.removeEdge(1, 2));
      assertDegrees(new int[]{0,0,1,0,1,2});
      assertTrue(instance.removeEdge(5, 4));
      assertDegrees(new int[]{0,0,1,0,0,1});
      assertTrue(instance.removeEdge(5, 2));
      assertDegrees(new int[]{0,0,0,0,0,0});

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
