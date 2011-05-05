/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * HillClimbingLS.java
 */

package dvrlib.localsearch;

public class HillClimbingLS<S extends Solution, E extends Comparable<E>> extends StatefulLocalSearch<S, E, SingularSearchState<Problem<S, E>, S>> {
   protected final Changer<Problem<S, E>, S, Object> changer          ;
   protected       Object                            lastChange = null;

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
    * @see HillClimbingLS#iterate(dvrlib.localsearch.SingularSearchState, int)
    */
   @Override
   public S search(Problem<S, E> problem, S solution) {
      changer.reinitialize();
      SingularSearchState<Problem<S, E>, S> state = newState(problem, solution);
      iterate(state, -1).saveSolution();
      return state.getSolution();
   }

   /**
    * Searches for an optimal solution using the given search state, with a maximum of <tt>n</tt> iterations, after which the state is returned.
    * A negative value of <tt>n</tt> indicates there is no limit to the number of iterations.
    * @see HillClimbingLS#iterate(dvrlib.localsearch.Solution)
    */
   @Override
   public SingularSearchState<Problem<S, E>, S> iterate(SingularSearchState<Problem<S, E>, S> state, long n) {
      int iterations = 1;
      // Keep mutating as long as it improves the solution and the maximum number of iterations has not been reached
      E e1, e2 = state.problem.evaluate(state);
      do {
         e1 = e2;
         lastChange = changer.generateChange(state);
         changer.doChange(state, lastChange);
         e2 = state.problem.evaluate(state);
      }
      while(state.problem.better(e2, e1) && (n < 0 || iterations++ < n));

      if(n < 0 || iterations <= n) {
         // Undo last change
         changer.undoChange(state, lastChange);
         lastChange = null;
      }

      state.solution.setIterationCount(state.getIterationNumber());
      return state;
   }

   @Override
   public SingularSearchState<Problem<S, E>, S> newState(Problem<S, E> problem, S solution) {
      return new SingularSearchState(problem, solution);
   }
}
