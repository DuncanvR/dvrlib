/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * IncrementalChanger.java
 */

package dvrlib.localsearch;

public abstract class IncrementalChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> implements Changer<P, S, IncrementalChanger<P, S>.Change> {
   public class Change extends dvrlib.localsearch.Change<P, S> {
      /**
       * Undoes this change by restoring the solution to its original state.
       * @see IncrementalChanger#undoChange(SingularSearchState, IncrementalChanger.Change)
       */
      @Override
      protected final void undo(SingularSearchState<P, S> ss) {
         if(next != null)
            next.undo(ss);
         undoChange(ss, this);
      }
   }

   /**
    * Undoes the given change.
    * @see Change#makeChange(SingularSearchState)
    */
   public abstract void undoChange(SingularSearchState<P, S> ss, IncrementalChanger<P, S>.Change change);
}

