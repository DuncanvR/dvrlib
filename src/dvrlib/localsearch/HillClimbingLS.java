/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * HillClimbingLS.java
 */

package dvrlib.localsearch;

public class HillClimbingLS<S extends Solution, E extends Comparable<E>> extends StatefulLocalSearch<Problem<S, E>, S, E, SingularSearchState<Problem<S, E>, S>> {
   protected final Changer<Problem<S, E>, S, Object> changer;

   /**
    * HillClimbingLS constructor.
    * @param changer The changer used when searching for a solution.
    * O(1).
    */
   public HillClimbingLS(Changer<Problem<S, E>, S, Object> changer) {
      this.changer = changer;
   }

   /**
    * Searches for an optimal solution for the given problem, starting from the given solution, which is saved and returned.
    * This algorithm keeps generating changes for the solution until they no longer improve it.
    * @see HillClimbingLS#iterate(SingularSearchState, int)
    */
   @Override
   public S search(Problem<S, E> problem, S solution) {
      SingularSearchState<Problem<S, E>, S> state = newState(problem, solution);
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
   public SingularSearchState<Problem<S, E>, S> iterate(SingularSearchState<Problem<S, E>, S> state, long n) {
      Object change;
      E e1, e2 = state.problem.evaluate(state);
      int iterations = 1;

      // Keep mutating as long as it improves the solution and the maximum number of iterations has not been reached
      do {
         e1 = e2;
         change = changer.generateChange(state);
         changer.doChange(state, change);
         e2 = state.problem.evaluate(state);
         state.increaseIterationCount(1);
      }
      while(state.problem.better(e2, e1) && (n < 0 || iterations++ < n));

      if(state.problem.better(e1, e2)) // Undo last change
         changer.undoChange(state, change);

      return state;
   }

   @Override
   public SingularSearchState<Problem<S, E>, S> newState(Problem<S, E> problem, S solution) {
      changer.reinitialize(problem);
      return new SingularSearchState<Problem<S, E>, S>(problem, solution);
   }
}
