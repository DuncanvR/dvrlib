/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * SimulatedAnnealingLS.java
 */

package dvrlib.localsearch;

public class SimulatedAnnealingLS extends LocalSearch {
   /** The default initial temperature is 2.4663035, so dE = 1 results in a 2/3 chance of acceptance. */
   public final static double defTemp = 2.4663035;
   /** The default temperature modifier is 0.98, leading to a 2% decrease in temperature every coolCount iterations. */
   public final static double defTempMod = 0.98;

   protected final Changer changer;
   protected final int stopCount, coolCount;
   protected final double initTemp, tempMod;

   /**
    * SimulatedAnnealingLS constructor, using the default values for initTemp and tempMod.
    * @param stopCount  The algorithm will stop this number of iterations after the current best Solution was found.
    * @param coolCount  The number of iterations after which the temperature will decrease.
    * @see SimulatedAnnealingLS#SimulatedAnnealingLS(dvrlib.localsearch.Changer, int, int, double, double)
    * O(1).
    */
   public SimulatedAnnealingLS(Changer changer, int stopCount, int coolCount) {
      this(changer, stopCount, coolCount, defTemp, defTempMod);
   }

   /**
    * SimulatedAnnealingLS constructor.
    * @param stopCount  The algorithm will stop this number of iterations after the current best Solution was found.
    * @param coolCount  The number of iterations after which the temperature will decrease.
    * @param initTemp   The initial temperature.
    * @param tempMod    The modifier for the temperature. Every coolCount iterations the temperature is multiplied with this value.
    * O(1).
    */
   public SimulatedAnnealingLS(Changer changer, int stopCount, int coolCount, double initTemp, double tempMod) {
      this.changer   = changer;
      this.stopCount = stopCount;
      this.coolCount = coolCount;
      this.initTemp  = initTemp;
      this.tempMod   = tempMod;
   }

   @Override
   public Solution search(Problem problem, Solution solution) {
      double temperature = initTemp, curEval = problem.evaluate(solution);

      // Main loop: i holds the total number of iterations; j holds the number of iterations since the last improvement
      int iterations = 1, j = 0;
      for(; j < stopCount; iterations++, j++) {
         // Mutate the solution
         Object change = changer.generateChange(solution);
         changer.doChange(solution, change);

         // Calculate the difference in evaluation
         double newEval = problem.evaluate(solution), deltaE = newEval - curEval;

         if(deltaE <= 0 || Math.random() < Math.exp(-deltaE / temperature)) {
            curEval = newEval;
            // Reset stop-counter if new solution is better
            if(deltaE < 0)
               j = 0;
         }
         else {
            // Undo the mutation
            changer.undoChange(solution, change);
         }

         // Decrease the temperature regularly
         if(iterations % coolCount == 0)
            temperature *= tempMod;
      }

      solution.setIterationCount(iterations - 1); // -1, because iterations starts at 1

      return solution;
   }
}
