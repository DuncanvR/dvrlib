/*
 * DvRlib - Algorithm
 * Copyright (C) Duncan van Roermund, 2013
 * KnapsackTest.java
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

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class KnapsackTest {
   protected class KnapsackTestObject implements KnapsackObject {
      protected double value;
      protected int    weight;

      public KnapsackTestObject(double value, int weight) {
         this.value  = value;
         this.weight = weight;
      }

      @Override
      public double value() {
         return value;
      }

      @Override
      public int weight() {
         return weight;
      }
   }

   @Test
   public void testSolve() {
      ArrayList<KnapsackTestObject> objects = new ArrayList<KnapsackTestObject>();
      objects.add(new KnapsackTestObject(12d, 8));
      testSolve(objects, 0, new boolean[] { false },  0d);
      testSolve(objects, 1, new boolean[] { false },  0d);
      testSolve(objects, 7, new boolean[] { false },  0d);
      testSolve(objects, 8, new boolean[] {  true }, 12d);
      testSolve(objects, 9, new boolean[] {  true }, 12d);

      objects.add(new KnapsackTestObject(11d, 8));
      testSolve(objects,  0, new boolean[] { false, false },  0d);
      testSolve(objects,  1, new boolean[] { false, false },  0d);
      testSolve(objects,  7, new boolean[] { false, false },  0d);
      testSolve(objects,  8, new boolean[] {  true, false }, 12d);
      testSolve(objects,  9, new boolean[] {  true, false }, 12d);
      testSolve(objects, 15, new boolean[] {  true, false }, 12d);
      testSolve(objects, 16, new boolean[] {  true,  true }, 23d);
      testSolve(objects, 17, new boolean[] {  true,  true }, 23d);

      objects.add(new KnapsackTestObject(8d, 6));
      testSolve(objects,  0, new boolean[] { false, false, false },  0d);
      testSolve(objects,  1, new boolean[] { false, false, false },  0d);
      testSolve(objects,  5, new boolean[] { false, false, false },  0d);
      testSolve(objects,  6, new boolean[] { false, false,  true },  8d);
      testSolve(objects,  7, new boolean[] { false, false,  true },  8d);
      testSolve(objects,  8, new boolean[] {  true, false, false }, 12d);
      testSolve(objects,  9, new boolean[] {  true, false, false }, 12d);
      testSolve(objects, 13, new boolean[] {  true, false, false }, 12d);
      testSolve(objects, 14, new boolean[] {  true, false,  true }, 20d);
      testSolve(objects, 15, new boolean[] {  true, false,  true }, 20d);
      testSolve(objects, 16, new boolean[] {  true,  true, false }, 23d);
      testSolve(objects, 17, new boolean[] {  true,  true, false }, 23d);
      testSolve(objects, 21, new boolean[] {  true,  true, false }, 23d);
      testSolve(objects, 22, new boolean[] {  true,  true,  true }, 31d);
      testSolve(objects, 23, new boolean[] {  true,  true,  true }, 31d);

      objects.add(new KnapsackTestObject(6d, 5));
      testSolve(objects,  0, new boolean[] { false, false, false, false },  0d);
      testSolve(objects,  1, new boolean[] { false, false, false, false },  0d);
      testSolve(objects,  4, new boolean[] { false, false, false, false },  0d);
      testSolve(objects,  5, new boolean[] { false, false, false,  true },  6d);
      testSolve(objects,  6, new boolean[] { false, false,  true, false },  8d);
      testSolve(objects,  7, new boolean[] { false, false,  true, false },  8d);
      testSolve(objects,  8, new boolean[] {  true, false, false, false }, 12d);
      testSolve(objects,  9, new boolean[] {  true, false, false, false }, 12d);
      testSolve(objects, 10, new boolean[] {  true, false, false, false }, 12d);
      testSolve(objects, 11, new boolean[] { false, false,  true,  true }, 14d);
      testSolve(objects, 12, new boolean[] { false, false,  true,  true }, 14d);
      testSolve(objects, 13, new boolean[] {  true, false, false,  true }, 18d);
      testSolve(objects, 14, new boolean[] {  true, false,  true, false }, 20d);
      testSolve(objects, 15, new boolean[] {  true, false,  true, false }, 20d);
      testSolve(objects, 16, new boolean[] {  true,  true, false, false }, 23d);
      testSolve(objects, 17, new boolean[] {  true,  true, false, false }, 23d);
      testSolve(objects, 18, new boolean[] {  true,  true, false, false }, 23d);
      testSolve(objects, 19, new boolean[] {  true, false,  true,  true }, 26d);
      testSolve(objects, 20, new boolean[] {  true, false,  true,  true }, 26d);
      testSolve(objects, 21, new boolean[] {  true,  true, false,  true }, 29d);
      testSolve(objects, 22, new boolean[] {  true,  true,  true, false }, 31d);
      testSolve(objects, 23, new boolean[] {  true,  true,  true, false }, 31d);
      testSolve(objects, 26, new boolean[] {  true,  true,  true, false }, 31d);
      testSolve(objects, 27, new boolean[] {  true,  true,  true,  true }, 37d);
      testSolve(objects, 99, new boolean[] {  true,  true,  true,  true }, 37d);
   }

   protected void testSolve(ArrayList<KnapsackTestObject> objects, int capacity, boolean inclusions[], double profit) {
      Knapsack<KnapsackTestObject> k = new Knapsack<KnapsackTestObject>(objects.toArray(new KnapsackTestObject[0]), capacity);
      double p = k.solve();
      assertEquals(profit, p, 1e-9);
      java.util.HashSet<KnapsackTestObject> s = k.solution();
      for(int i = 0; i < inclusions.length; i++) {
         assertEquals(inclusions[i], s.contains(objects.get(i)));
      }
   }
}
