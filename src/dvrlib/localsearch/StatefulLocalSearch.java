/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * StatefulLocalSearch.java
 */

package dvrlib.localsearch;

public abstract class StatefulLocalSearch<P extends Problem<S, E>, S extends Solution, E extends Comparable<E>, SS extends SearchState<P, S>> extends LocalSearch<P, S, E> {
   /**
    * Does <code>n</code> iterations on the given solution, and returns the best solution found.
    * When a solution is found that is better or equal to the given bound, the search is stopped.
    */
   public abstract SS iterate(SS state, E bound, long n);

   /**
    * Returns a new search state.
    */
   public abstract SS newState(P problem, S solution);
}
