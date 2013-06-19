/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2012
 * ChangeList.java
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

public class ChangeList<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> {
   protected Changer<P, S, ?>.Change first, last;
   protected int size = 0;

   @SuppressWarnings("unchecked")
   public void add(Changer<P, S, ?>.Change change) {
      if(size++ > 0) {
         change.prev = (Changer.Change) last;
         last.next   = (Changer.Change) change;
         last        = (Changer.Change) change;
      }
      else
         first = last = change;
   }

   public void clear() {
      first = last = null;
      size = 0;
   }

   public int size() {
      return size;
   }

   public void undoAll(SingularSearchState<P, S> ss) {
      if(size > 0) {
         first.undo(ss);
         first = null;
         last  = null;
         size  = 0;
      }
   }

   public void undoLast(SingularSearchState<P, S> ss) {
      if(size > 0) {
         last.undo(ss);
         last = last.prev;
         size--;
      }
   }
}
