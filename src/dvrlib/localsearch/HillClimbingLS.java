/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * HillClimbingLS.java
 */

package dvrlib.localsearch;

public class HillClimbingLS extends LocalSearch {
   protected final Changer changer;

   /**
    * HillClimbingLS constructor.
    * @param changer The changer used when searching for a solution.
    * O(1).
    */
   public HillClimbingLS(Changer changer) {
      this.changer = changer;
   }

   /**
    * Search for a solution for the given problem, using hill climbing.
    * This algorithm keeps generating changes to the solution until they no longer improve it.
    * @return The solution that was reached.
    */
   @Override
   public Solution search(Problem problem, Solution solution) {
      Object change = null;

      // Keep mutating as long as it improves the solution
      double e1, e2 = problem.evaluate(solution);
      do {
         e1 = e2;
         change = changer.generateChange(solution);
         changer.doChange(solution, change);
         solution.increaseIterationCount(1);
         e2 = problem.evaluate(solution);
      }
      while(e2 < e1);

      // Undo last change
      changer.undoChange(solution, change);

      problem.saveSolution(solution);
      return solution;
   }
}
