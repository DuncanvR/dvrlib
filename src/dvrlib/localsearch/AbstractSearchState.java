/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2011-2012
 * AbstractSearchState.java
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

public abstract class AbstractSearchState<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> implements SearchState<P, S> {
   protected final P    problem;
   protected       long iteration = 0;

   public      AbstractSearchState(P problem) {
      this.problem = problem;
   }

   @Override
   public P    problem()                      {
      return problem;
   }

   @Override
   public void saveSolution()                 {
      problem.saveSolution(solution());
   }

   @Override
   public long iterationCount()               {
      return iteration;
   }
}
