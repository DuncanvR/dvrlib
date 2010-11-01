/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * UndirectedGraphTest.java
 */

package dvrlib.generic.graph;

import java.util.Collection;
import static org.junit.Assert.*;

public class UndirectedGraphTest extends GraphTest {
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

      // Test hasEdge(int)
      for(int i = 0; i < instance.nodeCount; i++) {
         assertEquals((i % 2 == 0), instance.hasEdge(i));
      }

      // Test hasEdge(int, int)
      for(int i = 0; i < instance.nodeCount; i++) {
         for(int j = 0; j < instance.nodeCount; j++) {
            assertEquals((i % 2 == 0 && j % 2 == 1 && i < j) || (i % 2 == 1 && j % 2 == 0 && i > j), instance.hasEdge(i, j));
         }
      }

      // Test getFirstEdge(int) and getLastEdge(int)
      for(int i = 0; i < instance.nodeCount; i++) {
         Edge f = instance.getFirstEdge(i), l = instance.getLastEdge(i);
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
   public void assertNeighbours(Edge edges[]) {
      // Test getNeighbours(int)
      for(int i = 0; i < instance.getNodeCount(); i++) {
         Collection<AbstractNode> neighbours = instance.getNeighbours(i);
         for(Edge e : edges) {
            if(i == e.a)
               assertTrue(neighbours.remove(instance.getNode(e.b)));
            else if(i == e.b)
               assertTrue(neighbours.remove(instance.getNode(e.a)));
         }
         assertTrue(neighbours.isEmpty());
      }
   }

   @Override
   public void testDegrees() {
      instance = newInstance(6);
      assertDegrees(0, new int[]{0,0,0,0,0,0});
      instance.addEdge(0, 1);
      assertDegrees(1, new int[]{1,1,0,0,0,0});
      instance.addEdge(2, 1);
      assertDegrees(2, new int[]{1,2,1,0,0,0});
      instance.addEdge(2, 3);
      assertDegrees(2, new int[]{1,2,2,1,0,0});
      instance.addEdge(2, 5);
      assertDegrees(3, new int[]{1,2,3,1,0,1});
      instance.addEdge(4, 5);
      assertDegrees(3, new int[]{1,2,3,1,1,2});

      instance = newInstance(5);
      assertDegrees(0, new int[]{0,0,0,0,0});
      instance.addEdge(0, 1);
      assertDegrees(1, new int[]{1,1,0,0,0});
      instance.addEdge(2, 3);
      assertDegrees(1, new int[]{1,1,1,1,0});
      instance.addEdge(0, 4);
      assertDegrees(2, new int[]{2,1,1,1,1});
      instance.addEdge(2, 4);
      assertDegrees(2, new int[]{2,1,2,1,2});
      instance.addEdge(0, 3);
      assertDegrees(3, new int[]{3,1,2,2,2});
      instance.removeEdge(2, 4);
      assertDegrees(3, new int[]{3,1,1,2,1});
      instance.removeEdge(2, 3);
      assertDegrees(3, new int[]{3,1,0,1,1});
      instance.removeEdge(0, 1);
      assertDegrees(2, new int[]{2,0,0,1,1});
      instance.removeEdge(0, 4);
      assertDegrees(1, new int[]{1,0,0,1,0});
      instance.removeEdge(0, 3);
      assertDegrees(0, new int[]{0,0,0,0,0});
   }
}
