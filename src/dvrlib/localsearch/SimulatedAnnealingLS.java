/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * SimulatedAnnealingLS.java
 */

package dvrlib.localsearch;

public class SimulatedAnnealingLS<P extends NumericProblem<S, E>, S extends Solution, E extends Number & Comparable<E>> extends LocalSearch<P, S, E> {
   public class SearchState extends SingularSearchState<P, S> {
      protected ChangeList<P, S> changes     = new ChangeList<P, S>();
      protected double           temperature = initTemp;

      public SearchState(P problem, S solution) {
         super(problem, solution);
         iteration = 1; // Start at 1, otherwise the temperature would be decreased at the first iteration
      }
   }

   /** The default initial temperature is 2.4663035, so <tt>dE = 1</tt> results in a 2/3 chance of acceptance. */
   public final static double defTemp    = 2.4663035;
   /** The default temperature modifier is 0.98, leading to a 2% decrease in temperature every <tt>coolCount</tt> iterations. */
   public final static double defTempMod = 0.98;

   protected final Changer<P, S, ? extends Changer<P, S, ?>.Change> changer;
   protected final int                                              stopCount,
                                                                    coolCount;
   protected final double                                           initTemp,
                                                                    tempMod;

   /**
    * SimulatedAnnealingLS constructor, using the default values for initTemp and tempMod.
    * @param changer   The changer that is used to make changes to the solution during the search.
    * @param stopCount The algorithm will stop this number of iterations after the best known solution was found.
    * @param coolCount The number of iterations after which the temperature will decrease.
    * @see SimulatedAnnealingLS#SimulatedAnnealingLS(Changer, int, int, double, double)
    * O(1).
    */
   public SimulatedAnnealingLS(Changer<P, S, ? extends Changer<P, S, ?>.Change> changer, int stopCount, int coolCount) {
      this(changer, stopCount, coolCount, defTemp, defTempMod);
   }

   /**
    * SimulatedAnnealingLS constructor, using the default value for tempMod.
    * @param changer   The changer that is used to make changes to the solution during the search.
    * @param stopCount The number of iterations after which the algorithm will stop after the best known solution was found.
    * @param coolCount The number of iterations after which the temperature will decrease.
    * @param initTemp  The initial temperature.
    * @see SimulatedAnnealingLS#SimulatedAnnealingLS(Changer, int, int, double, double)
    * O(1).
    */
   public SimulatedAnnealingLS(Changer<P, S, ? extends Changer<P, S, ?>.Change> changer, int stopCount, int coolCount, double initTemp) {
      this(changer, stopCount, coolCount, initTemp, defTempMod);
   }

   /**
    * SimulatedAnnealingLS constructor.
    * @param changer   The changer that is used to make changes to the solution during the search.
    * @param stopCount The number of iterations after which the algorithm will stop after the best known solution was found.
    * @param coolCount The number of iterations after which the temperature will decrease.
    * @param initTemp  The initial temperature.
    * @param tempMod   The modifier for the temperature. Every <tt>coolCount</tt> iterations the temperature is multiplied by this value, so its value should be in the range [0,1].
    * O(1).
    */
   public SimulatedAnnealingLS(Changer<P, S, ? extends Changer<P, S, ?>.Change> changer, int stopCount, int coolCount, double initTemp, double tempMod) {
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
    * After <tt>stopCount</tt> iterations in which no improvements were found, the best known solution is returned.
    */
   @Override
   public S search(P problem, S solution) {
      SearchState state = newState(problem, solution);
      E curEval = problem.evaluate(state), bestEval = curEval;

      try {
         // Main loop: j holds the number of iterations since the last improvement
         for(int sc = 0; sc < stopCount; sc++, state.iteration++) {
            // Mutate the solution
            state.changes.add(changer.makeChange(state));
            E newEval = problem.evaluate(state);

            if(savingCriterion == LocalSearch.SavingCriterion.EveryIteration)
               state.saveSolution();

            if(problem.better(newEval, curEval)) {
               sc = 0;
               curEval = newEval;
               if(problem.betterEq(newEval, bestEval)) {
                  // If the new solution is better than the best solution found, clear the list of changes
                  bestEval = newEval;
                  state.changes.clear();

                  if(savingCriterion == LocalSearch.SavingCriterion.NewBest)
                     state.saveSolution();
               }
            }
            else if(Math.random() < Math.exp(problem.diffEval(newEval, curEval) * problem.direction() / state.temperature)) {
               // Accept the change, even though it's not an improvement
               curEval = newEval;
            }
            else
               state.changes.undoLast(state);

            // Decrease the temperature according to the schedule
            if(state.iteration % coolCount == 0)
               state.temperature *= tempMod;
         }
      }
      catch(CannotChangeException _) { }

      // Undo the changes since the last improvement, reverting to the best known solution
      state.iteration -= state.changes.size();
      state.changes.undoAll(state);

      state.saveSolution();
      return state.solution();
   }

   protected SearchState newState(P problem, S solution) {
      changer.reinitialize(problem);
      return new SearchState(problem, solution);
   }
}
