/*
 * DvRlib - Container
 * Copyright (C) Duncan van Roermund, 2010
 * WeightedTreeTest.java
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

package dvrlib.container;

import dvrlib.generic.Pair;

import java.util.Iterator;

import org.junit.Test;
import static org.junit.Assert.*;

public class WeightedTreeTest {
   @Test
   public void testSize() {
      WeightedTree<Integer> instance = new WeightedTree<Integer>();
      Pair<Double, Integer> element;

      assertTrue(instance.isEmpty());
      assertEquals(0, instance.size());
      for(int i = 1; i < 9; i++) {
         instance.add((double) i, 10 - i);

         assertFalse(instance.isEmpty());
         assertEquals(i, instance.size());

         testBalance(instance, instance.root);
      }

      element = instance.peekMin();
      assertEquals(1, element.a.intValue());
      assertEquals(9, element.b.intValue());

      for(int i = 8; i > 0; i--) {
         assertEquals(i, instance.size());
         assertFalse(instance.isEmpty());

         element = instance.peekMax();
         assertEquals(i, element.a.intValue());
         assertEquals(10 - i, element.b.intValue());

         element = instance.popMax();
         assertEquals(i, element.a.intValue());
         assertEquals(10 - i, element.b.intValue());

         testBalance(instance, instance.root);
      }

      assertTrue(instance.isEmpty());

      for(int i = 8; i > 0; i--) {
         instance.add((double) i, 10 + i);
         testBalance(instance, instance.root);
      }

      for(int i = 1; i < 9; i++) {
         element = instance.peekMin();
         assertEquals(i, element.a.intValue());
         assertEquals(10 + i, element.b.intValue());

         element = instance.popMin();
         assertEquals(i, element.a.intValue());
         assertEquals(10 + i, element.b.intValue());

         testBalance(instance, instance.root);
      }

      assertTrue(instance.isEmpty());

      for(int i = 1; i < 20; i++) {
         instance.add((double) 1 + (i % 5), i);

         assertFalse(instance.isEmpty());
         assertEquals(i, instance.size());

         testBalance(instance, instance.root);
      }

      assertTrue(instance.remove(2, 1));
      testBalance(instance, instance.root);
      assertTrue(instance.remove(2, 6));
      testBalance(instance, instance.root);
      assertTrue(instance.remove(2, 11));
      testBalance(instance, instance.root);
      assertTrue(instance.remove(2, 16));
      testBalance(instance, instance.root);
   }

   @Test
   public void testAdd() {
      WeightedTree<Number> instance;
      instance = testAdd(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
      instance = testAdd(new Integer[]{9, 8, 7, 6, 5, 4, 3, 2, 1});
      instance = testAdd(new Integer[]{1, 9, 2, 8, 3, 7, 4, 6, 5});
      instance = testAdd(new Integer[]{5, 4, 6, 3, 7, 2, 8, 1, 9});
      instance = testAdd(new Integer[]{4, 2, 6, 3, 7, 8, 1, 5, 9});
      instance = testAdd(new Double []{0.1, 0.2, 0.3, 0.4, 0.5, 0.6});
      instance = testAdd(new Double []{0.6, 0.5, 0.4, 0.3, 0.2, 0.1});
      instance = testAdd(new Double []{0.1, 0.6, 0.2, 0.5, 0.3, 0.4});

      Number vs[] = new Double []{0.5, 0.3, 0.2, 0.6, 0.1, 0.4};
      instance = testAdd(vs);
      testBalance(instance, instance.root);
      assertContains(instance, vs);
      assertEquals(0.6, instance.max().key, 0.0);
      instance.popMax();
      testBalance(instance, instance.root);
      assertEquals(0.1, instance.min().key, 0.0);
      assertEquals(0.5, instance.max().key, 0.0);
      instance.popMin();
      testBalance(instance, instance.root);
      assertEquals(0.2, instance.min().key, 0.0);
      assertEquals(0.5, instance.max().key, 0.0);
      instance.popMax();
      testBalance(instance, instance.root);
      assertEquals(0.2, instance.min().key, 0.0);
      assertEquals(0.4, instance.max().key, 0.0);

      vs = new Double []{0.3, 0.4, 0.2, 0.5, 0.1, 0.6, 0.2, 0.2};
      instance = testAdd(vs);
      testBalance(instance, instance.root);
      assertContains(instance, vs);
      assertTrue(instance.remove(0.3, new Double(0.3)));
      assertFalse(instance.remove(0.3, new Double(0.3)));
      testBalance(instance, instance.root);
      assertTrue(instance.remove(0.2, new Double(0.2)));
      testBalance(instance, instance.root);
      assertTrue(instance.remove(0.2, new Double(0.2)));
      testBalance(instance, instance.root);
      assertTrue(instance.remove(0.2, new Double(0.2)));
      assertFalse(instance.remove(0.2, new Double(0.2)));
      testBalance(instance, instance.root);
      assertTrue(instance.remove(0.1, new Double(0.1)));
      assertFalse(instance.remove(0.1, new Double(0.1)));
      testBalance(instance, instance.root);

      vs = new Double []{0.3, 0.4, 0.2, 0.5, 0.1, 0.6, 0.2, 0.2};
      instance = testAdd(vs);
      assertContains(instance, vs);
      testBalance(instance, instance.root);
      instance.retainBest(6);
      assertContains(instance, new Double[]{0.2, 0.2, 0.3, 0.4, 0.5, 0.6});
      testBalance(instance, instance.root);
      instance.retainBest(4);
      assertContains(instance, new Double[]{0.3, 0.4, 0.5, 0.6});
      testBalance(instance, instance.root);
      instance.retainBest(1);
      assertContains(instance, new Double[]{0.6});
      testBalance(instance, instance.root);
   }
   public WeightedTree<Number> testAdd(Number keys[]) {
      WeightedTree<Number> instance = new WeightedTree<Number>();
      assertNull(instance.root);

      double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
      for(Number n : keys) {
         double nd = n.doubleValue();
         instance.add(nd, n);

         if(nd < min)
            min = nd;
         if(nd > max)
            max = nd;

         testBalance(instance, instance.root);
      }

      assertNotNull(instance.root);
      assertNull(instance.root.parent);
      assertEquals(min, instance.min().key, 0.0);
      assertEquals(max, instance.max().key, 0.0);
      assertEquals(keys.length, instance.size());

      return instance;
   }
   @SuppressWarnings("unchecked")
   public void testBalance(WeightedTree instance, WeightedTree.Node node) {
      if(node != null) {
         assertTrue(Math.abs(instance.height(node.left) - instance.height(node.right)) < 2);
         if(node.left != null) {
            assertTrue(((WeightedTree.Node) node.left).key <= ((WeightedTree.Node) node).key);
            testBalance(instance, (WeightedTree.Node) node.left);
         }
         if(node.right != null) {
            assertTrue(((WeightedTree.Node) node.right).key > ((WeightedTree.Node) node).key);
            testBalance(instance, (WeightedTree.Node) node.right);
         }
      }
   }
   public void assertContains(WeightedTree<Number> instance, Number ks[]) {
      assertEquals(ks.length, instance.size());
      for(Number k : ks) {
         assertTrue(instance.contains(k.doubleValue(), k));
      }
   }

   @Test
   public void testIterator() {
      WeightedTree<Integer> instance1 = new WeightedTree<Integer>(),
                            instance2 = new WeightedTree<Integer>();
      assertFalse(instance1.iterator().hasNext());
      assertFalse(instance2.iterator().hasNext());

      for(int i = 0; i < 9; i++) {
         for(int j = 0; j < 9; j++) {
            instance1.add((double) i, i + j);
            instance2.add((double) j, i + j);
         }
      }
      Iterator<Integer> it1 = instance1.iterator(),
                        it2 = instance2.iterator();
      for(int i = 8; i >= 0; i--) {
         for(int j = 0; j < 9; j++) {
            assertTrue(it1.hasNext());
            assertTrue(it2.hasNext());
            assertEquals(i + j, it1.next().intValue());
            assertEquals(i + j, it2.next().intValue());
            if(i % 3 == 0)
               it1.remove();
            if(j % 2 == 0)
               it2.remove();
         }
      }
      assertFalse(it1.hasNext());
      assertFalse(it2.hasNext());

      it1 = instance1.iterator();
      it2 = instance2.iterator();
      for(int i = 8; i >= 0; i--) {
         for(int j = 0; j < 9; j++) {
            if(i % 3 != 0) {
               assertTrue(it1.hasNext());
               assertEquals(i + j, it1.next().intValue());
            }
            if(j % 2 != 0) {
               assertTrue(it2.hasNext());
               assertEquals(i + j, it2.next().intValue());
            }
         }
      }
      assertFalse(it1.hasNext());
      assertFalse(it2.hasNext());
   }

   @Test
   public void testWeighted() {
      WeightedTree<Integer> instance = new WeightedTree<Integer>();
      assertNull(instance.getWeighted(0.5));

      instance.add(0.3, 10);
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.0));
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.5));
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.9999999));

      instance.add(0.5, 15);
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.0));
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.3749999));
      assertEqualsWeighted(0.5, 15, instance.getWeighted(0.3750001));
      assertEqualsWeighted(0.5, 15, instance.getWeighted(0.9999999));

      instance.add(0.2, 20);
      assertEqualsWeighted(0.2, 20, instance.getWeighted(0.0));
      assertEqualsWeighted(0.2, 20, instance.getWeighted(0.1999999));
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.2000001));
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.4999999));
      assertEqualsWeighted(0.5, 15, instance.getWeighted(0.5000001));
      assertEqualsWeighted(0.5, 15, instance.getWeighted(0.9999999));

      instance.add(0.5, 25);
      assertEqualsWeighted(0.2, 20, instance.getWeighted(0.0));
      assertEqualsWeighted(0.2, 20, instance.getWeighted(0.1333332));
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.1333334));
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.3333332));
      assertEqualsWeighted(0.5, 15, instance.getWeighted(0.3333334));
      assertEqualsWeighted(0.5, 15, instance.getWeighted(0.6666665));
      assertEqualsWeighted(0.5, 25, instance.getWeighted(0.6666667));
      assertEqualsWeighted(0.5, 25, instance.getWeighted(0.9999999));

      instance.remove(0.5, 15);
      assertEqualsWeighted(0.2, 20, instance.getWeighted(0.0));
      assertEqualsWeighted(0.2, 20, instance.getWeighted(0.1999999));
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.2000001));
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.4999999));
      assertEqualsWeighted(0.5, 25, instance.getWeighted(0.5000001));
      assertEqualsWeighted(0.5, 25, instance.getWeighted(0.9999999));

      instance.remove(0.2, 20);
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.0));
      assertEqualsWeighted(0.3, 10, instance.getWeighted(0.3749999));
      assertEqualsWeighted(0.5, 25, instance.getWeighted(0.3750001));
      assertEqualsWeighted(0.5, 25, instance.getWeighted(0.9999999));
   }
   public void assertEqualsWeighted(double k, int v, Pair<Double, Integer> e) {
      assertEquals(k, e.a.doubleValue(), 0.0);
      assertEquals(v, e.b.intValue());
   }
}
