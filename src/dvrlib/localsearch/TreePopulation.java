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

import dvrlib.generic.IterableOnce;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TreePopulation<S extends Solution, E extends Comparable<E>> extends Population<S> {
   protected final Problem<S, E>          problem;
   protected final TreeMap<E, HashSet<S>> tree;
   protected final int                    sizeLim;

   protected int size = 0;

   public TreePopulation(Problem<S, E> problem, int sizeLim) {
      this.problem = problem;
      this.sizeLim = sizeLim;
      tree = new TreeMap<E, HashSet<S>>();
   }

   /**
    * Adds the given solution to this population if it is not already present.
    * If this population is full and the given solution is better than the the current worst, the worst solution is removed.
    * @return A boolean indicating whether the solution was actually added.
    * @see Population#add(Solution)
    */
   @Override
   public boolean add(S solution) {
      if(size >= sizeLim)
         popWorst();
      E eval = problem.evaluate(solution);
      if(!tree.containsKey(eval))
         tree.put(eval, new HashSet<S>());
      if(tree.get(eval).add(solution)) {
         size++;
         return true;
      }
      else
         return false;
   }

   /**
    * Removes all solutions from this population.
    * @see Population#clear()
    */
   @Override
   public void clear() {
      tree.clear();
      size = 0;
   }

   /**
    * Returns whether the given solution is part of this population.
    * @see Population#contains(Solution)
    */
   @Override
   public boolean contains(S solution) {
      E e = problem.evaluate(solution);
      return (tree.containsKey(e) && tree.get(e).contains(solution));
   }

   /**
    * Returns true if this collection contains no elements.
    */
   @Override
   public boolean isEmpty() {
      return (size == 0);
   }

   /**
    * Returns an iterator to the solutions of this population, ordered best to worst.
    */
   @Override
   public Iterator<S> iterator() {
      return new Iterator<S>() {
            protected Entry<E, HashSet<S>> e = tree.lastEntry();
            protected Iterator<S> it = e.getValue().iterator();

            public boolean hasNext() {
               return (it.hasNext() || tree.lowerEntry(e.getKey()) != null);
            }

            public S next() {
               if(!hasNext())
                  throw new java.util.NoSuchElementException();
               if(!it.hasNext()) {
                  e = tree.lowerEntry(e.getKey());
                  it = e.getValue().iterator();
               }
               return it.next();
            }

            public void remove() {
               it.remove();
               size--;
               if(e.getValue().isEmpty()) {
                  tree.remove(e.getKey());
                  e = tree.lowerEntry(e.getKey());
               }
            }
         };
   }

   /**
    * Returns but does not remove the best solution in this population.
    * @see Population#peekBest()
    */
   @Override
   public S peekBest() {
      return tree.lastEntry().getValue().iterator().next();
   }

   /**
    * Returns but does not remove the worst solution in this population.
    * @see Population#peekWorst()
    */
   @Override
   public S peekWorst() {
      return tree.firstEntry().getValue().iterator().next();
   }

   /**
    * Removes and returns the best solution in this population.
    * @see Population#popBest()
    */
   @Override
   public S popBest() {
      Entry<E, HashSet<S>> e = tree.lastEntry();
      if(e.getValue().size() <= 1)
         tree.remove(e.getKey());
      S s = e.getValue().iterator().next();
      e.getValue().remove(s);
      size--;
      return s;
   }

   /**
    * Removes and returns the worst solution in this population.
    * @see Population#popWorst()
    */
   @Override
   public S popWorst() {
      Entry<E, HashSet<S>> e = tree.firstEntry();
      if(e.getValue().size() <= 1)
         tree.remove(e.getKey());
      S s = e.getValue().iterator().next();
      e.getValue().remove(s);
      size--;
      return s;
   }

   /**
    * Returns the number of solutions in this population.
    * @see Population#size()
    */
   @Override
   public int size() {
      return size;
   }

   /**
    * Returns an array containing all of the elements in this collection.
    */
   @Override
   public Object[] toArray() {
      Object array[] = new Object[size()];
      int i = 0;
      for(S s : new IterableOnce<S>(iterator())) {
         array[i++] = s;
      }
      return array;
   }
   /**
    * Returns an array containing all of the elements in this collection; the runtime type of the returned array is that of the specified array.
    */
   @Override
   @SuppressWarnings("unchecked")
   public <T> T[] toArray(T[] a) {
      if(a.length < size())
         a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size());
      int i = 0;
      for(S s : new IterableOnce<S>(iterator())) {
         a[i++] = (T) s;
      }
      if(i < a.length - 1)
         a[i] = null;
      return a;
   }
}
