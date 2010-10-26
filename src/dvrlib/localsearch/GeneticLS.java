/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * GeneticLS.java
 */

package dvrlib.localsearch;

import dvrlib.generic.HalfComparablePair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GeneticLS extends LocalSearch {
   protected final Combiner combiner;
   protected final int populationSize, stopCount;

   public GeneticLS(Combiner combiner, int populationSize, int stopCount) {
      this.combiner = combiner;
      this.populationSize = Math.max(1, populationSize);
      this.stopCount = stopCount;
   }

   @Override
   public Solution search(Problem problem, Solution solution) {
      return search(problem, solution, populationSize).get(0).b;
   }

   public ArrayList<HalfComparablePair<Double, Solution>> search(Problem problem, Solution solution, int size) {
      ArrayList<HalfComparablePair<Double, Solution>> population = new ArrayList<HalfComparablePair<Double, Solution>>();
      population.ensureCapacity(populationSize);
      population.add(new HalfComparablePair(problem.evaluate(solution), solution));

      // Initialize population with random solutions
      for(int i = 1; i < populationSize; i++) {
         Solution s = problem.randomSolution();
         double eval = problem.evaluate(s);
         population.add(new HalfComparablePair(eval, s));
      }
      Collections.sort(population);

      Random r = new Random();
      int iterations = 0;
      for(int sc = 0; sc < stopCount; iterations++, sc++) {
         // Pick two solutions from the population
         int i = r.nextInt(populationSize), j;
         do {
            j = r.nextInt(populationSize);
         }
         while(i == j);

         // Generate new solution
         Solution newSolution = combiner.combine(population.get(i).b, population.get(j).b);
         combiner.mutate(newSolution);
         double newEval = problem.evaluate(newSolution);

         // Compare new solution with the worst
         HalfComparablePair<Double, Solution> worst = population.get(populationSize - 1);
         double worstEval = worst.a;
         if(newEval < worstEval) {
            population.set(populationSize - 1, new HalfComparablePair(newEval, newSolution));
            Collections.sort(population);
            sc = 0;
         }
      }

      for(HalfComparablePair<Double, Solution> p : population) {
         p.b.increaseIterationCount(iterations);
      }
      
      return population;
   }
}
