/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2012-2013
 * PhasedMultiChanger.java
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
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
