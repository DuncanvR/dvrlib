/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2010-2013
 * Combiner.java
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

public interface Combiner<P extends GeneticProblem<S, ?>, S extends Solution> {
   /**
    * Reinitialises this combiner; used when a new search is started.
    * @see LocalSearch#search(Problem, Solution)
    */
   public void reinitialise(P problem);

   /**
    * Returns a set of combinations of the two given solutions.
    * The resulting set should provide an appropriate ordering on the solutions if a strategy other than GeneticLS.CombinerStrategy.Best is used.
    * This method may also introduce mutations after creating a new solution.
    * @see GeneticLS.CombinerStrategy
    */
   public java.util.Set<S> combine(SearchState<P, S> ss, S s1, S s2);

   /**
    * Creates a new population with at least the given solutions and at most <code>popSize</code> solutions.
    * A simple implementation would include the given solutions, and some random solutions.
    */
   public GeneticPopulation<S> createPopulation(P problem, Iterable<S> solution, int popSize);
}
