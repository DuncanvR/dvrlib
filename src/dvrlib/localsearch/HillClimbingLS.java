/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * HillClimbingLS.java
 */

package dvrlib.localsearch;

public class HillClimbingLS extends LocalSearch {
   protected final Changer changer;

   public HillClimbingLS(Changer changer) {
      this.changer = changer;
   }

   @Override
   public Solution search(Problem problem, Solution solution) {
      Object change = null;
      int iterations = 0;

      // Keep mutating as long as it improves the solution
      double e1, e2 = problem.evaluate(solution);
      do {
         e1 = e2;
         change = changer.generateChange(solution);
         changer.doChange(solution, change);
         e2 = problem.evaluate(solution);
         iterations++;
      }
      while(e2 < e1);

      // Undo last change
      changer.undoChange(solution, change);

      solution.setIterationCount(iterations - 1); // -1, because the last change is undone

      return solution;
   }
}
