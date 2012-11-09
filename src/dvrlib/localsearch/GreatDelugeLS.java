/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * GreatDelugeLS.java
 */

package dvrlib.localsearch;

// TODO: Fix implementation and remove abstract modifier
public abstract class GreatDelugeLS<P extends Problem<S, E>, S extends Solution, E extends Comparable<E>> extends StatefulLocalSearch<P, S, E, GreatDelugeLS<P, S, E>.SearchState> {
   public class SearchState extends SingularSearchState<P, S> {
      protected ChangeList<P, S> changes;
      protected E                tolerance;

      protected SearchState(P problem, S solution, E tolerance) {
         super(problem, solution);
         this.tolerance = tolerance;
      }
   }

   protected final Changer<P, S, ? extends Changer<P, S, ?>.Change> changer;
   protected final E                                                initTolerance;

   /**
    * GreatDelugeLS constructor.
    * @param changer The changer used when searching for a solution.
    * O(1).
    */
   public GreatDelugeLS(Changer<P, S, ? extends Changer<P, S, ?>.Change> changer, E initTolerance) {
      this.changer       = changer;
      this.initTolerance = initTolerance;
   }

   /**
    * Decays the tolerance value in the given state.
    */
   public abstract void decay(SearchState state);

   /**
    * Searches for an optimal solution for the given problem, starting from the given solution, which is saved and returned.
    * This algorithm keeps generating changes for the solution while it can be improved over the tolerance.
    * @see GreatDelugeLS#iterate(GreatDelugeLS.GDSearchState, int)
    */
   @Override
   public S search(P problem, S solution) {
      SearchState state = newState(problem, solution);
      iterate(state, -1).saveSolution(); // TODO: Determine a good stopping criterium for this LS method.
      state.solution.setIterationCount(state.iterationCount());
      return state.solution();
   }

   /**
    * Searches for an optimal solution using the given search state, with a maximum of <code>n</code> iterations, after which the state is returned.
    * @see GreatDelugeLS#iterate(Solution)
    */
   @Override
   public SearchState iterate(SearchState state, long n) {
      if(n < 0)
         throw new IllegalArgumentException("The number of requested operations should not be less than zero");

      E best = state.problem.evaluate(state), current;

      try {
         // Keep mutating as long as the maximum number of iterations has not been reached
         // TODO: Add another stopping criterium, in order to support n < 0
         for(int i = 0; i < n; i++, state.iteration++) {
            state.changes.add(changer.makeChange(state));
            current = state.problem.evaluate(state);

            if(savingCriterion == LocalSearch.SavingCriterion.EveryIteration)
               state.saveSolution();

            if(state.problem.better(current, state.tolerance)) { // Keep the change
               if(savingCriterion == LocalSearch.SavingCriterion.EveryImprovement)
                  state.saveSolution();

               if(state.problem.better(current, best)) {
                  best = current;
                  state.changes.clear();

                  if(savingCriterion == LocalSearch.SavingCriterion.NewBest)
                     state.saveSolution();
               }

               decay(state);
            }
            else // Revert the change
               state.changes.undoLast(state);
         }
      }
      catch(CannotChangeException _) { }

      // Undo the changes since the last improvement, reverting to the best known solution
      state.iteration -= state.changes.size();
      state.changes.undoAll(state);

      state.saveSolution();

      return state;
   }

   @Override
   public SearchState newState(P problem, S solution) {
      changer.reinitialize(problem);
      return new SearchState(problem, solution, initTolerance);
   }
}
