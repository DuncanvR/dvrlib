/*
 * DvRlib - Local search
 * Duncan van Roermund, 2012
 * SubstitutionChanger.java
 */

package dvrlib.localsearch;

public abstract class SubstitutionChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> extends Changer<P, S, SubstitutionChanger<P, S>.Change> {
   public class Change extends Changer<P, S, SubstitutionChanger<P, S>.Change>.Change {
      protected final S old;

      /**
       * SubstitutionChanger.Change constructor.
       * @param old The previous solution.
       */
      protected Change(S old) {
         this.old = old;
      }

      /**
       * Undoes this change by reverting to the previous solution.
       */
      @Override
      public final void undo(SingularSearchState<P, S> ss) {
         ss.solution = old;
      }
   }

   public Change setSolution(SingularSearchState<P, S> ss, S s) {
      Change c = new Change(ss.solution());
      ss.solution = s;
      return c;
   }
}
