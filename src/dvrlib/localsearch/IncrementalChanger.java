/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2012
 * IncrementalChanger.java
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

public abstract class IncrementalChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> extends Changer<P, S, IncrementalChanger<P, S>.Change> {
   public class Change extends Changer<P, S, ?>.Change {
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

