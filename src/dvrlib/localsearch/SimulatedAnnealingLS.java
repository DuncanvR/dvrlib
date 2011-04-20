/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * SimulatedAnnealingLS.java
 */

package dvrlib.localsearch;

import java.util.LinkedList;

public class SimulatedAnnealingLS<S extends Solution, E extends Number & Comparable<E>> extends LocalSearch<S, E> {
   /** The default initial temperature is 2.4663035, so <tt>dE = 1</tt> results in a 2/3 chance of acceptance. */
   public final static double defTemp    = 2.4663035;
   /** The default temperature modifier is 0.98, leading to a 2% decrease in temperature every <tt>coolCount</tt> iterations. */
   public final static double defTempMod = 0.98     ;

   protected final Changer<S, Object> changer  ;
   protected final int                stopCount,
                                      coolCount;
   protected final double             initTemp ,
                                      tempMod  ;

   /**
    * SimulatedAnnealingLS constructor, using the default values for initTemp and tempMod.
    * @param stopCount  The algorithm will stop this number of iterations after the best known solution was found.
    * @param coolCount  The number of iterations after which the temperature will decrease.
    * @see SimulatedAnnealingLS#SimulatedAnnealingLS(dvrlib.localsearch.Changer, int, int, double, double)
    * O(1).
    */
   public SimulatedAnnealingLS(Changer<S, Object> changer, int stopCount, int coolCount) {
      this(changer, stopCount, coolCount, defTemp, defTempMod);
   }

   /**
    * SimulatedAnnealingLS constructor.
    * @param stopCount  The number of iterations after which the algorithm will stop after the best known solution was found.
    * @param coolCount  The number of iterations after which the temperature will decrease.
    * @param initTemp   The initial temperature.
    * @param tempMod    The modifier for the temperature. Every <tt>coolCount</tt> iterations the temperature is multiplied with this value.
    * O(1).
    */
   public SimulatedAnnealingLS(Changer<S, Object> changer, int stopCount, int coolCount, double initTemp, double tempMod) {
      this.changer   = changer;
      this.stopCount = stopCount;
      this.coolCount = coolCount;
      this.initTemp  = initTemp;
      this.tempMod   = tempMod;
   }

   /**
    * Searches for a solution for the given problem, starting from the given solution.
    * This algorithm repeatedly generates changes for the current solution and applies them if they improve it.
    * If a change does not improve the solution, it is applied with a certain chance based on the current temperature, which decreases as time passes by.
    * After a predefined number of iterations in which no improvements were found, the best known solution is returned.
    * @see SimulatedAnnealingLS#iterate(dvrlib.localsearch.Problem, dvrlib.localsearch.Solution, int)
    */
   @Override
   public S search(Problem<S, E> problem, S solution) {
      long iterations = 1; // Starts at 1, otherwise the temperature would be decreased at the first iteration
      double temperature = initTemp;
      E curEval = problem.evaluate(solution, iterations), bestEval = curEval;
      LinkedList<Object> changeList = new LinkedList();

      // Main loop: j holds the number of iterations since the last improvement
      for(int sc = 0; sc < stopCount; iterations++, sc++) {
         // Mutate the solution
         changeList.add(changer.generateChange(solution));
         changer.doChange(solution, changeList.peekLast());

         // Calculate the difference in evaluation
         E newEval = problem.evaluate(solution, iterations);
         double deltaE = problem.diffEval(newEval, curEval).doubleValue();

         if(deltaE <= 0 || Math.random() < Math.exp(-deltaE / temperature)) {
            curEval = newEval;
            // If new solution is better, reset stop-counter
            if(deltaE < 0) {
               sc = 0;
               // If new solution is better that the best solution found, clear list of changes
               if(problem.betterEq(newEval, bestEval)) {
                  bestEval = newEval;
                  changeList.clear();
               }
            }
         }
         else {
            // Undo the mutation
            changer.undoChange(solution, changeList.pollLast());
         }

         // Decrease the temperature regularly
         if(iterations % coolCount == 0)
            temperature *= tempMod;
      }

      // Undo the changes since the last improvement, reverting to the best known solution
      while(!changeList.isEmpty())
         changer.undoChange(solution, changeList.pollLast());

      solution.setIterationCount(solution.getIterationCount() + iterations - 1);
      problem.saveSolution(solution);
      return solution;
   }

   /**
    * Unsupported by SimulatedAnnealingLS.
    */
   @Override
   public S iterate(Problem<S, E> problem, S solution, int n) {
      throw new UnsupportedOperationException("LocalSearch#iterate(Problem, Solution, int) is not supported by SimulatedAnnealingLS.");
   }
}
