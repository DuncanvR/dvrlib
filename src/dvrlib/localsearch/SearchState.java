/*
 * DvRLib - Local search
 * Duncan van Roermund, 2011-2012
 * SearchState.java
 */

package dvrlib.localsearch;

public interface SearchState<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> {
   /**
    * Returns the problem of this search.
    */
   public P    getProblem();

   /**
    * Returns the current solution.
    */
   public S    getSolution();

   /**
    * Saves the current solution to the problem.
    */
   public void saveSolution();

   /**
    * Returns the number of iterations this search has done.
    */
   public long getIterationCount();

   /**
    * Increases the number of iterations by <tt>n</tt>.
    */
   public void increaseIterationCount(long n);
}
