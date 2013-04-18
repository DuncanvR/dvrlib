/*
 * DvRlib - Local search
 * Duncan van Roermund, 2010-2012
 * Changer.java
 */

package dvrlib.localsearch;

public abstract class Changer<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C extends Changer<P, S, ?>.Change> {
   protected abstract class Change {
      protected Change prev, next;

      /**
       * Undoes this change by executing the appropriate recursive calls.
       * This method should propagate as much of its functionality as possible to the enclosing Changer.
       */
      protected abstract void undo(SingularSearchState<P, S> ss);
   }

   /**
    * Generates, executes and returns a new change.
    * The change should be small, such that it transforms the solution into one that closely resembles it.
    * @see Changer#undoChange(SingularSearchState, Change)
    * @throws CannotChangeException To indicate this changer was unable to change the given search state.
    */
   public abstract C makeChange(SingularSearchState<P, S> ss) throws CannotChangeException;

   /**
    * Reinitializes this changer, used when a new search is started.
    * @see LocalSearch#search(Problem, Solution)
    */
   public abstract void reinitialize(P problem);
}
