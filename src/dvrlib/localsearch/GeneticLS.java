/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * GeneticLS.java
 */

package dvrlib.localsearch;

public class GeneticLS<S extends Solution, E extends Number & Comparable<E>> extends StatefulLocalSearch<GeneticProblem<S, E>, S, E, GeneticLS<S, E>.GLSSearchState> {
   protected class GLSSearchState extends AbstractSearchState<GeneticProblem<S, E>, S> {
      protected Population<S> population;
      protected S             solution        = null;
      protected long          lastImprovement;

      protected GLSSearchState(GeneticProblem<S, E> problem, Population<S> population) {
         super(problem);
         this.population = population;
      }

      @Override
      public S solution() {
         return (solution == null ? population.peekBest() : solution);
      }
   }
   protected final Combiner<GeneticProblem<S, E>, S> combiner;
   protected final int                               popSize,
                                                     stopCount;

   /**
    * GeneticLS constructor.
    * @param combiner       The combiner used to combine solutions when searching for a solution.
    * @param populationSize The default number of solutions kept in a population.
    * @param stopCount      The number of iterations in which no better solution was found after which the algorithm will stop.
    */
   public GeneticLS(Combiner<GeneticProblem<S, E>, S> combiner, int populationSize, int stopCount) {
      if(populationSize < 1)
         throw new IllegalArgumentException("populationSize should be > 0");
      if(stopCount < 1)
         throw new IllegalArgumentException("stopCount should be > 0");

      this.combiner  = combiner;
      this.popSize   = populationSize;
      this.stopCount = stopCount;
   }

   /**
    * Searches for an optimal solution for the given problem, which is saved and returned.
    * @see GeneticLS#search(GeneticLS.GLSSearchState)
    */
   @Override
   public S search(GeneticProblem<S, E> problem, S solution) {
      return search(newState(problem, solution)).solution();
   }

   /**
    * Searches for an optimal solution using the given search state, after which the best found solution is saved and the state is returned.
    * This algorithm keeps replacing the worst solution in the population by the new combined solution if it is better, until a predefined number of iterations give no improvement.
    * @see GeneticLS#iterate(GeneticLS.GLSSearchState, int)
    */
   public GLSSearchState search(GLSSearchState state) {
      combiner.reinitialize();
      long n;
      do {
         n = stopCount - (state.iteration - state.lastImprovement);
         iterate(state, n);
      }
      while(n > 0);
      state.solution().setIterationCount(state.iterationCount());
      state.saveSolution();
      return state;
   }

   /**
    * Does <tt>n</tt> iterations using the given search state, after which it is returned.
    */
   @Override
   public GLSSearchState iterate(GLSSearchState state, long n) {
      for(long i = state.iterationCount(), iMax = i + n; i < iMax; i++) {
         // Generate new solution by combining two random solutions from the population
         state.solution = combiner.combine(state, state.population.peekRandom(), state.population.peekRandom());

         if(state.population.contains(state.solution))
            continue;

         if(savingCriterion == LocalSearch.SavingCriterion.EveryIteration ||
               (savingCriterion == LocalSearch.SavingCriterion.NewBest && state.problem.better(state.solution, state.population.peekBest())))
            state.saveSolution();

         if(state.population.add(state.solution)) {
            state.lastImprovement = i;
            if(savingCriterion == LocalSearch.SavingCriterion.EveryImprovement)
               state.saveSolution();
         }
      }

      state.solution = null;
      state.iteration += n;
      return state;
   }

   @Override
   public GLSSearchState newState(GeneticProblem<S, E> problem, S solution) {
      Population<S> population = combiner.createPopulation(problem, solution, popSize);
      problem.saveSolution(population.peekBest());
      return new GLSSearchState(problem, population);
   }
}
