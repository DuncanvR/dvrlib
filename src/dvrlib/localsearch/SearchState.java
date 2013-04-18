/*
 * DvRlib - Local search
 * Duncan van Roermund, 2011-2012
 * SearchState.java
 */

package dvrlib.localsearch;

public interface SearchState<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> {
   /**
    * Returns the problem of this search.
    */
   public P    problem();

   /**
    * Returns the current solution.
    */
   public S    solution();

   /**
    * Saves the current solution to the problem.
    */
   public void saveSolution();

   /**
    * Returns the number of iterations this search has done.
    */
   public long iterationCount();
}
