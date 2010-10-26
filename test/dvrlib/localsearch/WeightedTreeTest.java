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
      }
      assertTrue(instance.isEmpty());
      for(int i = 8; i > 0; i--) {
         instance.add((double) i, 10 + i);
      }
      for(int i = 1; i < 9; i++) {
         element = instance.peekMin();
         assertEquals(i, element.a.intValue());
         assertEquals(10 + i, element.b.intValue());
         element = instance.popMin();
         assertEquals(i, element.a.intValue());
         assertEquals(10 + i, element.b.intValue());
      }
   }

   @Test
   public void testAddRemove() {
      WeightedTree<Double> instance = new WeightedTree();
      assertNull(instance.root);
      instance.add(5.0, 5.0);
      assertNotNull(instance.root);
      assertNull(instance.root.parent);
   }
}