/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * Changer.java
 */

package dvrlib.localsearch;

public interface Changer<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C extends Change<P, S>> {
   /**
    * Reinitializes this changer, used when a new search is started.
    * @see LocalSearch#search(Problem, Solution)
    */
   public void reinitialize(P problem);

   /**
    * Generates, executes and returns a new change.
    * The change should be small, such that it transforms the solution into one that closely resembles it.
    * @see Changer#undoChange(SingularSearchState, Change)
    */
   public C makeChange(SingularSearchState<P, S> ss);
}
