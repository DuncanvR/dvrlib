/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * GreatDelugeLS.java
 */

package dvrlib.localsearch;

public abstract class GreatDelugeLS<S extends Solution, E extends Comparable<E>> extends StatefulLocalSearch<S, E, GreatDelugeLS.GDSearchState<S, E>> {
   public static class GDSearchState<S extends Solution, E extends Comparable<E>> extends SingularSearchState<Problem<S, E>, S> {
      protected E tolerance;
      protected GDSearchState(Problem<S, E> problem, S solution, E tolerance) {
         super(problem, solution);
         this.tolerance = tolerance;
      }
   }
   protected final Changer<Problem<S, E>, S, Object> changer;
   protected final E                                 initTolerance;

   /**
    * GreatDelugeLS constructor.
    * @param changer The changer used when searching for a solution.
    * O(1).
    */
   public GreatDelugeLS(Changer<Problem<S, E>, S, Object> changer, E initTolerance) {
      this.changer       = changer;
      this.initTolerance = initTolerance;
   }

   /**
    * Decays the tolerance value in the given state.
    */
   public abstract void decay(GDSearchState<S, E> state);

   /**
    * Searches for an optimal solution for the given problem, starting from the given solution, which is saved and returned.
    * This algorithm keeps generating changes for the solution while it can be improved over the tolerance.
    * @see GreatDelugeLS#iterate(dvrlib.localsearch.SingularSearchState, int)
    */
   @Override
   public S search(Problem<S, E> problem, S solution) {
      GDSearchState<S, E> state = newState(problem, solution);
      iterate(state, -1).saveSolution(); // TODO: Determine a good stopping criterium for this LS method.
      state.solution.setIterationCount(state.getIterationNumber());
      return state.getSolution();
   }

   /**
    * Searches for an optimal solution using the given search state, with a maximum of <tt>n</tt> iterations, after which the state is returned.
    * @see GreatDelugeLS#iterate(dvrlib.localsearch.Solution)
    */
   @Override
   public GDSearchState<S, E> iterate(GDSearchState<S, E> state, long n) {
      if(n < 0)
         throw new IllegalArgumentException("The number of requested operations should not be less than zero");

      Object change = null;
      E e1 = null, e2 = state.problem.evaluate(state);

      // Keep mutating as long as the maximum number of iterations has not been reached
      // TODO: Add another stopping criterium
      for(int i = 0; i < n; i++) {
         change = changer.generateChange(state);
         changer.doChange(state, change);
         e2 = state.problem.evaluate(state);

         if(state.problem.better(e2, state.tolerance)) { // Keep the change
            e1 = e2;
            decay(state);
         }
         else // Revert the change
            changer.undoChange(state, change);

         state.increaseIterationCount(1);
      }

      if(state.problem.better(e1, e2)) // Undo last change
         changer.undoChange(state, change);

      return state;
   }

   @Override
   public GDSearchState<S, E> newState(Problem<S, E> problem, S solution) {
      changer.reinitialize(problem);
      return new GDSearchState(problem, solution, initTolerance);
   }
}
