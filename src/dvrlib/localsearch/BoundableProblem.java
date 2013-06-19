/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2012
 * BoundableProblem.java
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

public interface BoundableProblem<S extends Solution, E extends Comparable<E>> extends Problem<S, E> {
   /**
    * Returns a bound on the evaluation of the given solution.
    * This method should return quickly and give a value that in no case will be worse than the actual evaluation,
    *    i.e. <code>betterEq(evaluationBound(s), evaluate(s))</code> should always hold, for any solution <code>s</code>.
    */
   public E evaluationBound(S s);
   /**
    * Returns a bound on the evaluation of the given solution at the indicated iteration.
    * This method should return quickly and give a value that in no case will be worse than the actual evaluation,
    *    i.e. <code>betterEq(evaluationBound(s, n), evaluate(s, n))</code> should always hold, for any solution <code>s</code>.
    */
   public E evaluationBound(S s, long iterationNumber);
   /**
    * Returns a bound on the evaluation of the current solution of the given search state.
    * This method should return quickly and give a value that in no case will be worse than the actual evaluation,
    *    i.e. <code>betterEq(evaluationBound(ss), evaluate(ss))</code> should always hold, for any solution <code>s</code>.
    */
   public E evaluationBound(SearchState<? extends Problem<S, E>, S> ss);
}
