/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * SubstitutionChanger.java
 */

package dvrlib.localsearch;

public abstract class SubstitutionChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> implements Changer<P, S, SubstitutionChanger<P, S>.Change> {
   public class Change extends dvrlib.localsearch.Change<P, S> {
      protected final S old;

      public Change(S old) {
         this.old = old;
      }

      /**
       * Undoes this change by restoring the old solution.
       */
      @Override
      protected final void undo(SingularSearchState<P, S> ss) {
         ss.solution = old;
      }
   }
}
