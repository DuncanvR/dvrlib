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

/**
 * GeneticLS implements a genetic search algorithm in the localsearch framework.
 * By invoking the search method, it starts iterating until no more improvements can be found for a number of iterations -- controlled by the <code>stopCount</code> parameter.
 * Each iteration, <code>2 * (populationSize + additionalSelectionCount - elitistSelectionCount)</code> parents are selected for generating offspring, and the <code>elitistSelectionCount</code> best solutions are copied to the next generation.
 * For each pair of parents, the <code>Combiner</code> is called to create a (set of) solution(s), of which the first/last/best is added to the next generation, depending on the setting of <code>combinerStrategy</code>.
 */
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

   protected final Combiner<GeneticProblem<S, E>, S> combiner;

   protected int                     additionalSelectionCount,
                                     elitistSelectionCount,
                                     populationSize,
                                     stopCount;
   protected ParentSelector<S>       parentSelector;
   protected OffspringSelector<S, E> offspringSelector;

   /**
    * GeneticLS constructor.
    * @param selector                 The selector used to select solutions for recombination from the population.
    * @param combiner                 The combiner used to combine solutions when searching for a solution.
    * @param additionalSelectionCount The number of extra solutions that are created each generation.
    * @param elitistSelectionCount    The number of best solutions that are copied from a previous generation without alteration.
    * @param populationSize           The maximum number of solutions kept in a population.
    * @param stopCount                The number of iterations in which no better solution was found after which the algorithm will stop.
    * @see GeneticLS#setPopulationSize(int)
    * @see GeneticLS#setStopCount(int)
    * @see GeneticLS#setAdditionalSelectionCount(int)
    * @see GeneticLS#setElitistSelectionCount(int)
    */
   public GeneticLS(ParentSelector<S> parentSelector, Combiner<GeneticProblem<S, E>, S> combiner, OffspringSelector<S, E> offspringSelector, int additionalSelectionCount, int elitistSelectionCount, int populationSize, int stopCount) {
      this.combiner = combiner;
      this.parentSelector = parentSelector;
      this.offspringSelector = offspringSelector;
      setPopulationSize(populationSize);
      setStopCount(stopCount);
      setAdditionalSelectionCount(additionalSelectionCount);
      setElitistSelectionCount(elitistSelectionCount);
   }

   /**
    * Sets the number of extra solutions that are created each generation.
    */
   public void setAdditionalSelectionCount(int additionalSelectionCount) {
      this.additionalSelectionCount = additionalSelectionCount;
   }

   /**
    * Sets the number of best solutions that are copied over into the next generation without alteration, bypassing the combination and mutation.
    */
   public void setElitistSelectionCount(int elitistSelectionCount) {
      if(elitistSelectionCount > populationSize)
         throw new IllegalArgumentException("elitistSelectionCount should be <= populationSize");
      this.elitistSelectionCount = elitistSelectionCount;
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
   protected S doSearch(GeneticProblem<S, E> problem, E bound, long maxTimeMillis, S solution) {
      return iterate(newState(problem, solution), bound, maxTimeMillis).solution();
   }

   /**
    * Searches for an optimal solution using the given search state, after which the best found solution is saved and the state is returned.
    * This algorithm keeps replacing the worst solution in the population by the new combined solution if it is better, until a predefined number of iterations give no improvement.
    * @see GeneticLS#iterate(GeneticLS.GLSSearchState, Object, long, int)
    */
   public SearchState iterate(SearchState state, E bound, long maxTimeMillis) {
      assert (state != null) : "State should not be null";
      assert (bound != null) : "Bound should not be null";

      combiner.reinitialise(state.problem);
      long n = stopCount;
      while(n > 0 && !state.problem.betterEq(state.solution(), bound)) {
         iterate(state, bound, maxTimeMillis, n);
         n = stopCount - (state.iteration - state.lastImprovement);

         // Check the time limit
         if(System.currentTimeMillis() >= maxTimeMillis)
            break;
      }
      state.solution().setIterationCount(state.iterationCount());
      state.saveSolution();
      return state;
   }

   /**
    * Does <code>n</code> iterations using the given search state, after which it is returned.
    * When a solution is found that is better or equal to the given bound, the search is stopped.
    * @param maxTimeMillis The bound on System.currentTimeMillis().
    */
   @Override
   public SearchState iterate(SearchState state, E bound, long maxTimeMillis, long n) {
      E overallBestEval = (state.population.size() > 0 ? state.problem.evaluate(state.population.peekBest()) : null);
      for(long stop = state.iteration + n; state.iteration < stop && !state.problem.betterEq(overallBestEval, bound); state.iteration++) {
         // Select parent solutions for the next generation
         Iterable<S> parents = parentSelector.select(state, 2 * (populationSize + additionalSelectionCount - elitistSelectionCount));

         // Clear population, keeping only the elitists
         state.population.retainBest(elitistSelectionCount);
         E generationBestEval = (savingCriterion == LocalSearch.SavingCriterion.EveryImprovement &&
                                 state.population.size() > 0 ? state.problem.evaluate(state.population.peekBest()) : null);

         // Generate new solutions by combining the parents
         java.util.Iterator<S> it = parents.iterator();
         while(it.hasNext()) {
            state.solution = offspringSelector.select(state, combiner.combine(state, it.next(), it.next()), (state.population.size() < populationSize ? null : state.population.peekWorst()));
            if(state.solution == null || state.population.contains(state.solution))
               continue;

            // Save the solution
            if(savingCriterion == LocalSearch.SavingCriterion.EveryImprovement && state.problem.better(state.solution, generationBestEval)) {
               state.saveSolution();
               generationBestEval = state.problem.evaluate(state.solution);
            }
            if(state.problem.better(state.solution, overallBestEval)) {
               overallBestEval = state.problem.evaluate(state.solution);
               state.lastImprovement = state.iteration;
               if(savingCriterion == LocalSearch.SavingCriterion.NewBest)
                  state.saveSolution();
            }
            if(state.population.add(state.solution)) {
               if(savingCriterion == LocalSearch.SavingCriterion.EveryIteration)
                  state.saveSolution();
            }

            // Check the time limit
            if(System.currentTimeMillis() >= maxTimeMillis)
               break;
         }

         // Check the time limit
         if(System.currentTimeMillis() >= maxTimeMillis)
            break;
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
