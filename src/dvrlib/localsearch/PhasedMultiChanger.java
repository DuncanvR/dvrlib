/*
 * DvRlib - Local search
 * Duncan van Roermund, 2012-2013
 * PhasedMultiChanger.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;

public class PhasedMultiChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C extends Changer<P, S, ?>.Change> extends Changer<P, S, PhasedMultiChanger<P, S, C>.Change> {
   protected class Change extends Changer<P, S, ?>.Change {
      protected C   change;
      protected int phase;

      public Change(C change, int phase) {
         this.change = change;
         this.phase  = phase;
      }

      @Override
      protected final void undo(SingularSearchState<P, S> ss) {
         change.undo(ss);
         PhasedMultiChanger.this.phase = phase;
      }
   }

   protected Changer<P, S, C>[] changers;
   protected int                phase;

   /**
    * PhasedMultiChanger constructor.
    * @param changers The changers that will be used for each phase.
    */
   public PhasedMultiChanger(Changer<P, S, C>[] changers) {
      this.changers = changers;
   }

   // dvrlib.localsearch.Changer methods
   /**
    * Generates, executes and returns a new change, by invoking the current phase.
    * @see Changer#undoChange(SingularSearchState, Change)
    * @throws CannotChangeException To indicate the current phase was unable to change the given search state.
    */
   @Override
   public Change makeChange(SingularSearchState<P, S> ss) throws CannotChangeException {
      return new Change(changers[phase].makeChange(ss), phase++);
   }

   /**
    * Reinitialises this changer and all its phases.
    * @see Changer#reinitialise(Problem)
    */
   @Override
   public void reinitialise(P problem) {
      for(Changer<P, S, C> c : changers) {
         c.reinitialise(problem);
      }
      phase = 0;
   }
}
