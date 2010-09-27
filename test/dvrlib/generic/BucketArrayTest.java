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