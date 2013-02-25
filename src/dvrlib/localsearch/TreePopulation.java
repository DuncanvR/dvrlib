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

public class TreePopulation<S extends Solution, E extends Comparable<E>> extends Population<S> {
   protected final HashSet<S>               keys      = new HashSet<S>();
   protected final TreeMap<E, ArrayList<S>> tree      = new TreeMap<E, ArrayList<S>>();
   protected final Problem<S, E>            problem;
   protected final int                      direction, size;

   public TreePopulation(Problem<S, E> problem, int size) {
      this.problem = problem;
      this.size    = size;
      direction    = LocalSearch.asNumber(problem.direction());
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
    * If this population is full and the given solution is better than the the current worst, the worst solution is removed.
    * @return A boolean indicating whether the solution was actually added.
    * @see Population#add(Solution)
    */
   @Override
   public boolean add(S solution) {
      if(!contains(solution)) {
         if(size() >= size) {
            if(problem.better(solution, peekWorst()))
               popWorst();
            else
               return false;
         }

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
    * Returns true if this collection contains no elements.
    */
   @Override
   public boolean isEmpty() {
      return keys.isEmpty();
   }

   /**
    * Returns an iterator to the solutions of this population, ordered best to worst.
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
    * Returns the number of solutions in this population.
    * @see Population#size()
    */
   @Override
   public int size() {
      return keys.size();
   }

   /**
    * Returns an array containing all of the elements in this collection.
    */
   @Override
   public Object[] toArray() {
      return keys.toArray();
   }
   /**
    * Returns an array containing all of the elements in this collection; the runtime type of the returned array is that of the specified array.
    */
   @Override
   public <T> T[] toArray(T[] a) {
      return keys.toArray(a);
   }
}
