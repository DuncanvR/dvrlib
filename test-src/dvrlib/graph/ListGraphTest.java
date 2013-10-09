/*
 * DvRLib - Graph
 * Copyright (C) Duncan van Roermund, 2010-2013
 * ListGraphTest.java
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dvrlib.graph;

import dvrlib.generic.IterableOnce;
import dvrlib.generic.Triple;

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

public class ListGraphTest {
   protected AbstractGraph<Integer, ListGraphNode<Integer, Integer, Object>, Integer, Object> instance;

   @SuppressWarnings("unchecked")
   public void newInstance(int nodeCount) {
      instance = new ListGraph<Integer, Integer, Object>() {
            @Override
            protected Integer mergeNodeData(Integer a, Integer b) {
               return a + b;
            }
         };
      for(int i = 0; i < nodeCount; i++) {
         instance.add(i, i);
      }
   }

   @Test
   public void testGetEdgeCount() {
      newInstance(3);
      assertEquals(0, instance.edgeCount());
      instance.addEdge(0, 1);
      assertEquals(1, instance.edgeCount());
      instance.addEdge(2, 1);
      assertEquals(2, instance.edgeCount());
      instance.removeEdge(2, 1);
      assertEquals(1, instance.edgeCount());
      instance.removeEdge(0, 1);
      assertEquals(0, instance.edgeCount());
   }

   @Test
   public void testHasEdge() {
      newInstance(10);
      for(int i = 1; i < instance.nodeCount(); i += 2) {
         for(int j = 0; j < i; j += 2) {
            instance.addEdge(i, j);
         }
      }
      for(int i = 0; i < instance.nodeCount(); i++) {
         for(int j = 0; j < instance.nodeCount(); j++) {
            assertEquals((i % 2 == 1 && j % 2 == 0 && j < i), instance.hasEdge(i, j));
         }
      }
   }

   @Test
   public void testMerge() {
      newInstance(3);
      instance.addEdge(0, 1);
      assertDegrees(new int[]{0,1,0}, new int[]{1,0,0});
      assertFalse(instance.hasEdge(0, 0));
      assertTrue (instance.hasEdge(0, 1));
      assertFalse(instance.hasEdge(0, 2));
      assertFalse(instance.hasEdge(1, 0));
      assertFalse(instance.hasEdge(1, 1));
      assertFalse(instance.hasEdge(1, 2));
      assertFalse(instance.hasEdge(2, 0));
      assertFalse(instance.hasEdge(2, 1));
      assertFalse(instance.hasEdge(2, 2));
      instance.merge(1, 2);
      assertDegrees(new int[]{0,1,1}, new int[]{1,0,0});
      assertFalse(instance.hasEdge(0, 0));
      assertTrue (instance.hasEdge(0, 1));
      assertTrue (instance.hasEdge(0, 2));
      assertFalse(instance.hasEdge(1, 0));
      assertFalse(instance.hasEdge(1, 1));
      assertFalse(instance.hasEdge(1, 2));
      assertFalse(instance.hasEdge(2, 0));
      assertFalse(instance.hasEdge(2, 1));
      assertFalse(instance.hasEdge(2, 2));

      newInstance(3);
      instance.addEdge(0, 2);
      assertDegrees(new int[]{0,0,1}, new int[]{1,0,0});
      assertFalse(instance.hasEdge(0, 0));
      assertFalse(instance.hasEdge(0, 1));
      assertTrue (instance.hasEdge(0, 2));
      assertFalse(instance.hasEdge(1, 0));
      assertFalse(instance.hasEdge(1, 1));
      assertFalse(instance.hasEdge(1, 2));
      assertFalse(instance.hasEdge(2, 0));
      assertFalse(instance.hasEdge(2, 1));
      assertFalse(instance.hasEdge(2, 2));
      instance.merge(1, 2);
      assertDegrees(new int[]{0,1,1}, new int[]{1,0,0});
      assertFalse(instance.hasEdge(0, 0));
      assertTrue (instance.hasEdge(0, 1));
      assertTrue (instance.hasEdge(0, 2));
      assertFalse(instance.hasEdge(1, 0));
      assertFalse(instance.hasEdge(1, 1));
      assertFalse(instance.hasEdge(1, 2));
      assertFalse(instance.hasEdge(2, 0));
      assertFalse(instance.hasEdge(2, 1));
      assertFalse(instance.hasEdge(2, 2));

      newInstance(3);
      instance.addEdge(0, 1);
      instance.addEdge(0, 2);
      assertDegrees(new int[]{0,1,1}, new int[]{2,0,0});
      assertFalse(instance.hasEdge(0, 0));
      assertTrue (instance.hasEdge(0, 1));
      assertTrue (instance.hasEdge(0, 2));
      assertFalse(instance.hasEdge(1, 0));
      assertFalse(instance.hasEdge(1, 1));
      assertFalse(instance.hasEdge(1, 2));
      assertFalse(instance.hasEdge(2, 0));
      assertFalse(instance.hasEdge(2, 1));
      assertFalse(instance.hasEdge(2, 2));
      instance.merge(1, 2);
      assertDegrees(new int[]{0,1,1}, new int[]{1,0,0});
      assertFalse(instance.hasEdge(0, 0));
      assertTrue (instance.hasEdge(0, 1));
      assertTrue (instance.hasEdge(0, 2));
      assertFalse(instance.hasEdge(1, 0));
      assertFalse(instance.hasEdge(1, 1));
      assertFalse(instance.hasEdge(1, 2));
      assertFalse(instance.hasEdge(2, 0));
      assertFalse(instance.hasEdge(2, 1));
      assertFalse(instance.hasEdge(2, 2));

      newInstance(3);
      instance.addEdge(0, 1);
      assertDegrees(new int[]{0,1,0}, new int[]{1,0,0});
      instance.merge(0, 1);
      assertDegrees(new int[]{0,0,0}, new int[]{0,0,0});
      newInstance(3);
      instance.addEdge(0, 2);
      assertDegrees(new int[]{0,0,1}, new int[]{1,0,0});
      instance.merge(0, 1);
      assertDegrees(new int[]{0,0,1}, new int[]{1,1,0});
      newInstance(3);
      instance.addEdge(0, 1);
      instance.addEdge(0, 2);
      assertDegrees(new int[]{0,1,1}, new int[]{2,0,0});
      instance.merge(0, 1);
      assertDegrees(new int[]{0,0,1}, new int[]{1,1,0});
   }

   @Test
   public void testDegrees() {
      newInstance(6);
      assertDegrees(new int[]{0,0,0,0,0,0}, new int[]{0,0,0,0,0,0});
      assertTrue(instance.addEdge(0, 1));
      assertDegrees(new int[]{0,1,0,0,0,0}, new int[]{1,0,0,0,0,0});
      assertTrue(instance.addEdge(2, 1));
      assertDegrees(new int[]{0,2,0,0,0,0}, new int[]{1,0,1,0,0,0});
      assertTrue(instance.addEdge(2, 3));
      assertDegrees(new int[]{0,2,0,1,0,0}, new int[]{1,0,2,0,0,0});
      assertTrue(instance.addEdge(2, 5));
      assertDegrees(new int[]{0,2,0,1,0,1}, new int[]{1,0,3,0,0,0});
      assertTrue(instance.addEdge(4, 5));
      assertDegrees(new int[]{0,2,0,1,0,2}, new int[]{1,0,3,0,1,0});
      assertEquals(3, instance.remove(3).intValue());
      assertDegrees(new int[]{0,2,0,  0,2}, new int[]{1,0,2,  1,0});
      assertEquals(1, instance.remove(1).intValue());
      assertDegrees(new int[]{0,  0,  0,2}, new int[]{0,  1,  1,0});
      assertEquals(2, instance.remove(2).intValue());
      assertTrue(instance.addEdge(0, 4));
      assertDegrees(new int[]{0,      1,1}, new int[]{1,      1,0});
      assertTrue(instance.addEdge(0, 5));
      assertDegrees(new int[]{0,      1,2}, new int[]{2,      1,0});
      assertEquals(4, instance.remove(4).intValue());
      assertDegrees(new int[]{0,        1}, new int[]{1,        0});
      assertEquals(0, instance.remove(0).intValue());
      assertDegrees(new int[]{          0}, new int[]{          0});
      assertEquals(5, instance.remove(5).intValue());
      assertDegrees(new int[]{           }, new int[]{           });

      newInstance(5);
      assertDegrees(new int[]{0,0,0,0,0}, new int[]{0,0,0,0,0});
      assertTrue(instance.addEdge(0, 1));
      assertDegrees(new int[]{0,1,0,0,0}, new int[]{1,0,0,0,0});
      assertTrue(instance.addEdge(2, 3));
      assertDegrees(new int[]{0,1,0,1,0}, new int[]{1,0,1,0,0});
      assertTrue(instance.addEdge(0, 4));
      assertDegrees(new int[]{0,1,0,1,1}, new int[]{2,0,1,0,0});
      assertTrue(instance.addEdge(2, 4));
      assertDegrees(new int[]{0,1,0,1,2}, new int[]{2,0,2,0,0});
      assertTrue(instance.addEdge(0, 3));
      assertDegrees(new int[]{0,1,0,2,2}, new int[]{3,0,2,0,0});
      assertNull(instance.removeEdge(2, 4));
      assertDegrees(new int[]{0,1,0,2,1}, new int[]{3,0,1,0,0});
      assertNull(instance.removeEdge(2, 3));
      assertDegrees(new int[]{0,1,0,1,1}, new int[]{3,0,0,0,0});
      assertNull(instance.removeEdge(0, 1));
      assertDegrees(new int[]{0,0,0,1,1}, new int[]{2,0,0,0,0});
      assertNull(instance.removeEdge(0, 4));
      assertDegrees(new int[]{0,0,0,1,0}, new int[]{1,0,0,0,0});
      assertNull(instance.removeEdge(0, 3));
      assertDegrees(new int[]{0,0,0,0,0}, new int[]{0,0,0,0,0});

      newInstance(5);
      assertTrue(instance.addEdge(0, 1));
      assertTrue(instance.addEdge(0, 2));
      assertTrue(instance.addEdge(1, 2));
      assertTrue(instance.addEdge(1, 3));
      assertTrue(instance.addEdge(2, 3));
      assertTrue(instance.addEdge(2, 4));
      assertTrue(instance.addEdge(3, 4));
      assertDegrees(new int[]{0,1,2,2,2}, new int[]{2,2,2,1,0});
      instance.merge(2, 3);
      assertEquals(instance.node(2), instance.node(3));
      assertEquals(5, instance.node(2).data.intValue());
      assertDegrees(new int[]{0,1,2,2,1}, new int[]{2,1,1,1,0});
      instance.merge(1, 4);
      assertEquals(5, instance.node(1).data.intValue());
      assertDegrees(new int[]{0,2,2,2,2}, new int[]{2,1,1,1,1});
      assertNull(instance.removeEdge(1, 2));
      assertDegrees(new int[]{0,2,1,1,2}, new int[]{2,0,1,1,0});
   }

   public void assertDegrees(int inDs[], int outDs[]) {
      assertEquals(inDs.length, outDs.length);
      int maxInDegree = 0, maxOutDegree = 0;
      for(int i = 0, j = 0; j < Math.max(instance.nodeCount(), inDs.length); i++) {
         if(instance.contains(i)) {
            assertEquals(inDs[j],  instance.node(i).inDegree());
            assertEquals(outDs[j], instance.node(i).outDegree());
            maxInDegree  = Math.max(maxInDegree,  inDs[j]);
            maxOutDegree = Math.max(maxOutDegree, outDs[j]);
            j++;
         }
      }
      assertEquals(maxInDegree,  instance.maxInDegree());
      assertEquals(maxOutDegree, instance.maxOutDegree());
   }

   @Test
   public void testEdgeIterables() {
      newInstance(10);
      boolean arrA[][] = new boolean[instance.nodeCount()][instance.nodeCount()],
              arrB[][] = new boolean[instance.nodeCount()][instance.nodeCount()];
      for(int i = 1; i < instance.nodeCount(); i += 2) {
         for(int j = 0; j < i; j += 2) {
            instance.addEdge(i, j);
            arrA[i][j] = arrB[i][j] = true;
         }
      }
      for(int i = 0; i < instance.nodeCount(); i++) {
         if(i % 2 == 0) {
            for(Triple<ListGraphNode<Integer, Integer, Object>, Object, ListGraphNode<Integer, Integer, Object>> t :
                  new IterableOnce<Triple<ListGraphNode<Integer, Integer, Object>, Object, ListGraphNode<Integer, Integer, Object>>>(instance.inEdgesIterator(i))) {
               assertEquals(instance.node(i), t.c);
               assertTrue(arrA[t.a.data][i]);
               arrA[t.a.data][i] = false;
            }
            for(Object o : new IterableOnce<Triple<ListGraphNode<Integer, Integer, Object>, Object, ListGraphNode<Integer, Integer, Object>>>(instance.outEdgesIterator(i))) {
               fail();
            }
         }
         else {
            for(Object o : new IterableOnce<Triple<ListGraphNode<Integer, Integer, Object>, Object, ListGraphNode<Integer, Integer, Object>>>(instance.inEdgesIterator(i))) {
               fail();
            }
            for(Triple<ListGraphNode<Integer, Integer, Object>, Object, ListGraphNode<Integer, Integer, Object>> t :
                  new IterableOnce<Triple<ListGraphNode<Integer, Integer, Object>, Object, ListGraphNode<Integer, Integer, Object>>>(instance.outEdgesIterator(i))) {
               assertEquals(instance.node(i), t.a);
               assertTrue(arrB[i][t.c.data]);
               arrB[i][t.c.data] = false;
            }
         }
      }
      for(int i = 0; i < instance.nodeCount(); i++) {
         for(int j = 0; j < instance.nodeCount(); j++) {
            assertFalse(arrA[i][j]);
            assertFalse(arrB[i][j]);
         }
      }
   }
}
