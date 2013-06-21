/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2012
 * AbstractGeneticProblem.java
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

public abstract class AbstractGeneticProblem<S extends Solution, E extends Comparable<E>> extends AbstractBoundableProblem<S, E> implements GeneticProblem<S, E> {
   /**
    * AbstractGeneticProblem constructor.
    * @param solutionPoolSize The maximum number of best solutions that will be kept track of.
    * @see AbstractProblem#AbstractProblem(int)
    */
   public AbstractGeneticProblem(int solutionPoolSize) {
      super(solutionPoolSize);
   }

   /**
    * Makes the first solution look most like the second one.
    * Default implementation of this method throws an UnsupportedOperationException.
    * @see GeneticProblem#ensureMostCommon(Solution)
    */
   @Override
   public void ensureMostCommon(S s1, S s2) {
      throw new UnsupportedOperationException(this.getClass().getName() + ".ensureMostCommon(dvrlib.localsearch.Solution) has not been implemented");
   }

   /**
    * Returns the weight of the given solution.
    * @see GeneticProblem#weight(Solution)
    * @see AbstractProblem#evaluate(Solution)
    */
   @Override
   public double weight(S s) {
      return weight(evaluate(s));
   }
   /**
    * Returns the weight of the given solution at the given iteration.
    * @param iterationNumber Indicates the iteration number in the current search.
    * @see AbstractProblem#weight(java.lang.Comparable)
    * @see AbstractProblem#evaluate(Solution, long)
    */
   @Override
   public double weight(S s, long iterationNumber) {
      return weight(evaluate(s, iterationNumber));
   }
   /**
    * Returns the weight of the current solution of the given search state.
    * @see AbstractProblem#weight(Solution, long)
    */
   @Override
   public double weight(SearchState<GeneticProblem<S, E>, S> ss) {
      return weight(ss.solution(), ss.iterationCount());
   }
}
