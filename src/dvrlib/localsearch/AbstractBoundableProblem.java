/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2012
 * AbstractBoundableProblem.java
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

public abstract class AbstractBoundableProblem<S extends Solution, E extends Comparable<E>> extends AbstractProblem<S, E> implements BoundableProblem<S, E> {
   /**
    * AbstractBoundableProblem constructor.
    * @param solutionPoolSize The maximum number of best solutions that will be kept track of.
    * @see AbstractProblem#AbstractProblem(int)
    */
   public AbstractBoundableProblem(int solutionPoolSize) {
      super(solutionPoolSize);
   }

   /**
    * Returns a bound on the evaluation of the given solution, ignoring <code>iterationNumber</code>.
    * Override this method to implement an evaluation-bound function which takes the iteration number into account.
    * @see BoundableProblem#evaluationBound(Solution)
    */
   @Override
   public E evaluationBound(S s, long iterationNumber) {
      return evaluationBound(s);
   }
   /**
    * Returns a bound on the evaluation of the current solution of the given search state, using the current iteration-number.
    * @see BoundableProblem#evaluationBound(Solution, long)
    */
   @Override
   public E evaluationBound(SearchState<? extends Problem<S, E>, S> ss) {
      return evaluationBound(ss.solution(), ss.iterationCount());
   }
}
