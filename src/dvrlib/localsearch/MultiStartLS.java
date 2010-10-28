/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * MultiStartLS.java
 */

package dvrlib.localsearch;

public class MultiStartLS extends LocalSearch {
   protected final LocalSearch ls;
   protected final int count;

   /**
    * MultiStartLS Constructor.
    * @param ls    The search algorithm that will be used repeatedly to search for a solution.
    * @param count The number of times to search for a solution.
    */
   public MultiStartLS(LocalSearch ls, int count) {
      this.ls    = ls;
      this.count = count;
   }

   /**
    * Searches for a solution for the given problem by repeatedly applying the predefined search algorithm.
    */
   @Override
   public Solution search(Problem problem, Solution bestSolution) {
      for(int i = 0; i < count; i++) {
         Solution newSolution = ls.search(problem);
         if(problem.evaluate(newSolution) < problem.evaluate(bestSolution))
            bestSolution = newSolution;
      }
      return bestSolution;
   }
}
