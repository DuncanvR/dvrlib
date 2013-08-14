/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2010-2012
 * WeightedTreePopulation.java
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

package dvrlib.localsearch;

import dvrlib.container.WeightedTree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

public class WeightedTreePopulation<S extends Solution> extends GeneticPopulation<S> {
   protected static final Random Rnd = new Random();

   protected final HashMap<S, Double>   keys    = new HashMap<S, Double>();
   protected final GeneticProblem<S, ?> problem;
   protected final WeightedTree<S>      tree    = new WeightedTree<S>();
   protected final int                  capacity;

   public WeightedTreePopulation(GeneticProblem<S, ?> problem, int capacity) {
      this.problem  = problem;
      this.capacity = capacity;
   }

   /**
    * Adds the given solution to this population.
    * If the given solution is already present, it's weight is updated accordingly.
    * If this population is full and the given solution is better than the the current worst, the worst solution is removed.
    * @return A boolean indicating whether this population was modified in any way.
    * @see Population#add(Solution)
    */
   @Override
   public boolean add(S solution) {
      if(contains(solution)) {
         double newKey = problem.weight(solution),
                oldKey = keys.put(solution, newKey);
         if(newKey != oldKey && tree.remove(oldKey, solution)) {
            tree.add(newKey, solution);
            return true;
         }
      }
      else {
         if(size() >= capacity) {
            if(problem.better(solution, peekWorst()))
               popWorst();
            else
               return false;
         }
         double key = problem.weight(solution);
         keys.put(solution, key);
         tree.add(key, solution);
         return true;
      }
      return false;
   }

   /**
    * Removes all solutions from this population.
    * @see Population#clear()
    */
   @Override
   public void clear() {
      keys.clear();
      tree.clear();
   }

   /**
    * Returns whether the given solution is part of this population.
    * @see Population#contains(Solution)
    */
   @Override
   public boolean contains(S solution) {
      return keys.containsKey(solution);
   }

   /**
    * Returns true if this collection contains no elements.
    */
   @Override
   public boolean isEmpty() {
      return keys.isEmpty();
   }

   /**
    * Returns an iterator to the solutions of this population.
    * @see dvrlib.container.WeightedTree#iterator()
    */
   @Override
   public Iterator<S> iterator() {
      return tree.iterator();
   }

   /**
    * Returns but does not remove the best solution in this population.
    * @see Population#peekBest()
    */
   @Override
   public S peekBest() {
      return tree.peekMax().b;
   }

   /**
    * Returns but does not remove a random solution from this population.
    * The chance a solution is chosen is equal for all solutions.
    */
   @Override
   public S peekRandom() {
      return tree.getIndexed(Rnd.nextInt(tree.size())).b;
   }

   /**
    * Returns but does not remove a random solution from this population.
    * The chance a solution is chosen is directly proportionate to its fitness score.
    */
   @Override
   public S peekWeighted() {
      return tree.getWeighted(Rnd.nextDouble()).b;
   }

   /**
    * Returns but does not remove the worst solution in this population.
    * @see Population#peekWorst()
    */
   @Override
   public S peekWorst() {
      dvrlib.generic.Pair<?, S> p = tree.peekMin();
      return (p == null ? null : p.b);
   }

   /**
    * Removes and returns the best solution in this population.
    * @see Population#popBest()
    */
   @Override
   public S popBest() {
      S best = tree.popMax().b;
      keys.remove(best);
      return best;
   }

   /**
    * Removes and returns the worst solution in this population.
    * @see Population#popWorst()
    */
   @Override
   public S popWorst() {
      S worst = tree.popMin().b;
      keys.remove(worst);
      return worst;
   }

   /**
    * Clears this population, retaining only the given number of best solutions.
    */
   @Override
   public void retainBest(int n) {
      tree.retainBest(n);
      Iterator<Entry<S, Double>> it = keys.entrySet().iterator();
      while(it.hasNext()) {
         Entry<S, Double> e = it.next();
         if(!tree.contains(e.getValue(), e.getKey()))
            it.remove();
      }
   }

   /**
    * Returns the number of solutions in this population.
    * @see Population#size()
    */
   @Override
   public int size() {
      assert keys.size() == tree.size();
      return keys.size();
   }

   /**
    * Returns an array containing all of the elements in this collection.
    */
   @Override
   public Object[] toArray() {
      return tree.toArray();
   }
   /**
    * Returns an array containing all of the elements in this collection; the runtime type of the returned array is that of the specified array.
    */
   @Override
   public <T> T[] toArray(T[] a) {
      return tree.toArray(a);
   }
}
