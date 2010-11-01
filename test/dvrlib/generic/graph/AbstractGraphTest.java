/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * AbstractGraphTest.java
 */

package dvrlib.generic.graph;

import java.util.Collection;
import org.junit.Test;
import static org.junit.Assert.*;

public abstract class AbstractGraphTest {
   AbstractGraph instance;

   public abstract AbstractGraph newInstance(int nodeCount);
   
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
      instance = newInstance(10);
      for(int i = 1; i < instance.nodeCount; i += 2) {
         for(int j = 0; j < i; j += 2) {
            instance.addEdge(i, j);
         }
      }

      // Test hasEdge(int, int)
      for(int i = 0; i < instance.nodeCount; i++) {
         for(int j = 0; j < instance.nodeCount; j++) {
            assertEquals((i % 2 == 1 && j % 2 == 0 && j < i), instance.hasEdge(i, j));
         }
      }
   }

   @Test
   public void testEdges() {
      instance = newInstance(6);
      AbstractEdge edges[] = new AbstractEdge[5];
      edges[0] = addEdge(0, 1);
      edges[1] = addEdge(2, 1);
      edges[2] = addEdge(2, 3);
      edges[3] = addEdge(5, 2);
      edges[4] = addEdge(5, 4);
      assertEdges(edges);

      edges[4] = addEdge(5, 4);
      edges[3] = addEdge(5, 2);
      edges[2] = addEdge(2, 3);
      edges[1] = addEdge(2, 1);
      edges[0] = addEdge(0, 1);
      assertEdges(edges);

      edges[3] = addEdge(5, 2);
      edges[0] = addEdge(0, 1);
      edges[2] = addEdge(2, 3);
      edges[4] = addEdge(5, 4);
      edges[1] = addEdge(2, 1);
      assertEdges(edges);
   }

   protected abstract AbstractEdge addEdge(int a, int b);

   public void assertEdges(AbstractEdge edges[]) {
      // Test getEdge(int, int)
      for(AbstractEdge e : edges) {
         assertTrue(instance.hasEdge(e.a, e.b));
      }

      assertNeighbours(edges);

      // Test removeEdge(int, int)
      for(AbstractEdge e : edges) {
         assertTrue(instance.removeEdge(e.a, e.b));
         assertFalse(instance.hasEdge(e.a, e.b));
      }

      for(int i = 0; i < instance.nodeCount; i++) {
         for(int j = 0; j < instance.nodeCount; j++) {
            assertFalse(instance.hasEdge(i, j));
         }
      }
   }

   public void assertNeighbours(AbstractEdge edges[]) {
      // Test getNeighbours(int)
      int totalNeighbourCount = 0;
      for(int i = 0; i < instance.getNodeCount(); i++) {
         Collection<MatrixGraphNode> neighbours = instance.getNeighbours(i);
         for(AbstractEdge e : edges) {
            if(i == e.a)
               assertTrue(instance.hasEdge(e.a, e.b));
         }
         totalNeighbourCount += neighbours.size();
      }
      assertNeighbours(edges.length, totalNeighbourCount);
   }

   protected void assertNeighbours(int edges, int neighbours) {
      assertEquals(edges, neighbours);
   }

   @Test
   public void testDegrees() {
      instance = newInstance(6);
      assertDegrees(new int[]{0,0,0,0,0,0});
      assertTrue(instance.addEdge(0, 1));
      assertDegrees(new int[]{1,0,0,0,0,0});
      assertTrue(instance.addEdge(2, 1));
      assertDegrees(new int[]{1,0,1,0,0,0});
      assertTrue(instance.addEdge(2, 3));
      assertDegrees(new int[]{1,0,2,0,0,0});
      assertTrue(instance.addEdge(2, 5));
      assertDegrees(new int[]{1,0,3,0,0,0});
      assertTrue(instance.addEdge(4, 5));
      assertDegrees(new int[]{1,0,3,0,1,0});

      instance = newInstance(5);
      assertDegrees(new int[]{0,0,0,0,0});
      assertTrue(instance.addEdge(0, 1));
      assertDegrees(new int[]{1,0,0,0,0});
      assertTrue(instance.addEdge(2, 3));
      assertDegrees(new int[]{1,0,1,0,0});
      assertTrue(instance.addEdge(0, 4));
      assertDegrees(new int[]{2,0,1,0,0});
      assertTrue(instance.addEdge(2, 4));
      assertDegrees(new int[]{2,0,2,0,0});
      assertTrue(instance.addEdge(0, 3));
      assertDegrees(new int[]{3,0,2,0,0});
      assertTrue(instance.removeEdge(2, 4));
      assertDegrees(new int[]{3,0,1,0,0});
      assertTrue(instance.removeEdge(2, 3));
      assertDegrees(new int[]{3,0,0,0,0});
      assertTrue(instance.removeEdge(0, 1));
      assertDegrees(new int[]{2,0,0,0,0});
      assertTrue(instance.removeEdge(0, 4));
      assertDegrees(new int[]{1,0,0,0,0});
      assertTrue(instance.removeEdge(0, 3));
      assertDegrees(new int[]{0,0,0,0,0});
   }

   public void assertDegrees(int ds[]) {
      int maxDegree = 0;

      // Test getDegree(int)
      for(int i = 0; i < ds.length; i++) {
         assertEquals(ds[i], instance.getDegree(i));
         maxDegree = Math.max(maxDegree, ds[i]);
      }
      // Test getMaxDegree()
      assertEquals(maxDegree, instance.getMaxDegree());
   }
}