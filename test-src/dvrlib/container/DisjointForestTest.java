/*
 * DvRLib - Container
 * Duncan van Roermund, 2013
 * DisjointForestTest.java
 */

package dvrlib.container;

import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

public class DisjointForestTest {
   @Test
   public void testForest() {
      Integer[][] es = new Integer[][]{{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8 }};
      DisjointForest<Integer> df = new DisjointForest<Integer>();
      assertTrue(df.isEmpty());
      assertEquals(df.size(), 0);

      int c = 0;
      for(int i = 0; i < es.length; i++) {
         for(int j = 0; j < es[i].length; j++) {
            df.add(es[i][j]);
            assertEquals(df.size(), ++c);
         }
      }
      assertFalse(df.isEmpty());

      for(int i = 0; i < es.length; i++) {
         for(int j = 1; j < es[i].length; j++) {
            df.union(es[i][0], es[i][j]);
            assertEquals(df.size(), c);
         }
      }

      for(int i = 0; i < es.length; i++) {
         for(int j = 0; j < es[i].length; j++) {
            assertSetsMatch(df.retrieveSet(es[i][j]).a, es[i]);
         }
      }
   }

   protected void assertSetsMatch(HashSet<Integer> s, Integer[] es) {
      assertEquals(s.size(), es.length);
      for(int i = 0; i < es.length; i++) {
         assertTrue(s.contains(es[i]));
      }
   }
}
