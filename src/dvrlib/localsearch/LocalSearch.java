/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * LocalSearch.java
 */

package dvrlib.localsearch;

public abstract class LocalSearch<P extends Problem<S>, S extends Solution> {
   /**
    * Search for a solution for the given problem, starting from a random solution.
    * @see LocalSearch#search(dvrlib.localsearch.Problem, dvrlib.localsearch.Solution)
    */
   public S search(P problem) {
      return search(problem, problem.randomSolution());
   }

   /**
    * Search for a solution for the given problem, starting from the given solution.
    */
   public abstract S search(P problem, S solution);
}
