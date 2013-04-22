/*
 * DvRlib - Container
 * Duncan van Roermund, 2013
 * DisjointSetForestTest.java
 */

package dvrlib.container;

import dvrlib.generic.Pair;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

public class DisjointSetForestTest {
   @Test
   public void testForests() {
      testForests(new Integer[][]{ });
      testForests(new Integer[][]{{ 1 }});
      testForests(new Integer[][]{{ 1, 2 }});
      testForests(new Integer[][]{{ 1 }, { 2 }});
      testForests(new Integer[][]{{ 1, 2 }, { 3, 4 }});
      testForests(new Integer[][]{{ 1, 2, 3 }});
      testForests(new Integer[][]{{ 1, 2, 3 }, { 4, 5, 6 }});
      testForests(new Integer[][]{{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }});
      testForests(new Integer[][]{{ 1, 2 }, { 3, 4, 5, 6 }, { 7 }, { 8, 9 }});
   }

   protected void testForests(Integer[][] es) {
      testForest(buildForest(new DisjointSetForest<Integer>() {
            @Override
            public Collection<Pair<HashSet<Integer>, Object>> retrieveSets() {
               Collection<Pair<HashSet<Integer>, Object>> ts = super.retrieveSets();
               for(Pair<HashSet<Integer>, Object> t : ts) {
                  assertNull(t.b);
               }
               return ts;
            }
         }, es), es);

      testForest(buildForest(new AbstractDisjointSetForest<Integer, Integer>() {
            @Override
            public boolean add(Integer e) {
               return add(e, e);
            }

            @Override
            protected Pair<HashSet<Integer>, Integer> merge(Pair<HashSet<Integer>, Integer> t1, Pair<HashSet<Integer>, Integer> t2) {
               t1.a.addAll(t2.a);
               t1.b += t2.b;
               return t1;
            }
            public Collection<Pair<HashSet<Integer>, Integer>> retrieveSets() {
               Collection<Pair<HashSet<Integer>, Integer>> ts = super.retrieveSets();
               for(Pair<HashSet<Integer>, Integer> t : ts) {
                  int s = 0;
                  for(Integer i : t.a) {
                     s += i;
                  }
                  assertEquals(t.b.intValue(), s);
               }
               return ts;
            }
         }, es), es);
   }

   protected AbstractDisjointSetForest<Integer, ?> buildForest(AbstractDisjointSetForest<Integer, ?> df, Integer[][] es) {
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

      return df;
   }

   protected void testForest(AbstractDisjointSetForest<Integer, ?> df, Integer[][] es) {
      for(int i = 0; i < es.length; i++) {
         Integer r = df.representative(es[i][0]);
         for(int j = 0; j < es[i].length; j++) {
            assertSetsMatch(df.retrieveSet(es[i][j]).a, es[i]);
            assertEquals(r, df.representative(es[i][j]));
         }
         assertTrue(df.retrieveSet(es[i][0]).a.contains(r));
      }
   }

   protected <E> void assertSetsMatch(HashSet<E> s, E[] es) {
      assertEquals(s.size(), es.length);
      for(E e : es) {
         assertTrue(s.contains(e));
      }
   }

   @Test
   public void testRemove() {
      AbstractDisjointSetForest<Integer, ?> df = buildForest(new DisjointSetForest<Integer>(), new Integer[][]{{ 1, 2, 3 }, { 4, 5 }, { 6 }, { 7, 8, 9 }});
      testForest(df, new Integer[][]{{ 1, 2, 3 }, { 4, 5 }, { 6 }, { 7, 8, 9 }});
      df.remove(4);
      testForest(df, new Integer[][]{{ 1, 2, 3 }, { 5 }, { 6 }, { 7, 8, 9 }});
      df.remove(5);
      testForest(df, new Integer[][]{{ 1, 2, 3 }, { 6 }, { 7, 8, 9 }});
      df.remove(8);
      testForest(df, new Integer[][]{{ 1, 2, 3 }, { 6 }, { 7, 9 }});
      df.remove(1);
      testForest(df, new Integer[][]{{ 2, 3 }, { 6 }, { 7, 9 }});
   }
}
