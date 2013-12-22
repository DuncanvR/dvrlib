/*
 * DvRlib - Algorithm
 * Copyright (C) Duncan van Roermund, 2013
 * Knapsack.java
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

import java.util.HashSet;
import java.util.TreeMap;

public class Knapsack<O extends KnapsackObject> {
   protected final static double IllegalValue = -Double.MAX_VALUE;

   protected final O                        objects[];
   protected final TreeMap<Integer, Double> entries[];
   protected final int                      capacityLB,
                                            capacityUB;
   protected final double                   totalValue;

   protected boolean solved = false;
   protected Integer bestC  = null;

   /**
    * Knapsack constructor.
    */
   @SuppressWarnings({"unchecked"})
   public Knapsack(O objects[], int capacity) {
      this.objects = objects;
      entries = (TreeMap<Integer, Double>[]) new TreeMap[objects.length];
      double tV = 0d;
      int tW = 0, maxW = 0;
      for(int i = 0; i < objects.length; i++) {
         entries[i] = new TreeMap<Integer, Double>();
         assert (objects[i].value() > 0d) : "Knapsack does not support negative values";
         tV += objects[i].value();
         assert (objects[i].weight() > 0) : "Knapsack does not support negative weights";
         tW += objects[i].weight();
         maxW = Math.max(maxW, objects[i].weight());
      }
      capacityUB = Math.min(tW, capacity) - 1;
      capacityLB = Math.max(0, capacityUB - maxW);
      totalValue = tV;
   }

   /**
    * Solves this knapsack problem and returns the value of the optimal solution.
    * @return The combined value of the optimal set of objects with combined weight within the set capacity.
    */
   public double solve() {
      if(!solved && capacityUB >= 0) {
         solved = true;
         double bestValue = IllegalValue;
         for(int c = capacityUB; c >= capacityLB; c--) {
            double v = entry(c, objects.length - 1);
            if(v > bestValue) {
               if(v == totalValue) {
                  bestC = -1;
                  break;
               }
               bestValue = v;
               bestC = c;
            }
         }
      }
      if(bestC == null)
         return 0d;
      else if(bestC == -1)
         return totalValue;
      else
         return entries[objects.length - 1].get(bestC).doubleValue();
   }

   protected double entry(int c, int i) {
      if(c == -1)
         return 0d;
      if(c < 0 || i < 0)
         return IllegalValue;

      if(!entries[i].containsKey(c))
         entries[i].put(c, Math.max(entry(c, i - 1), entry(c - objects[i].weight(), i - 1) + objects[i].value()));
      return entries[i].get(c);
   }

   /**
    * Returns the optimal solution to selecting objects from the given set, with the given capacity.
    * @return The set containing the objects of the optimal solution.
    */
   public HashSet<O> solution() {
      if(!solved)
         solve();
      HashSet<O> knapsack = new HashSet<O>();
      if(bestC != null) {
         if(bestC == -1)
            for(O o : objects) {
               knapsack.add(o);
            }
         else
            fill(knapsack, bestC, objects.length - 1);
      }
      return knapsack;
   }

   protected void fill(HashSet<O> knapsack, int c, int i) {
      if(c >= 0) {
         assert (i >= 0);
         O o = objects[i];
         if(entry(c, i - 1) >= entry(c - o.weight(), i - 1) + o.value())
            fill(knapsack, c, i - 1);
         else {
            knapsack.add(o);
            fill(knapsack, c - o.weight(), i - 1);
         }
      }
   }
}
