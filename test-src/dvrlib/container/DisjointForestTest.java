/*
 * DvRLib - Container
 * Duncan van Roermund, 2013
 * DisjointForestTest.java
 */

package dvrlib.container;

import dvrlib.generic.Tuple;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

public class DisjointForestTest {
   @Test
   public void testForests() {
      testForest(new Integer[][]{{ }});
      testForest(new Integer[][]{{ 1 }});
      testForest(new Integer[][]{{ 1, 2 }});
      testForest(new Integer[][]{{ 1 }, { 2 }});
      testForest(new Integer[][]{{ 1, 2 }, { 3, 4 }});
      testForest(new Integer[][]{{ 1, 2, 3 }});
      testForest(new Integer[][]{{ 1, 2, 3 }, { 4, 5, 6 }});
      testForest(new Integer[][]{{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }});
      testForest(new Integer[][]{{ 1, 2 }, { 3, 4, 5, 6 }, { 7 }, { 8, 9 }});
   }

   protected void testForest(Integer[][] es) {
      testForest(new DisjointForest<Integer>() {
            @Override
            public Collection<Tuple<HashSet<Integer>, Object>> retrieveSets() {
               Collection<Tuple<HashSet<Integer>, Object>> ts = super.retrieveSets();
               for(Tuple<HashSet<Integer>, Object> t : ts) {
                  assertNull(t.b);
               }
               return ts;
            }
         }, es);

      testForest(new AbstractDisjointForest<Integer, Integer>() {
            @Override
            public boolean add(Integer e) {
               return add(e, e);
            }

            @Override
            protected Tuple<HashSet<Integer>, Integer> merge(Tuple<HashSet<Integer>, Integer> t1, Tuple<HashSet<Integer>, Integer> t2) {
               t1.a.addAll(t2.a);
               t1.b += t2.b;
               return t1;
            }
            public Collection<Tuple<HashSet<Integer>, Integer>> retrieveSets() {
               Collection<Tuple<HashSet<Integer>, Integer>> ts = super.retrieveSets();
               for(Tuple<HashSet<Integer>, Integer> t : ts) {
                  int s = 0;
                  for(Integer i : t.a) {
                     s += i;
                  }
                  assertEquals(t.b.intValue(), s);
               }
               return ts;
            }
         }, es);
   }

   protected void testForest(AbstractDisjointForest<Integer, ?> df, Integer[][] es) {
      assertTrue(df.isEmpty());
      assertEquals(df.size(), 0);

      int c = 0;
      for(int i = 0; i < es.length; i++) {
         for(int j = 0; j < es[i].length; j++) {
            df.add(es[i][j]);
            assertEquals(df.size(), ++c);
         }
      }
      if(c > 0)
         assertFalse(df.isEmpty());

      for(int i = 0; i < es.length; i++) {
         for(int j = 1; j < es[i].length; j++) {
            assertEquals(df.representative(es[i][j]), es[i][j]);
            df.union(es[i][0], es[i][j]);
            assertEquals(df.representative(es[i][j]), df.representative(es[i][0]));
            assertEquals(df.size(), c);
         }
      }

      for(int i = 0; i < es.length; i++) {
         for(int j = 0; j < es[i].length; j++) {
            assertSetsMatch(df.retrieveSet(es[i][j]).a, es[i]);
         }
      }

      df.retrieveSets();
   }

   protected <E> void assertSetsMatch(HashSet<E> s, E[] es) {
      assertEquals(s.size(), es.length);
      for(E e : es) {
         assertTrue(s.contains(e));
      }
   }
}
