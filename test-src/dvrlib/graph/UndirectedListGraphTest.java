/*
 * DvRlib - Graph
 * Duncan van Roermund, 2010-2013
 * UndirectedListGraphTest.java
 */

package dvrlib.graph;

import dvrlib.generic.IterableOnce;
import dvrlib.generic.Triple;

import static org.junit.Assert.*;

public class UndirectedListGraphTest extends ListGraphTest {

   @Override
   @SuppressWarnings("unchecked")
   public void newInstance(int nodeCount) {
      instance = new UndirectedListGraph<Integer, Integer, Object>() {
            @Override
            protected Integer mergeNodeData(Integer a, Integer b) {
               return a + b;
            }
         };
      for(int i = 0; i < nodeCount; i++) {
         instance.add(i, i);
      }
   }

   @Override
   public void testHasEdge() {
      newInstance(10);
      for(int i = 1; i < instance.nodeCount(); i += 2) {
         for(int j = 0; j < i; j += 2) {
            instance.addEdge(i, j);
         }
      }
      for(int i = 0; i < instance.nodeCount(); i++) {
         for(int j = 0; j < instance.nodeCount(); j++) {
            assertEquals((i % 2 == 0 && j % 2 == 1 && i < j) || (i % 2 == 1 && j % 2 == 0 && i > j), instance.hasEdge(i, j));
         }
      }
   }

   @Override
   public void testDegrees() {
      newInstance(6);
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
      assertEquals(2, instance.remove(2).intValue());
      assertDegrees(new int[]{1,1,  0,1,1});
      assertEquals(4, instance.remove(4).intValue());
      assertDegrees(new int[]{1,1,  0,  0});
      assertEquals(3, instance.remove(3).intValue());
      assertDegrees(new int[]{1,1,      0});
      assertEquals(0, instance.remove(0).intValue());
      assertDegrees(new int[]{  0,      0});
      assertEquals(5, instance.remove(5).intValue());
      assertDegrees(new int[]{  0        });
      assertEquals(1, instance.remove(1).intValue());
      assertDegrees(new int[]{           });

      newInstance(5);
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
      assertNull(instance.removeEdge(2, 4));
      assertDegrees(new int[]{3,1,1,2,1});
      assertNull(instance.removeEdge(2, 3));
      assertDegrees(new int[]{3,1,0,1,1});
      assertNull(instance.removeEdge(0, 1));
      assertDegrees(new int[]{2,0,0,1,1});
      assertNull(instance.removeEdge(0, 4));
      assertDegrees(new int[]{1,0,0,1,0});
      assertNull(instance.removeEdge(0, 3));
      assertDegrees(new int[]{0,0,0,0,0});

      newInstance(5);
      assertTrue(instance.addEdge(0, 1));
      assertTrue(instance.addEdge(0, 2));
      assertTrue(instance.addEdge(1, 3));
      assertTrue(instance.addEdge(1, 4));
      assertTrue(instance.addEdge(2, 3));
      assertTrue(instance.addEdge(2, 4));
      assertDegrees(new int[]{2,3,3,2,2});
      instance.merge(1, 3);
      assertEquals(4, instance.node(1).data.intValue());
      try {
         instance.add(3, 3);
         fail("Should not be able to add a node with id 3");
      }
      catch(IllegalArgumentException _) { }
      assertFalse(instance.addEdge(1, 3));
      assertDegrees(new int[]{2,3,3,3,2});
      instance.merge(2, 4);
      assertEquals(6, instance.node(2).data.intValue());
      assertDegrees(new int[]{2,2,2,2,2});
      assertNull(instance.removeEdge(1, 2));
      assertDegrees(new int[]{2,1,1,0,1});
   }

   public void assertDegrees(int ds[]) {
      assertDegrees(ds, ds);
   }

   @Override
   @SuppressWarnings("unchecked")
   public void testEdgeIterables() {
      newInstance(10);
      char arr[][] = new char[instance.nodeCount()][instance.nodeCount()];
      for(int i = 1; i < instance.nodeCount(); i += 2) {
         for(int j = 0; j < i; j += 2) {
            instance.addEdge(i, j);
            arr[i][j] = arr[j][i] = 2;
         }
      }
      for(int i = 0; i < instance.nodeCount(); i++) {
         for(Triple<ListGraphNode<Integer, Integer, Object>, Object, ListGraphNode<Integer, Integer, Object>> t :
               new IterableOnce<Triple<ListGraphNode<Integer, Integer, Object>, Object, ListGraphNode<Integer, Integer, Object>>>(instance.inEdgesIterator(i))) {
            assertEquals(instance.node(i), t.c);
            assertTrue(0 < arr[t.a.data][i]--);
         }
         for(Triple<ListGraphNode<Integer, Integer, Object>, Object, ListGraphNode<Integer, Integer, Object>> t :
               new IterableOnce<Triple<ListGraphNode<Integer, Integer, Object>, Object, ListGraphNode<Integer, Integer, Object>>>(instance.outEdgesIterator(i))) {
            assertEquals(instance.node(i), t.a);
            assertTrue(0 < arr[i][t.c.data]--);
         }
      }
      for(int i = 0; i < instance.nodeCount(); i++) {
         for(int j = 0; j < instance.nodeCount(); j++) {
            assertTrue(arr[i][j] == 0);
         }
      }
   }
}
