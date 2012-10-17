/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * PhasedChanger.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Tuple;

public class PhasedChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C extends Changer<P, S, ?>.Change> extends Changer<P, S, PhasedChanger<P, S, C>.Change> {
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
         PhasedChanger.this.phase = phase;
      }
   }

   protected Changer<P, S, C>[] changers;
   protected int                phase;

   /**
    * PhasedChanger constructor.
    * @param changers The changers that will be used for each phase.
    */
   public PhasedChanger(Changer<P, S, C>[] changers) {
      this.changers = changers;
   }

   /**
    * Generates, executes and returns a new change.
    * The change should be small, such that it transforms the solution into one that closely resembles it.
    * @see Changer#undoChange(SingularSearchState, PhasedChanger.Change)
    */
   @Override
   public Change makeChange(SingularSearchState<P, S> ss) {
      return new Change(changers[phase].makeChange(ss), phase++);
   }

   /**
    * Reinitializes this changer and all its phases.
    * @see Changer#reinitialize(Problem)
    */
   @Override
   public void reinitialize(P problem) {
      for(Changer<P, S, C> c : changers) {
         c.reinitialize(problem);
      }
      phase = 0;
   }
}
