/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * MultiStartLS.java
 */

package dvrlib.localsearch;

public class MultiStartLS<S extends Solution, E extends Comparable<E>> extends LocalSearch<S, E> {
   protected final LocalSearch<S, E> ls;
   protected final int count;

   /**
    * MultiStartLS Constructor.
    * @param ls    The search algorithm that will be used repeatedly to search for a solution.
    * @param count The number of times to search for a solution.
    */
   public MultiStartLS(LocalSearch<S, E> ls, int count) {
      this.ls    = ls;
      this.count = count;
   }

   /**
    * Searches for a solution for the given problem by repeatedly applying the predefined search algorithm to a random solution.
    */
   @Override
   public S search(Problem<S, E> problem) {
      S bestSolution = null;
      for(int i = 0; i < count; i++) {
         S newSolution = ls.search(problem);
         if(problem.better(newSolution, bestSolution))
            bestSolution = newSolution;
      }
      return bestSolution;
   }

   /**
    * Searches for a solution for the given problem by repeatedly applying the predefined search algorithm to the given solution.
    */
   @Override
   public S search(Problem<S, E> problem, S startSolution) {
      S bestSolution = startSolution;
      for(int i = 0; i < count; i++) {
         S newSolution = ls.search(problem, startSolution);
         if(problem.better(newSolution, bestSolution))
            bestSolution = newSolution;
      }
      return bestSolution;
   }
}
