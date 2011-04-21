/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * GeneticLS.java
 */

package dvrlib.localsearch;

import java.util.Random;

public class GeneticLS<S extends Solution, E extends Number & Comparable<E>> extends StatefulLocalSearch<S, E, GeneticLS.GLSSearchState<S, E>> {
   public static class GLSSearchState<S extends Solution, E extends Comparable<E>> extends AbstractSearchState<Problem<S, E>, S> {
      // static inner class: workaround of bug 6557954 in jdk6
      protected WeightedTree<S> population            ;
      protected S               solution        = null;
      protected long            lastImprovement       ;

      protected GLSSearchState(Problem<S, E> problem, WeightedTree<S> population) {
         super(problem);
         this.population = population;
      }

      @Override
      public S getSolution() {
         return (solution == null ? population.getMax().peek() : solution);
      }

      public WeightedTree<S> getPopulation() {
         return population;
      }
   }
   protected final Combiner<Problem<S, E>, S> combiner ;
   protected final int                        popSize  ,
                                              stopCount;

   /**
    * GeneticLS constructor.
    * @param combiner       The combiner used to combine solutions when searching for a solution.
    * @param populationSize The default number of solutions kept in a population.
    * @param stopCount      The number of iterations in which no better solution was found after which the algorithm will stop.
    */
   public GeneticLS(Combiner<Problem<S, E>, S> combiner, int populationSize, int stopCount) {
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
    * @see GeneticLS#search(dvrlib.localsearch.PluralSearchState)
    */
   @Override
   public S search(Problem<S, E> problem, S solution) {
      return search(newState(problem, solution)).getSolution();
   }

   /**
    * Searches for an optimal solution using the given search state, after which the best found solution is saved and the state is returned.
    * This algorithm keeps replacing the worst solution in the population by the new combined solution if it is better, until a predefined number of iterations give no improvement.
    * @see GeneticLS#iterate(dvrlib.localsearch.PluralSearchState, int)
    */
   public GLSSearchState<S, E> search(GLSSearchState<S, E> state) {
      long n;
      do {
         n = stopCount - (state.iteration - state.lastImprovement);
         iterate(state, n);
      }
      while(n > 0);
      state.getSolution().setIterationCount(state.getIterationNumber());
      state.saveSolution();
      return state;
   }

   /**
    * Does <tt>n</tt> iterations using the given search state, after which it is returned.
    */
   @Override
   public GLSSearchState<S, E> iterate(GLSSearchState<S, E> state, long n) {
      Random r = new Random();

      while(state.population.size() > popSize)
         state.population.popMin();

      for(long i = state.getIterationNumber(), iMax = state.getIterationNumber() + n; i < iMax; i++) {
         // Generate new solution from two random solutions in the population
         state.solution = combiner.combine(state, state.population.getWeighted(r.nextDouble()).b, state.population.getWeighted(r.nextDouble()).b);

         if(state.population.size() >= popSize) {
            // Replace the worst solution in the population with the new solution if its better
            if(state.population.replaceMin(state.problem.getWeight(state), state.solution) != null)
               state.lastImprovement = i;
         }
         else {
            state.population.add(state.getProblem().getWeight(state), state.solution);
            state.lastImprovement = i;
         }

         state.solution = null;
      }

      state.increaseIterationCount(n);
      return state;
  }

   @Override
   public GLSSearchState<S, E> newState(Problem<S, E> problem, S solution) {
      return new GLSSearchState(problem, combiner.createPopulation(problem, solution, popSize));
   }
}
