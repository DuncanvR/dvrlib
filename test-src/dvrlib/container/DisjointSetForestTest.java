/*
 * DvRlib - Container
 * Copyright (C) Duncan van Roermund, 2013
 * DisjointSetForestTest.java
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dvrlib.container;

import dvrlib.generic.Pair;

import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

public class DisjointSetForestTest {
   protected class TestForest1 extends DisjointSetForest<Integer> {
      public TestForest1() { }

      public TestForest1(TestForest1 that) {
         super(that);
      }

      @Override
      public Collection<Pair<HashSet<Integer>, Object>> retrieveSets() {
         Collection<Pair<HashSet<Integer>, Object>> ts = super.retrieveSets();
         for(Pair<HashSet<Integer>, Object> t : ts) {
            assertNull(t.b);
         }
         return ts;
      }
   }
   protected class TestForest2 extends AbstractDisjointSetForest<Integer, Integer> {
      public TestForest2() { }

      public TestForest2(TestForest2 that) {
         super(that);
      }

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
   }

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
      TestForest1 tf1 = new TestForest1();
      buildForest(tf1, es);
      testForest(tf1, es);
      testForest(new TestForest1(tf1), es);

      TestForest2 tf2 = new TestForest2();
      buildForest(tf2, es);
      testForest(tf2, es);
      testForest(new TestForest2(tf2), es);
   }

   protected void buildForest(AbstractDisjointSetForest<Integer, ?> df, Integer[][] es) {
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
      AbstractDisjointSetForest<Integer, Object> df = new DisjointSetForest<Integer>();
      buildForest(df, new Integer[][]{{ 1, 2, 3 }, { 4, 5 }, { 6 }, { 7, 8, 9 }});
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
