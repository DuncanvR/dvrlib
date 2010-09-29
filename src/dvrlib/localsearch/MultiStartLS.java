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
    * @param ls    This LocalSearch will be used repeatedly to search for a solution.
    * @param count The number of times a solution is searched for.
    */
   public MultiStartLS(LocalSearch ls, int count) {
      this.ls    = ls;
      this.count = count;
   }

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
