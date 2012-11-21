/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * GraphTest.java
 */

package dvrlib.generic;

import org.junit.Test;
import static org.junit.Assert.*;

public class BucketArrayTest {
   public class BucketItemTest extends AbstractBucketItem {
      public int number;

      public BucketItemTest(int number) {
         this.number = number;
      }

      @Override
      public String toString() {
         return "BIT#" + number;
      }
   }

   BucketArray<BucketItemTest> instance;

   @Test
   public void testGetSize() {
      instance = new BucketArray<BucketItemTest>(-3, 3);
      assertEquals(0, instance.getSize());
      instance.add(-2, new BucketItemTest(0));
      assertEquals(1, instance.getSize());
      instance.add(2, new BucketItemTest(1));
      assertEquals(2, instance.getSize());
      instance.popFirst();
      assertEquals(1, instance.getSize());
      instance.popLast();
      assertEquals(0, instance.getSize());
   }

   @Test
   public void testGetBucketIndex() {
      instance = new BucketArray<BucketItemTest>(-5, 5);
      assertEquals(5, instance.getFirstBucketIndex());
      assertEquals(-5, instance.getLastBucketIndex());
      
      instance.add(-2, new BucketItemTest(0));
      assertEquals(-2, instance.getFirstBucketIndex());
      assertEquals(-2, instance.getLastBucketIndex());

      instance.add(2, new BucketItemTest(1));
      assertEquals(-2, instance.getFirstBucketIndex());
      assertEquals(2, instance.getLastBucketIndex());

      instance.add(1, new BucketItemTest(2));
      assertEquals(-2, instance.getFirstBucketIndex());
      assertEquals(2, instance.getLastBucketIndex());

      assertEquals(1, instance.popLast().number);
      assertEquals(-2, instance.getFirstBucketIndex());
      assertEquals(1, instance.getLastBucketIndex());

      assertEquals(0, instance.popFirst().number);
      assertEquals(1, instance.getFirstBucketIndex());
      assertEquals(1, instance.getLastBucketIndex());

      assertEquals(2, instance.pop(1).number);
      assertEquals(5, instance.getFirstBucketIndex());
      assertEquals(-5, instance.getLastBucketIndex());
   }

   @Test
   public void testAddRemove() {
      instance = new BucketArray<BucketItemTest>(-3, 3);
      BucketItemTest items[] = new BucketItemTest[10];
      for(int i = 0; i < items.length; i++) {
         items[i] = new BucketItemTest(i);
      }

      assertEquals(0, instance.add(-1, items[0]));
      assertEquals(1, instance.add(-1, items[1]));
      assertEquals(2, instance.add(-1, items[2]));
      assertEquals(3, instance.add(-1, items[3]));
      assertEquals(0, instance.add(2, items[4]));
      assertEquals(1, instance.add(2, items[5]));
      assertEquals(2, instance.add(2, items[6]));
      assertEquals(3, instance.add(2, items[7]));

      assertEquals(items[2], instance.remove(-1, 2));
      assertEquals(items[3], instance.pop(-1));
      assertEquals(items[5], instance.remove(2, 1));
      assertEquals(items[6], instance.pop(2));
   }

   @Test
   public void testPeekPop() {
      instance = new BucketArray<BucketItemTest>(-5, 5);
      instance.add(-3, new BucketItemTest(1));
      instance.add(-2, new BucketItemTest(2));
      instance.add(0, new BucketItemTest(3));
      instance.add(2, new BucketItemTest(4));
      instance.add(3, new BucketItemTest(5));

      assertEquals(1, instance.popFirst().number);
      assertEquals(4, instance.peek(2).number);
      assertEquals(-2, instance.getFirstBucketIndex());
      assertEquals(5, instance.popLast().number);
      assertEquals(2, instance.getLastBucketIndex());
      assertEquals(3, instance.pop(0).number);
      assertEquals(-2, instance.getFirstBucketIndex());
      assertEquals(2, instance.popFirst().number);
   }
}