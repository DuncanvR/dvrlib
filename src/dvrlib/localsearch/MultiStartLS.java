/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * MultiStartLS.java
 */

package dvrlib.localsearch;

public class MultiStartLS<S extends Solution, E extends Comparable<E>> extends LocalSearch<Problem<S, E>, S, E> {
   protected LocalSearch<Problem<S, E>, S, E> ls;
   protected int count;

   /**
    * MultiStartLS Constructor.
    * @param ls    The search algorithm that will be used repeatedly to search for a solution.
    * @param count The number of times to search for a solution.
    */
   public MultiStartLS(LocalSearch<Problem<S, E>, S, E> ls, int count) {
      this.ls    = ls;
      this.count = count;
   }

   /**
    * Searches for a solution for the given problem by applying the predefined search algorithm to multiple random solution.
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
