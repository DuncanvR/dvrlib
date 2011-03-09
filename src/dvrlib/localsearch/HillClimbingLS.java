/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * HillClimbingLS.java
 */

package dvrlib.localsearch;

public class HillClimbingLS<S extends Solution, E extends Comparable<E>> extends LocalSearch<S, E> {
   protected final Changer<S, Object> changer          ;
   protected       Object             lastChange = null;

   /**
    * HillClimbingLS constructor.
    * @param changer The changer used when searching for a solution.
    * O(1).
    */
   public HillClimbingLS(Changer<S, Object> changer) {
      this.changer = changer;
   }

   /**
    * Searches for a solution for the given problem, using hill climbing, which is saved and returned.
    * This algorithm keeps generating changes for the solution until they no longer improve it.
    * @see HillClimbingLS#iterate(dvrlib.localsearch.Problem, dvrlib.localsearch.Solution, int)
    */
   @Override
   public S search(Problem<S, E> problem, S solution) {
      return iterate(problem, solution, -1);
   }

   /**
    * Searches for a solution for the given problem, with a maximum of <tt>n</tt> iterations, which is saved and returned.
    * A negative value of <tt>n</tt> indicates there is no limit to the number of iterations.
    * @see HillClimbingLS#iterate(dvrlib.localsearch.Solution)
    */
   @Override
   public S iterate(Problem<S, E> problem, S solution, int n) {
      int iterations = 1;
      // Keep mutating as long as it improves the solution and the maximum number of iterations has not been reached
      E e1, e2 = problem.evaluate(solution);
      do {
         e1 = e2;
         e2 = problem.evaluate(iterate(solution));
      }
      while(problem.better(e2, e1) && (n < 0 || iterations++ < n));

      if(n < 0 || iterations <= n) {
         // Undo last change
         changer.undoChange(solution, lastChange);
         lastChange = null;
      }

      solution.increaseIterationCount(iterations);

      // Save and return solution
      problem.saveSolution(solution);
      return solution;
   }

   /**
    * Does one iteration on the given solution and returns it.
    */
   protected S iterate(S solution) {
      lastChange = changer.generateChange(solution);
      changer.doChange(solution, lastChange);
      return solution;
   }
}
