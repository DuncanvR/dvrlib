/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2012-2013
 * StatefulLocalSearch.java
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

public abstract class StatefulLocalSearch<P extends Problem<S, E>, S extends Solution, E extends Comparable<E>, SS extends SearchState<P, S>> extends LocalSearch<P, S, E> {
   /**
    * Does <code>n</code> iterations on the given solution, and returns the best solution found.
    * When a solution is found that is better or equal to the given bound, the search is stopped.
    */
   public abstract SS iterate(SS state, E bound, long maxTimeMillis, long n);

   /**
    * Returns a new search state.
    */
   public abstract SS newState(P problem, S solution);
}
