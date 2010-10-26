/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * WeightedTreeTest.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
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
   public void testAddRemove() {
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
         if(node.left != null) {
            assertTrue(node.left.a <= node.a);
            testBalance(node.left);
         }
         if(node.right != null) {
            assertTrue(node.right.a > node.a);
            testBalance(node.right);
         }
      }
   }
}