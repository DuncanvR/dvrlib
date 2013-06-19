/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2012
 * GeneticProblem.java
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

public interface GeneticProblem<S extends Solution, E extends Comparable<E>> extends BoundableProblem<S, E> {
   /**
    * Makes the first solution look most like the second one.
    */
   public void ensureMostCommon(S s1, S s2);

   /**
    * Returns the weight of the given evaluation.
    */
   public double weight(E e);
   /**
    * Returns the weight of the given solution.
    * @see GeneticProblem#weight(java.lang.Comparable)
    */
   public double weight(S s);
   /**
    * Returns the weight of the given solution at the given iteration.
    * @param iterationNumber Indicates the iteration number in the current search.
    * @see GeneticProblem#weight(java.lang.Comparable)
    */
   public double weight(S s, long iterationNumber);
   /**
    * Returns the weight of the current solution of the given search state.
    * @see GeneticProblem#weight(Solution, long)
    */
   public double weight(SearchState<GeneticProblem<S, E>, S> ss);
}
