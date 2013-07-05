/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2010-2013
 * TreePopulation.java
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

import java.util.Iterator;
import java.util.TreeSet;

public class TreePopulation<S extends Solution> extends Population<S> {
   protected final TreeSet<S>    tree;
   protected final int           sizeLim;

   public TreePopulation(Problem<S, ?> problem, int sizeLim) {
      this.sizeLim = sizeLim;
      tree = new TreeSet<S>(problem);
   }

   /**
    * Adds the given solution to this population if it is not already present.
    * If this population is full and the given solution is better than the the current worst, the worst solution is removed.
    * @return A boolean indicating whether the solution was actually added.
    * @see Population#add(Solution)
    */
   @Override
   public boolean add(S solution) {
      return tree.add(solution);
   }

   /**
    * Removes all solutions from this population.
    * @see Population#clear()
    */
   @Override
   public void clear() {
      tree.clear();
   }

   /**
    * Returns whether the given solution is part of this population.
    * @see Population#contains(Solution)
    */
   @Override
   public boolean contains(S solution) {
      return tree.contains(solution);
   }

   /**
    * Returns true if this collection contains no elements.
    */
   @Override
   public boolean isEmpty() {
      return tree.isEmpty();
   }

   /**
    * Returns an iterator to the solutions of this population, ordered best to worst.
    */
   @Override
   public Iterator<S> iterator() {
      return tree.descendingIterator();
   }

   /**
    * Returns but does not remove the best solution in this population.
    * @see Population#peekBest()
    */
   @Override
   public S peekBest() {
      return tree.last();
   }

   /**
    * Returns but does not remove the worst solution in this population.
    * @see Population#peekWorst()
    */
   @Override
   public S peekWorst() {
      return tree.first();
   }

   /**
    * Removes and returns the best solution in this population.
    * @see Population#popBest()
    */
   @Override
   public S popBest() {
      return tree.pollLast();
   }

   /**
    * Removes and returns the worst solution in this population.
    * @see Population#popWorst()
    */
   @Override
   public S popWorst() {
      return tree.pollFirst();
   }

   /**
    * Returns the number of solutions in this population.
    * @see Population#size()
    */
   @Override
   public int size() {
      return tree.size();
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
