/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * WeightedTreeTest.java
 */

package dvrlib.generic.tree;

import dvrlib.generic.Pair;
import org.junit.Test;
import static org.junit.Assert.*;

public class WeightedTreeTest {
   @Test
   public void testSize() {
      WeightedTree<Integer, Integer> instance = new WeightedTree();
      Pair<Integer, Integer> element;

      assertTrue(instance.isEmpty());
      for(int i = 1; i < 9; i++) {
         instance.add(i, 10 - i);
         assertEquals(i, instance.size());
         assertFalse(instance.isEmpty());
      }
      for(int i = 8; i > 0; i--) {
         assertEquals(i, instance.size());
         assertFalse(instance.isEmpty());
         element = instance.peekMax();
         assertEquals(i, (int) element.a);
         assertEquals(10 - i, (int) element.b);
         element = instance.popMax();
         assertEquals(i, (int) element.a);
         assertEquals(10 - i, (int) element.b);
      }
      assertTrue(instance.isEmpty());
      for(int i = 8; i > 0; i--) {
         instance.add(i, 10 + i);
      }
      for(int i = 1; i < 9; i++) {
         element = instance.peekMin();
         assertEquals(i, (int) element.a);
         assertEquals(10 + i, (int) element.b);
         element = instance.popMin();
         assertEquals(i, (int) element.a);
         assertEquals(10 + i, (int) element.b);
      }
   }

   @Test
   public void testAddRemove() {
      WeightedTree instance = new WeightedTree();
      assertNull(instance.root);
      instance.add(5, 5);
      assertNotNull(instance.root);
      assertNull(instance.root.parent);
   }
}