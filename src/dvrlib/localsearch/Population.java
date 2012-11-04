/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * Population.java
 */

package dvrlib.localsearch;

public interface Population<S extends Solution> extends Iterable<S> {
   /**
    * Adds the given solution to this population if it is not already present.
    * If this population is full and the given solution is better than the the current worst, the worst solution is removed.
    * @return A boolean indicating whether the solution was actually added.
    */
   public boolean add(S solution);

   /**
    * Removes all solutions from this population.
    */
   public void clear();

   /**
    * Returns whether the given solution is part of this population.
    */
   public boolean contains(S solution);

   /**
    * Returns but does not remove the best solution in this population.
    */
   public S peekBest();

   /**
    * Returns but does not remove a random solution from this population.
    */
   public S peekRandom();

   /**
    * Returns but does not remove the worst solution in this population.
    */
   public S peekWorst();

   /**
    * Removes and returns the worst solution in this population.
    */
   public S popWorst();

   /**
    * Returns the number of solutions in this population.
    */
   public int size();
}
