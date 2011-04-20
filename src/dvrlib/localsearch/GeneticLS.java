/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * GeneticLS.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
import java.util.Random;

public class GeneticLS<S extends Solution, E extends Number & Comparable<E>> extends LocalSearch<S, E> {
   protected final Combiner<S> combiner      ;
   protected final int         populationSize,
                               stopCount     ;

   /**
    * GeneticLS constructor.
    * @param combiner       The combiner used to combine solutions when searching for a solution.
    * @param populationSize The default number of solutions kept in a population.
    * @param stopCount      The number of iterations in which no better solution was found after which the algorithm will stop.
    */
   public GeneticLS(Combiner<S> combiner, int populationSize, int stopCount) {
      if(populationSize < 1)
         throw new IllegalArgumentException("populationSize should be > 0");
      if(stopCount < 1)
         throw new IllegalArgumentException("stopCount should be > 0");

      this.combiner       = combiner;
      this.populationSize = populationSize;
      this.stopCount      = stopCount;
   }

   /**
    * Searches for an optimal solution for the given problem, which is saved and returned.
    * @see GeneticLS#search(dvrlib.localsearch.PluralSearchState)
    */
   @Override
   public S search(Problem<S, E> problem, S solution) {
      S s = search(problem, solution, populationSize).peekMin().b;
      problem.saveSolution(s);
      return s;
   }

   /**
    * Searches for an optimal solution using the given search state, after which the best found solution is saved and the state is returned.
    * This algorithm keeps replacing the worst solution in the population by the new combined solution if it is better, until a predefined number of iterations give no improvement.
    * @see GeneticLS#iterate(dvrlib.localsearch.PluralSearchState, int)
    */
   public WeightedTree<S> search(Problem<S, E> problem, S solution, int maxPopSize) {
      WeightedTree<S> population = createPopulation(problem, solution);

      // Keep iterating until no further improvements are found
      for(int n = stopCount; n > 0; ) {
         int li = iterate(problem, population, n, maxPopSize);
         n = (li < 0 ? 0 : stopCount - (n - li));
      }

      return population;
   }

   /**
    * Does <tt>n</tt> iterations using the given search state, after which it is returned.
    */
   @Override
   public S iterate(Problem<S, E> problem, S solution, int n) {
      return iterate(problem, solution, n, populationSize);
   }

   /**
    * Does <tt>n</tt> iterations on the given solution, after which the best found solution is saved and returned.
    * @param maxPopSize The maximum size of the population.
    * @see GeneticLS#iterate(dvrlib.localsearch.Problem, dvrlib.localsearch.WeightedTree, int, int)
    */
   public S iterate(Problem<S, E> problem, S solution, int n, int maxPopSize) {
      WeightedTree<S> population = createPopulation(problem, solution);
      iterate(problem, population, n, maxPopSize);
      S s = population.peekMin().b;
      problem.saveSolution(s);
      return s;
  }

   /**
    * Does <tt>n</tt> iterations on the given population.
    * @param maxPopSize The maximum size of the population. If the given population is larger, excess solutions are dropped.
    * @return the iteration number at which the last improvement was made, or -1 if no improvement was made.
    */
   public int iterate(Problem<S, E> problem, WeightedTree<S> population, int n, int maxPopSize) {
      if(n < 0)
         throw new IllegalArgumentException("n should be >= 0");

      Random r = new Random();
      int lastImprovement = -1;

      while(population.size() > maxPopSize)
         population.popMin();

      for(int i = 0; i < n; i++) {
         // Generate new solution from two solutions in the population
         S newSolution = combiner.combine(population.getWeighted(r.nextDouble()).b, population.getWeighted(r.nextDouble()).b);
         combiner.mutate(newSolution);

         // Compare new solution with the worst
         WeightedTreeNode<S> worst = population.getMin();
         if(problem.better(newSolution, worst.peek())) {
            if(population.size() >= maxPopSize)
               population.pop(worst);
            population.add(problem.getWeight(newSolution), newSolution);
            lastImprovement = i;
         }
      }

      for(Pair<Double, S> p : population) {
         p.b.increaseIterationCount(n);
      }

      return lastImprovement;
   }

   /**
    * Creates a new population with the given solution in it.
    */
   protected WeightedTree<S> createPopulation(Problem<S, E> problem, S solution) {
      WeightedTree<S> population = new WeightedTree();
      population.add(problem.getWeight(solution), solution);
      return population;
   }
}
