/*
 * DvRlib - Local search
 * Duncan van Roermund, 2010-2012
 * HillClimbingLS.java
 */

package dvrlib.localsearch;

public class HillClimbingLS<P extends BoundableProblem<S, E>, S extends Solution, E extends Comparable<E>> extends StatefulLocalSearch<P, S, E, SingularSearchState<P, S>> {
   protected final Changer<P, S, ? extends Changer<P, S, ?>.Change> changer;

   /**
    * HillClimbingLS constructor.
    * @param changer The changer used when searching for a solution.
    * O(1).
    */
   public HillClimbingLS(Changer<P, S, ? extends Changer<P, S, ?>.Change> changer) {
      this.changer = changer;
   }

   /**
    * Searches for a solution for the given problem, starting from the given solution, which is saved and returned.
    * This algorithm keeps generating changes for the solution until they no longer improve it.
    * @see HillClimbingLS#iterate(SingularSearchState, Comparable, int)
    * @see LocalSearch#search(Problem, Comparable, Solution)
    */
   @Override
   protected S doSearch(P problem, E bound, S solution) {
      SingularSearchState<P, S> state = newState(problem, solution);
      iterate(state, bound, -1).saveSolution();
      state.solution.setIterationCount(state.iterationCount());
      return state.solution();
   }

   /**
    * Searches for an optimal solution using the given search state, with a maximum of <code>n</code> iterations,
    *    after which the state is returned.
    * A negative value of <code>n</code> indicates there is no limit to the number of iterations.
    * When a solution is found that is better or equal to the given bound, the search is stopped.
    * @see StatefulLocalSearch#iterate(SearchState, Comparable, int)
    */
   @Override
   public SingularSearchState<P, S> iterate(SingularSearchState<P, S> state, E bound, long n) {
      Changer<P, S, ?>.Change change = null;
      E eOld = null, eNew = state.problem.evaluate(state);

      try {
         // Keep mutating as long as it improves the solution and the maximum number of iterations has not been reached
         for(long iMax = state.iteration + n; n < 0 || state.iteration < iMax; state.iteration++) {
            eOld = eNew;
            // Change the solution
            change = changer.makeChange(state);

            // If the solution cannot be better that the current, break here
            if(!state.problem.better(state.problem.evaluationBound(state), eOld))
               break;

            // Evaluate the solution
            eNew = state.problem.evaluate(state);

            // Depending on the saving criterion, save the current solution
            if(savingCriterion == LocalSearch.SavingCriterion.EveryIteration)
               state.saveSolution();

            // If the solution is either no better than the current, or better than or equal to the target value, break here
            if(!state.problem.better(eNew, eOld) || state.problem.betterEq(eNew, bound))
               break;

            // Depending on the saving criterion, save the current solution
            if(savingCriterion == LocalSearch.SavingCriterion.EveryImprovement || savingCriterion == LocalSearch.SavingCriterion.NewBest)
               state.saveSolution();
         }
      }
      catch(CannotChangeException _) { }

      // Undo the last change
      if(state.problem.better(eOld, eNew))
         change.undo(state);

      return state;
   }

   @Override
   public SingularSearchState<P, S> newState(P problem, S solution) {
      changer.reinitialise(problem);
      return new SingularSearchState<P, S>(problem, solution);
   }
}
