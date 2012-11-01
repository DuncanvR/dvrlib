/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * TreePopulation.java
 */

package dvrlib.localsearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

public class TreePopulation<S extends Solution, E extends Comparable<E>> implements Population<S> {
   protected final HashSet<S>               keys      = new HashSet<S>();
   protected final TreeMap<E, ArrayList<S>> tree      = new TreeMap<E, ArrayList<S>>();
   protected final Problem<S, E>            problem;
   protected final int                      direction;

   public TreePopulation(Problem<S, E> problem) {
      this.problem = problem;
      direction    = problem.direction();
   }

   /**
    * Returns the value of the given solution.
    */
   public E value(S solution) {
      return problem.evaluate(solution);
   }

   /**
    * Returns an iterator to the lists inside the tree.
    */
   private final Iterator<ArrayList<S>> treeIterator() {
      if(direction > 0)
         return tree.descendingMap().values().iterator();
      else
         return tree.values().iterator();
   }

   private final ArrayList<S> getList(int direction) {
      ArrayList<S> list = (direction > 0 ? tree.lastEntry() : tree.firstEntry()).getValue();
      assert !list.isEmpty();
      return list;
   }

   /**
    * Adds the given solution to this population if it is not already present.
    * @return A boolean indicating whether the solution was actually added.
    * @see Population#add(Solution)
    */
   @Override
   public boolean add(S solution) {
      if(!contains(solution)) {
         keys.add(solution);
         E e = value(solution);
         if(tree.get(e) == null)
            tree.put(e, new ArrayList<S>());
         tree.get(e).add(solution);
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
      return keys.contains(solution);
   }

   /**
    * Returns an iterator to the solutions of this population, ordered best to worst.
    * @see Iterable#iterator()
    */
   @Override
   public Iterator<S> iterator() {
      return new Iterator<S>() {
         protected Iterator<ArrayList<S>> ti = treeIterator();
         protected Iterator<S>            li = null;

         @Override
         public boolean hasNext() {
            while(li == null || !li.hasNext()) {
               if(ti.hasNext())
                  li = ti.next().iterator();
               else
                  return false;
            }
            return true;
         }

         @Override
         public S next() {
            if(!hasNext())
               throw new java.util.NoSuchElementException();
            return li.next();
         }

         @Override
         public void remove() {
            hasNext();
            li.remove();
         }
      };
   }

   /**
    * Returns but does not remove the best solution in this population.
    * @see Population#peekBest()
    */
   @Override
   public S peekBest() {
      return getList(direction).get(0);
   }

   /**
    * Returns but does not remove a random solution from this population.
    * @see Population#peekRandom()
    */
   @Override
   public S peekRandom() {
      throw new UnsupportedOperationException();
   }

   /**
    * Returns but does not remove the worst solution in this population.
    * @see Population#peekWorst()
    */
   @Override
   public S peekWorst() {
      return getList(-direction).get(0);
   }

   /**
    * Removes and returns the worst solution in this population.
    * @see Population#popWorst()
    */
   @Override
   public S popWorst() {
      ArrayList<S> worstList = getList(-direction);
      S worst = worstList.remove(0);
      keys.remove(worst);
      if(worstList.isEmpty()) {
         if(direction > 0)
            tree.pollFirstEntry();
         else
            tree.pollLastEntry();
      }
      return worst;
   }

   /**
    * Replaces the worst solution with the given one and returns the removed worst,
    *  but only if the given solution is not yet present in this population.
    * @return The replaced solution or <tt>null</tt> if the given solution was already present.
    * @see Population#replaceWorst(Solution)
    */
   @Override
   public S replaceWorst(S solution) {
      if(!contains(solution)) {
         add(solution);
         return popWorst();
      }
      else
         return null;
   }

   /**
    * Returns the number of solutions in this population.
    * @see Population#size()
    */
   @Override
   public int size() {
      return keys.size();
   }
}
