/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2012
 * SubstitutionChanger.java
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
