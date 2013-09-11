/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2013
 * RetryChanger.java
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

public class RetryChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> extends Changer<P, S, Changer<P, S, ?>.Change> {
   protected Changer<P, S, Changer<P, S, ?>.Change> changer;
   protected int                                    tries;

   /**
    * RetryChanger constructor.
    * @param changers The changers that will be invoked.
    * @param tries    The maximum number of times the given changer will be called.
    */
   public RetryChanger(Changer<P, S, Changer<P, S, ?>.Change> changer, int tries) {
      this.changer = changer;
      this.tries   = tries;
   }

   // dvrlib.localsearch.Changer methods
   /**
    * Generates, executes and returns a new change, by invoking the underlying changer up to <code>tries</code> times.
    * @see Changer#undoChange(SingularSearchState, Change)
    * @throws CannotChangeException To indicate the underlying changer was unable to change the given search state within the number of tries.
    */
   @Override
   public Changer<P, S, ?>.Change makeChange(SingularSearchState<P, S> ss) throws CannotChangeException {
      CannotChangeException ex = null;
      for(int i = 0; i < tries; i++) {
         try {
            return changer.makeChange(ss);
         }
         catch(CannotChangeException e) {
            ex = e;
         }
      }
      throw new CannotChangeException(this, ex);
   }

   /**
    * Reinitialises the underlying changer.
    * @see Changer#reinitialise(Problem)
    */
   @Override
   public void reinitialise(P problem) {
      changer.reinitialise(problem);
   }
}
