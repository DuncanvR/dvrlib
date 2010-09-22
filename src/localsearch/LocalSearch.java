/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * LocalSearch.java
 */

package dvrlib.localsearch;

public abstract class LocalSearch {
   /**
    * Search for a Solution for the given Problem, starting with a random Solution.
    * @see LocalSearch#search(localsearch.Problem, localsearch.Solution)
    */
   public Solution search(Problem problem) {
      return search(problem, problem.randomSolution());
   }

   /**
    * Search for a Solution for the given Problem, starting with the given Solution.
    */
   public abstract Solution search(Problem problem, Solution solution);
}
