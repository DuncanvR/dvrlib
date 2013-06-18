/*
 * DvRlib - Generic
 * Duncan van Roermund, 2013
 * IterableTest.java
 */

package dvrlib.generic;

import java.util.Iterator;

import org.junit.Test;
import static org.junit.Assert.*;

public class IterableTest {
   protected final Integer[] es = new Integer[100];

   public IterableTest() {
      for(int i = 0; i < es.length; i++) {
         es[i] = i;
      }
   }

   @Test
   @SuppressWarnings("unchecked")
   public void testIterables() {
      // Test IterableArray
      Iterator<Integer> it = new IterableArray<Integer>(es).iterator();
      testIterator(it);
      assertFalse(it.hasNext());

      // Test IterableOnce
      Iterable<Integer> ibl = new IterableOnce<Integer>(new IterableArray<Integer>(es).iterator());
      it = ibl.iterator();
      assertNotNull(it);
      testIterator(it);
      assertFalse(it.hasNext());
      assertNull(ibl.iterator());

      // Test CombinedIterable
      CombinedIterable<Integer> cibl = new CombinedIterable<Integer>();
      assertFalse(cibl.iterator().hasNext());

      cibl.combine(new CombinedIterable<Integer>());
      assertFalse(cibl.iterator().hasNext());

      cibl.combine(new IterableOnce<Integer>(new IterableArray<Integer>(es).iterator()));
      it = cibl.iterator();
      testIterator(it);
      assertFalse(it.hasNext());

      cibl.combine(new IterableArray<Integer>(es));
      it = cibl.iterator();
      testIterator(it);
      assertFalse(it.hasNext());

      cibl = new CombinedIterable<Integer>(new Iterable[]{ new IterableArray<Integer>(es)
                                                         , new IterableOnce<Integer>(new IterableArray<Integer>(es).iterator())
                                                         , new IterableArray<Integer>(es)
                                                         , new IterableOnce<Integer>(new IterableArray<Integer>(es).iterator())
                                                         });
      it = cibl.iterator();
      for(int i = 0; i < 4; i++) {
         testIterator(it);
      }
      assertFalse(it.hasNext());
      it = cibl.iterator();
      for(int i = 0; i < 2; i++) {
         testIterator(it);
      }
      assertFalse(it.hasNext());
   }

   protected void testIterator(Iterator<Integer> it) {
      assertNotNull(it);
      for(int i = 0; i < es.length; i++) {
         assertTrue(it.hasNext());
         assertEquals(es[i], it.next());
      }
   }
}
