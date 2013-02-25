/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * Population.java
 */

package dvrlib.localsearch;

import java.util.Collection;

public abstract class Population<S extends Solution> implements Collection<S> {
   /**
    * Iterates over the given solutions, adding them to this population if they are not already present.
    * If this population is full, each solution is compared to the current worst, replacing it if better.
    * @return A boolean indicating whether this population was changed.
    */
   @Override
   public boolean addAll(Collection<? extends S> solutions) {
      boolean changed = false;
      for(S s : solutions) {
         changed |= add(s);
      }
      return changed;
   }

   /**
    * Returns true if this collection contains the specified element.
    * @see Population#contains(S)
    */
   @Override
   public boolean contains(Object o) {
      return (o instanceof Solution ? contains((Solution) o) : false);
   }
   /**
    * Returns whether the given solution is part of this population.
    */
   public abstract boolean contains(S solution);
   /**
    * Returns true if this collection contains all of the elements in the specified collection.
    */
   @Override
   public boolean containsAll(Collection<?> c) {
      for(Object o : c) {
         if(!contains(o))
            return false;
      }
      return true;
   }

   /**
    * Returns but does not remove the best solution in this population.
    */
   public abstract S peekBest();
   /**
    * Returns but does not remove the worst solution in this population.
    */
   public abstract S peekWorst();
   /**
    * Removes and returns the worst solution in this population.
    */
   public abstract S popWorst();

   /**
    * Unsupported operation.
    */
   @Override
   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }
   /**
    * Unsupported operation.
    */
   @Override
   public boolean removeAll(Collection<?> c) {
      throw new UnsupportedOperationException();
   }
   /**
    * Unsupported operation.
    */
   @Override
   public boolean retainAll(Collection<?> c) {
      throw new UnsupportedOperationException();
   }
}
