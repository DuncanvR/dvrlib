/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * Population.java
 */

package dvrlib.localsearch;

public interface Population<S extends Solution> extends Iterable<S> {
   /**
    * Adds the given key, solution combination to this population.
    */
   public void add(S solution);

   /**
    * Removes all solutions from this population.
    */
   public void clear();

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
    * If the given solution is better than the worst solution in this population,
    *    replaces the worst solution with the given one and returns the removed worst.
    * Otherwise, nothing is changed and <tt>null</tt> is returned.
    */
   public S replaceWorst(S solution);

   /**
    * Returns the number of solutions in this population.
    */
   public int size();
}
