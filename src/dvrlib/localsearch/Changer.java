/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2010-2013
 * Changer.java
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
    * Reinitialises this changer; used when a new search is started.
    * @see LocalSearch#search(Problem, Solution)
    */
   public abstract void reinitialise(P problem);
}
