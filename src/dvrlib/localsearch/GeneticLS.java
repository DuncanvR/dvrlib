/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * GeneticLS.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
import java.util.Random;

public class GeneticLS extends LocalSearch {
   protected final Combiner combiner;
   protected final int populationSize, stopCount;

   /**
    * GeneticLS constructor.
    * @param combiner       The combiner used to combine solutions when searching for a solution.
    * @param populationSize The number of solutions kept in the population.
    * @param stopCount      The number of iterations in which no better solution was found after which the algorithm will stop.
    */
   public GeneticLS(Combiner combiner, int populationSize, int stopCount) {
      this.combiner = combiner;
      this.populationSize = Math.max(1, populationSize);
      this.stopCount = stopCount;
   }

   /**
    * Searches for a solution for the given problem.
    * @see GeneticLS#search(dvrlib.localsearch.Problem, dvrlib.localsearch.Solution, int)
    */
   @Override
   public Solution search(Problem problem, Solution solution) {
      return search(problem, solution, populationSize).peekMin().b;
   }

   /**
    * Returns the population after searching for a solution for the given problem.
    * This algorithm keeps replacing the worst solution in the population by the new combined solution if it is better, until a predefined number of iterations give no improvement.
    */
   public WeightedTree<Solution> search(Problem problem, Solution solution, int size) {
      WeightedTree<Solution> population = new WeightedTree<Solution>();
      population.add(problem.evaluate(solution), solution);

      // Initialize population with random solutions
      for(int i = 1; i < populationSize; i++) {
         Solution s = problem.randomSolution();
         s.ensureMostCommon(solution);
         population.add(problem.evaluate(s), s);
      }

      Random r = new Random();
      int iterations = 0;
      for(int sc = 0; sc < stopCount; iterations++, sc++) {
         // Generate new solution from two solutions in the population
         Solution newSolution = combiner.combine(population.getWeighted(r.nextDouble()).b, population.getWeighted(r.nextDouble()).b);
         combiner.mutate(newSolution);
         double newEval = problem.evaluate(newSolution);

         // Compare new solution with the worst
         WeightedTreeNode<Solution> worst = population.getMax();
         if(newEval < worst.key) {
            population.remove(worst);
            population.add(newEval, newSolution);
            sc = 0;
         }
      }

      for(Pair<Double, Solution> p : population) {
         p.b.increaseIterationCount(iterations);
      }
      
      return population;
   }
}
