/*
 * DvRLib - Local search
 * Duncan van Roermund, 2011
 * StatefulLocalSearch.java
 */

package dvrlib.localsearch;

public abstract class StatefulLocalSearch<S extends Solution, E extends Comparable<E>, SS extends SearchState<Problem<S, E>, S>> extends LocalSearch<S, E> {
   /**
    * Does <tt>n</tt> iterations on the given solution, and returns the best solution found.
    */
   public abstract SS iterate(SS state, long n);

   /**
    * Returns a new search state.
    */
   public abstract SS newState(Problem<S, E> problem, S solution);
}
