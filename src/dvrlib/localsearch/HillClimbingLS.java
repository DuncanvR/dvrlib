/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * HillClimbingLS.java
 */

package dvrlib.localsearch;

public class HillClimbingLS<P extends Problem<S, E>, S extends Solution, E extends Comparable<E>> extends StatefulLocalSearch<P, S, E, SingularSearchState<P, S>> {
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
    * Searches for an optimal solution for the given problem, starting from the given solution, which is saved and returned.
    * This algorithm keeps generating changes for the solution until they no longer improve it.
    * @see HillClimbingLS#iterate(SingularSearchState, int)
    */
   @Override
   public S search(P problem, S solution) {
      SingularSearchState<P, S> state = newState(problem, solution);
      iterate(state, -1).saveSolution();
      state.solution.setIterationCount(state.iterationCount());
      return state.solution();
   }

   /**
    * Searches for an optimal solution using the given search state, with a maximum of <tt>n</tt> iterations, after which the state is returned.
    * A negative value of <tt>n</tt> indicates there is no limit to the number of iterations.
    * @see HillClimbingLS#iterate(Solution)
    */
   @Override
   public SingularSearchState<P, S> iterate(SingularSearchState<P, S> state, long n) {
      Changer<P, S, ?>.Change change = null;
      E e1 = null, e2 = state.problem.evaluate(state);
      int iterations = 1;

      try {
         // Keep mutating as long as it improves the solution and the maximum number of iterations has not been reached
         do {
            e1 = e2;
            change = changer.makeChange(state);
            e2 = state.problem.evaluate(state);
            state.iteration++;
         }
         while(state.problem.better(e2, e1) && (n < 0 || iterations++ < n));
      }
      catch(CannotChangeException _) { }

      if(state.problem.better(e1, e2)) // Undo last change
         change.undo(state);

      return state;
   }

   @Override
   public SingularSearchState<P, S> newState(P problem, S solution) {
      changer.reinitialize(problem);
      return new SingularSearchState<P, S>(problem, solution);
   }
}
