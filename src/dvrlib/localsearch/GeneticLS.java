/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2010-2013
 * GeneticLS.java
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

import java.util.LinkedList;

public class GeneticLS<S extends Solution, E extends Comparable<E>> extends StatefulLocalSearch<GeneticProblem<S, E>, S, E, GeneticLS<S, E>.SearchState> {
   public class SearchState extends AbstractSearchState<GeneticProblem<S, E>, S> {
      protected GeneticPopulation<S> population;
      protected S                    solution        = null;
      protected long                 lastImprovement;

      protected SearchState(GeneticProblem<S, E> problem, GeneticPopulation<S> population) {
         super(problem);
         this.population = population;
      }

      public GeneticPopulation<S> population() {
         return population;
      }

      @Override
      public S solution() {
         return (solution == null ? population.peekBest() : solution);
      }
   }

   /**
    * Defines the strategy for when to stop evaluating newly generated solutions.
    *   First: stop after the first improvement;
    *   Last:  continue until a non-improving solution is encountered;
    *   Best:  evaluate all solutions;
    * Note that the first two expect an appropriate ordering on the set of solutions given by Combiner#combine(SearchState, Solution, Solution).
    * @see GeneticLS#setCombinerStrategy(GeneticLS.CombinerStrategy)
    */
   public enum CombinerStrategy { First, Last, Best };

   protected final Combiner<GeneticProblem<S, E>, S> combiner;

   protected int              populationSize,
                              stopCount;
   protected CombinerStrategy combinerStrategy = CombinerStrategy.First;

   /**
    * GeneticLS constructor.
    * @param combiner       The combiner used to combine solutions when searching for a solution.
    * @param populationSize The maximum number of solutions kept in a population.
    * @param stopCount      The number of iterations in which no better solution was found after which the algorithm will stop.
    * @see GeneticLS#setPopulationSize(int)
    * @see GeneticLS#setStopCount(int)
    */
   public GeneticLS(Combiner<GeneticProblem<S, E>, S> combiner, int populationSize, int stopCount) {
      this.combiner  = combiner;
      setPopulationSize(populationSize);
      setStopCount(stopCount);
   }

   /**
    * Sets the criterion for deciding which generated child to pick.
    * @see GeneticLS.CombinerStrategy
    */
   public void setCombinerStrategy(CombinerStrategy combinerStrategy) {
      this.combinerStrategy = combinerStrategy;
   }

   /**
    * Sets the maximum number of solutions to keep.
    */
   public void setPopulationSize(int populationSize) {
      if(populationSize < 1)
         throw new IllegalArgumentException("populationSize should be > 0");
      this.populationSize = populationSize;
   }

   /**
    * Sets the number of iterations in which no better solutions are found after which the search will stop.
    */
   public void setStopCount(int stopCount) {
      if(stopCount < 1)
         throw new IllegalArgumentException("stopCount should be > 0");
      this.stopCount = stopCount;
   }

   /**
    * Searches for an optimal solution for the given problem, which is saved and returned.
    * @see GeneticLS#search(GeneticLS.GLSSearchState)
    */
   @Override
   protected S doSearch(GeneticProblem<S, E> problem, E bound, S solution) {
      return search(newState(problem, solution), bound).solution();
   }

   /**
    * Searches for an optimal solution using the given search state, after which the best found solution is saved and the state is returned.
    * This algorithm keeps replacing the worst solution in the population by the new combined solution if it is better, until a predefined number of iterations give no improvement.
    * @see GeneticLS#iterate(GeneticLS.GLSSearchState, int)
    */
   public SearchState search(SearchState state, E bound) {
      assert (state != null) : "State should not be null";
      assert (bound != null) : "Bound should not be null";

      combiner.reinitialise(state.problem);
      long n = stopCount;
      while(n > 0 && !state.problem.betterEq(state.solution(), bound)) {
         iterate(state, bound, n);
         n = stopCount - (state.iteration - state.lastImprovement);
      }
      state.solution().setIterationCount(state.iterationCount());
      state.saveSolution();
      return state;
   }

   /**
    * Does <code>n</code> iterations using the given search state, after which it is returned.
    * When a solution is found that is better or equal to the given bound, the search is stopped.
    */
   @Override
   public SearchState iterate(SearchState state, E bound, long n) {
      for(long stop = state.iteration + n; state.iteration < stop && !state.problem.betterEq(state.solution(), bound); state.iteration++) {
         // Generate new solutions by combining two random solutions from the population
         java.util.Set<S> ss = combiner.combine(state, state.population.peekRandom(), state.population.peekRandom());
         state.solution = (state.population.size() < populationSize ? null : state.population.peekWorst());

         for(S s : ss) {
            if(state.population.contains(s))
               continue;
            if(state.problem.better(state.problem.evaluationBound(s), state.solution)) {
               if(state.problem.better(s, state.solution)) {
                  state.solution = s;
                  if(combinerStrategy == CombinerStrategy.First)
                     break;
               }
               else if(combinerStrategy == CombinerStrategy.Last)
                  break;
            }
         }

         if(state.solution == null)
            continue;
         else if(state.solution == state.population.peekWorst()) {
            state.solution = null;
            continue;
         }

         if(savingCriterion == LocalSearch.SavingCriterion.EveryIteration ||
               (savingCriterion == LocalSearch.SavingCriterion.NewBest && state.problem.better(state.solution, state.population.peekBest())))
            state.saveSolution();

         if(state.population.add(state.solution)) {
            state.lastImprovement = state.iteration;
            if(savingCriterion == LocalSearch.SavingCriterion.EveryImprovement)
               state.saveSolution();
         }
      }
      state.solution = null;
      return state;
   }

   @Override
   public SearchState newState(GeneticProblem<S, E> problem, S solution) {
      LinkedList<S> solutions = new LinkedList<S>();
      solutions.add(solution);
      return newState(problem, solutions);
   }

   public SearchState newState(GeneticProblem<S, E> problem, Iterable<S> solutions) {
      GeneticPopulation<S> population = combiner.createPopulation(problem, solutions, populationSize);
      problem.saveSolution(population.peekBest());
      return new SearchState(problem, population);
   }
}
