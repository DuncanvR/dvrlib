/*
 * DvRlib - Algorithm
 * Copyright (C) Duncan van Roermund, 2013
 * SubSetsTest.java
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

package dvrlib.algorithm;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;
import static org.junit.Assert.*;

public class SubSetsTest {
   @Test
   public void testPermutations() {
      testPermutations(new Object[]{ });
      testPermutations(new Object[]{ new Object() });
      testPermutations(new Object[]{ new Object(), new Object() });
      testPermutations(new Object[]{ new Object(), new Object(), new Object() });
      testPermutations(new Object[]{ new Object(), new Object(), new Object(), new Object() });
   }

   protected void testPermutations(Object arr[]) {
      LinkedList<Object[]> lst = SubSets.permutations(arr);
      int n = simpleFactorial(arr.length - 1);

      if(arr.length > 0) {
         assertEquals(n * arr.length, lst.size());
         HashMap<Object, Integer[]> map = new HashMap<Object, Integer[]>();
         for(Object o : arr) {
            Integer is[] = new Integer[arr.length];
            for(int i = 0; i < is.length; i++) {
               is[i] = 0;
            }
            map.put(o, is);
         }
         for(Object[] os : lst) {
            for(int i = 0; i < os.length; i++) {
               map.get(os[i])[i]++;
               for(int j = i + 1; j < os.length; j++) {
                  assertNotEquals(os[i], os[j]);
               }
            }
         }
         for(Integer[] is : map.values()) {
            for(Integer i : is) {
               assertEquals(n, i.intValue());
            }
         }
      }
      else
         assertEquals(1, lst.size());
   }

   @Test
   public void testSubSets() {
      testSubSets(new Object[]{ });
      testSubSets(new Object[]{ new Object() });
      testSubSets(new Object[]{ new Object(), new Object() });
      testSubSets(new Object[]{ new Object(), new Object(), new Object() });
      testSubSets(new Object[]{ new Object(), new Object(), new Object(), new Object() });
   }

   protected void testSubSets(Object arr[]) {
      LinkedList<Object[]> lst = SubSets.subSets(arr);

      if(arr.length > 0) {
         HashMap<Object, Integer[]> map = new HashMap<Object, Integer[]>();
         for(Object o : arr) {
            Integer is[] = new Integer[arr.length];
            for(int i = 0; i < is.length; i++) {
               is[i] = 0;
            }
            map.put(o, is);
         }
         for(Object os[] : lst) {
            for(int i = 0; i < os.length; i++) {
               map.get(os[i])[i]++;
               for(int j = i + 1; j < os.length; j++) {
                  assertNotEquals(os[i], os[j]);
               }
            }
         }
         for(int i = 0; i < arr.length - 1; i++) {
            Integer is[] = map.get(arr[i]),
                    js[] = map.get(arr[i + 1]);
            assertEquals(is[0] / 2, js[0].intValue());
            for(int j = 1; j < is.length; j++) {
               assertEquals((is[j - 1] + is[j]) / 2, js[j].intValue());
            }
         }
         Integer is[] = map.get(arr[0]);
         assertEquals((int) Math.round(Math.pow(2, arr.length - 1)), is[0].intValue());
         for(int i = 1; i < is.length; i++) {
            assertEquals(0, is[i].intValue());
         }
      }
      else
         assertEquals(0, lst.size());
   }

   protected int simpleFactorial(int x) {
      return (x <= 1 ? 1 : x * simpleFactorial(x - 1));
   }
}
