/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * LocalSearch.java
 */

package dvrlib.localsearch;

public abstract class LocalSearch<P extends Problem<S>, S extends Solution> {
   /**
    * Search for a Solution for the given Problem, starting with a random Solution.
    * @see LocalSearch#search(dvrlib.localsearch.Problem, dvrlib.localsearch.Solution)
    */
   public S search(P problem) {
      return search(problem, problem.randomSolution());
   }

   /**
    * Search for a Solution for the given Problem, starting with the given Solution.
    */
   public abstract S search(P problem, S solution);
}
