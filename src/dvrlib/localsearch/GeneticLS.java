/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * GeneticLS.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
import java.util.Random;

public class GeneticLS<S extends Solution, E extends Number & Comparable<E>> extends LocalSearch<S, E> {
   protected final Combiner<S> combiner;
   protected final int populationSize, stopCount;

   /**
    * GeneticLS constructor.
    * @param combiner       The combiner used to combine solutions when searching for a solution.
    * @param populationSize The number of solutions kept in the population.
    * @param stopCount      The number of iterations in which no better solution was found after which the algorithm will stop.
    */
   public GeneticLS(Combiner<S> combiner, int populationSize, int stopCount) {
      this.combiner = combiner;
      this.populationSize = Math.max(1, populationSize);
      this.stopCount = stopCount;
   }

   /**
    * Searches for a solution for the given problem.
    * @see GeneticLS#search(dvrlib.localsearch.Problem, dvrlib.localsearch.Solution, int)
    */
   @Override
   public S search(Problem<S, E> problem, S solution) {
      S s = search(problem, solution, populationSize).peekMin().b;
      problem.saveSolution(s);
      return s;
   }

   /**
    * Returns the population after searching for a solution for the given problem.
    * This algorithm keeps replacing the worst solution in the population by the new combined solution if it is better, until a predefined number of iterations give no improvement.
    */
   public WeightedTree<S> search(Problem<S, E> problem, S solution, int size) {
      WeightedTree<S> population = new WeightedTree<S>();
      population.add(problem.getWeight(solution), solution);

      // Initialize population with random solutions
      while(population.size() < populationSize) {
         S newSolution = problem.randomSolution();
         newSolution.ensureMostCommon(solution);
         population.add(problem.getWeight(newSolution), newSolution);
      }

      Random r = new Random();
      int iterations = 0;
      for(int sc = 0; sc < stopCount; iterations++, sc++) {
         // Generate new solution from two solutions in the population
         S newSolution = combiner.combine(population.getWeighted(r.nextDouble()).b, population.getWeighted(r.nextDouble()).b);
         combiner.mutate(newSolution);

         // Compare new solution with the worst
         WeightedTreeNode<S> worst = population.getMax();
         if(problem.better(newSolution, worst.peek())) {
            population.remove(worst);
            population.add(problem.getWeight(newSolution), newSolution);
            sc = 0;
         }
      }

      for(Pair<Double, S> p : population) {
         p.b.increaseIterationCount(iterations);
      }

      return population;
   }
}
