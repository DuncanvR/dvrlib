/*
 * DvRLib - Algorithm
 * Duncan van Roermund, 2013
 * Knapsack.java
 */

package dvrlib.algorithm;

import java.util.Collection;
import java.util.HashSet;
import java.util.TreeMap;

public class Knapsack<O extends KnapsackObject> {
   protected final O      objects[];
   protected final double entries[][];

   protected int bestC = -1;

   /**
    * Knapsack constructor.
    */
   @SuppressWarnings({"unchecked"})
   public Knapsack(O objects[], int capacity) {
      this.objects = objects;
      entries = new double[capacity][objects.length];
   }

   /**
    * Solves this knapsack problem and returns the value of the optimal solution.
    * @return The combined value of the optimal set of objects selected within the set capacity.
    */
   public double solve() {
      if(bestC < 0) {
         double bestProfit = Double.MIN_VALUE;
         for(int c = 0; c < entries.length; c++) {
            double p = entry(c, objects.length - 1);
            if(p > bestProfit) {
               bestC      = c;
               bestProfit = p;
            }
         }
      }
      if(bestC >= 0)
         return entries[bestC][objects.length - 1];
      else
         return 0d;
   }

   protected double entry(int c, int i) {
      if(c == -1)
         return 0;
      if(c < 0 || i < 0)
         return -Double.MAX_VALUE;

      if(entries[c][i] <= 0)
         entries[c][i] = Math.max(entry(c, i - 1), entry(c - objects[i].weight(), i - 1) + objects[i].value());
      return entries[c][i];
   }

   /**
    * Returns the optimal solution to selecting objects from the given set, with the given capacity.
    * @return The set containing the objects of the optimal solution.
    */
   public HashSet<O> solution() {
      HashSet<O> knapsack = new HashSet<O>();
      fill(knapsack, bestC, objects.length - 1);
      return knapsack;
   }

   protected void fill(Collection<O> knapsack, int c, int i) {
      if(c >= 0 && i >= 0) {
         O ko = objects[i];
         if(entry(c, i - 1) >= entry(c - ko.weight(), i - 1) + ko.value())
            fill(knapsack, c, i - 1);
         else {
            knapsack.add(ko);
            fill(knapsack, c - ko.weight(), i - 1);
         }
      }
   }
}
