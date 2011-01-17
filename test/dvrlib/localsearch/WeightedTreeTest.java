/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * WeightedTreeTest.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

public class WeightedTreeTest {
   @Test
   public void testSize() {
      WeightedTree<Integer> instance = new WeightedTree();
      Pair<Double, Integer> element;

      assertTrue(instance.isEmpty());
      for(int i = 1; i < 9; i++) {
         instance.add((double) i, 10 - i);

         assertEquals(i, instance.size());
         assertFalse(instance.isEmpty());

         testBalance(instance.root);
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

         testBalance(instance.root);
      }

      assertTrue(instance.isEmpty());

      for(int i = 8; i > 0; i--) {
         instance.add((double) i, 10 + i);
         testBalance(instance.root);
      }

      for(int i = 1; i < 9; i++) {
         element = instance.peekMin();
         assertEquals(i, element.a.intValue());
         assertEquals(10 + i, element.b.intValue());

         element = instance.popMin();
         assertEquals(i, element.a.intValue());
         assertEquals(10 + i, element.b.intValue());

         testBalance(instance.root);
      }
   }

   @Test
   public void testAdd() {
      WeightedTree<Integer> instance = new WeightedTree();
      assertNull(instance.root);

      for(int i = 0; i < 5; i++) {
         for(int j = 0; j < 23; j++) {
            instance.add((double) j, i * j);
         }
         testBalance(instance.root);
      }

      assertNotNull(instance.root);
      assertNull(instance.root.parent);
   }

   public void testBalance(WeightedTreeNode<Integer> node) {
      if(node != null) {
         assertTrue(Math.abs(node.getLeftHeight() - node.getRightHeight()) < 2);
         if(node.left != null) {
            assertTrue(node.left.key <= node.key);
            testBalance(node.left);
         }
         if(node.right != null) {
            assertTrue(node.right.key > node.key);
            testBalance(node.right);
         }
      }
   }

   @Test
   public void testIterator() {
      WeightedTree<Integer> instance = new WeightedTree();
      assertFalse(instance.iterator().hasNext());

      for(int i = 0; i < 9; i++) {
         for(int j = 0; j < 9; j++) {
            instance.add((double) i, i + j);
         }
      }

      Iterator<Pair<Double, Integer>> it = instance.iterator();
      for(int i = 0; i < 9; i++) {
         for(int j = 0; j < 9; j++) {
            assertTrue(it.hasNext());
            Pair<Double, Integer> e = it.next();
            assertEquals(i, e.a.intValue());
            assertEquals(i + j, e.b.intValue());
         }
      }
      assertFalse(it.hasNext());
   }

   @Test
   public void testWeighted() {
      WeightedTree<Integer> instance = new WeightedTree();
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
   }

   public void assertEqualsWeighted(double k, int v, Pair<Double, Integer> e) {
      assertEquals(k, e.a.doubleValue(), 0.0);
      assertEquals(v, e.b.intValue());
   }
}