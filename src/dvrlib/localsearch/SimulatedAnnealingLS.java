/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2010-2013
 * SimulatedAnnealingLS.java
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dvrlib.localsearch;

public class SimulatedAnnealingLS<P extends Problem<S, E>, S extends Solution, E extends Number & Comparable<E>> extends LocalSearch<P, S, E> {
   public class SearchState extends SingularSearchState<P, S> {
      protected ChangeList<P, S> changes     = new ChangeList<P, S>();
      protected double           temperature = initTemp;

      public SearchState(P problem, S solution) {
         super(problem, solution);
         iteration = 1; // Start at 1, otherwise the temperature would be decreased at the first iteration
      }
   }

   /** The default number of retries attempted is 5. */
   public final static int defaultRetryCount = 5;
   /** The default initial temperature is 2.4663035, so <code>dE = 1</code> results in a 2/3 chance of acceptance. */
   public final static double defaultTemp    = 2.4663035;
   /** The default temperature modifier is 0.98, leading to a 2% decrease in temperature every <code>coolCount</code> iterations. */
   public final static double defaultTempMod = 0.98;

   protected final Changer<P, S, ? extends Changer<P, S, ?>.Change> changer;
   protected final int                                              coolCount,
                                                                    retryCount,
                                                                    stopCount;
   protected final double                                           initTemp,
                                                                    tempMod;

   /**
    * SimulatedAnnealingLS constructor, using the default values for initTemp and tempMod.
    * @param changer   The changer that is used to make changes to the solution during the search.
    * @param stopCount The number of iterations after which the algorithm will retry after the best known solution was found.
    * @param coolCount The number of iterations after which the temperature will decrease.
    * @see SimulatedAnnealingLS#SimulatedAnnealingLS(Changer, int, int, double, double)
    * O(1).
    */
   public SimulatedAnnealingLS(Changer<P, S, ? extends Changer<P, S, ?>.Change> changer, int stopCount, int coolCount) {
      this(changer, stopCount, coolCount, defaultTemp);
   }

   /**
    * SimulatedAnnealingLS constructor, using the default value for tempMod.
    * @param changer   The changer that is used to make changes to the solution during the search.
    * @param stopCount The number of iterations after which the algorithm will retry after the best known solution was found.
    * @param coolCount The number of iterations after which the temperature will decrease.
    * @param initTemp  The initial temperature.
    * @see SimulatedAnnealingLS#SimulatedAnnealingLS(Changer, int, int, double, double)
    * O(1).
    */
   public SimulatedAnnealingLS(Changer<P, S, ? extends Changer<P, S, ?>.Change> changer, int stopCount, int coolCount, double initTemp) {
      this(changer, stopCount, coolCount, defaultRetryCount, initTemp, defaultTempMod);
   }

   /**
    * SimulatedAnnealingLS constructor.
    * @param changer    The changer that is used to make changes to the solution during the search.
    * @param stopCount  The number of iterations after which the algorithm will retry after the best known solution was found.
    * @param coolCount  The number of iterations after which the temperature will decrease.
    * @param retryCount The number of retries that will be attempted.
    * @param initTemp   The initial temperature.
    * @param tempMod    The modifier for the temperature. Every <code>coolCount</code> iterations the temperature is multiplied by this value, so its value should be in the range [0,1].
    * O(1).
    */
   public SimulatedAnnealingLS(Changer<P, S, ? extends Changer<P, S, ?>.Change> changer, int stopCount, int coolCount, int retryCount, double initTemp, double tempMod) {
      this.changer    = changer;
      this.stopCount  = stopCount;
      this.coolCount  = coolCount;
      this.retryCount = retryCount;
      this.initTemp   = initTemp;
      this.tempMod    = tempMod;
   }

   /**
    * Searches for a solution for the given problem, starting from the given solution.
    * This algorithm repeatedly generates changes for the current solution and applies them if they improve it.
    * If a change does not improve the solution, it is applied with a certain chance based on the current temperature, which decreases as time passes by.
    * After <code>stopCount</code> iterations in which no improvements were found, all changes since the best known solution are undone, and the algorithm tries again.
    * After <code>retryCount</code> of retries, the best know solution is returned.
    * When a solution is found that is better or equal to the given bound, the search is stopped.
    */
   @Override
   protected S doSearch(P problem, E bound, S solution) {
      SearchState state = newState(problem, solution);
      E curEval  = problem.evaluate(state),
        bestEval = curEval;

      for(int r = 0; r < retryCount && !problem.betterEq(bestEval, bound); r++) {
         boolean improved = false;
         try {
            for(int sc = 0; sc < stopCount && !problem.betterEq(bestEval, bound); sc++, state.iteration++) {
               // Mutate the solution
               state.changes.add(changer.makeChange(state));
               E newEval = problem.evaluate(state);

               if(savingCriterion == LocalSearch.SavingCriterion.EveryIteration)
                  state.saveSolution();

               if(problem.better(newEval, curEval)) {
                  sc = 0;
                  curEval = newEval;

                  if(savingCriterion == LocalSearch.SavingCriterion.EveryImprovement)
                     state.saveSolution();

                  // If the new solution is at least as good as the best solution found, clear the list of changes
                  if(problem.betterEq(newEval, bestEval)) {
                     state.changes.clear();

                     if(problem.better(newEval, bestEval)) {
                        improved = true;
                        bestEval = newEval;
                        if(savingCriterion == LocalSearch.SavingCriterion.NewBest)
                           state.saveSolution();
                     }
                  }
               }
               else if(Math.random() < Math.exp((newEval.doubleValue() - curEval.doubleValue()) * LocalSearch.asNumber(problem.direction()) / state.temperature)) {
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

         if(improved)
            r = -1;
      }

      state.saveSolution();
      return state.solution();
   }

   protected SearchState newState(P problem, S solution) {
      changer.reinitialise(problem);
      return new SearchState(problem, solution);
   }
}
