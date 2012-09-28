/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * SimulatedAnnealingLS.java
 */

package dvrlib.localsearch;

import java.util.LinkedList;

public class SimulatedAnnealingLS<S extends Solution, E extends Number & Comparable<E>> extends LocalSearch<S, E> {
   protected class SASearchState extends SingularSearchState<Problem<S, E>, S> {
      protected LinkedList<Object> changes     = new LinkedList<Object>();
      protected double             temperature;

      public SASearchState(Problem<S, E> problem, S solution) {
         super(problem, solution);
         temperature = initTemp;
         iteration   = 1; // Start at 1, otherwise the temperature would be decreased at the first iteration
      }

      public S bestSolution() {
         // Undo the changes since the last improvement, reverting to the best known solution
         increaseIterationCount(-changes.size());
         while(!changes.isEmpty())
            changer.undoChange(this, changes.pollLast());

         saveSolution();
         return solution;
      }
   }

   /** The default initial temperature is 2.4663035, so <tt>dE = 1</tt> results in a 2/3 chance of acceptance. */
   public final static double defTemp    = 2.4663035;
   /** The default temperature modifier is 0.98, leading to a 2% decrease in temperature every <tt>coolCount</tt> iterations. */
   public final static double defTempMod = 0.98;

   protected final Changer<Problem<S, E>, S, Object> changer;
   protected final int                               stopCount,
                                                     coolCount;
   protected final double                            initTemp,
                                                     tempMod;

   /**
    * SimulatedAnnealingLS constructor, using the default values for initTemp and tempMod.
    * @param stopCount  The algorithm will stop this number of iterations after the best known solution was found.
    * @param coolCount  The number of iterations after which the temperature will decrease.
    * @see SimulatedAnnealingLS#SimulatedAnnealingLS(dvrlib.localsearch.Changer, int, int, double, double)
    * O(1).
    */
   public SimulatedAnnealingLS(Changer<Problem<S, E>, S, Object> changer, int stopCount, int coolCount) {
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
   public SimulatedAnnealingLS(Changer<Problem<S, E>, S, Object> changer, int stopCount, int coolCount, double initTemp, double tempMod) {
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
      SASearchState state = newState(problem, solution);
      E curEval = problem.evaluate(state), bestEval = curEval;

      // Main loop: j holds the number of iterations since the last improvement
      for(int sc = 0; sc < stopCount; sc++, state.increaseIterationCount(1)) {
         // Mutate the solution
         state.changes.add(changer.generateChange(state));
         changer.doChange(state, state.changes.peekLast());
         E newEval = problem.evaluate(state);

         if(problem.better(newEval, curEval)) {
            sc = 0;
            curEval = newEval;
            if(problem.betterEq(newEval, bestEval)) {
               // If the new solution is better than the best solution found, clear the list of changes
               bestEval = newEval;
               state.changes.clear();
            }
         }
         else if(Math.random() < Math.exp(problem.diffEval(newEval, curEval).doubleValue() * problem.direction() / state.temperature)) {
            // Accept the change, even thought it's not an improvement
            curEval = newEval;
         }
         else
            changer.undoChange(state, state.changes.pollLast());

         // Decrease the temperature regularly
         if(state.iteration % coolCount == 0)
            state.temperature *= tempMod;
      }

      return state.bestSolution();
   }

   protected SASearchState newState(Problem<S, E> problem, S solution) {
      changer.reinitialize(problem);
      return new SASearchState(problem, solution);
   }
}
