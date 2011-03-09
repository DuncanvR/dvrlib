/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * LocalSearch.java
 */

package dvrlib.localsearch;

public abstract class LocalSearch<S extends Solution, E extends Comparable<E>> {
   /**
    * Search for a solution for the given problem, starting from a random solution.
    * @see LocalSearch#search(dvrlib.localsearch.Problem, dvrlib.localsearch.Solution)
    */
   public S search(Problem<S, E> problem) {
      return search(problem, problem.randomSolution());
   }

   /**
    * Search for a solution for the given problem, starting from the given solution.
    */
   public abstract S search(Problem<S, E> problem, S solution);

   /**
    * Do <tt>n</tt> iterations on the given solution, and return the best solution found.
    */
   public abstract S iterate(Problem<S, E> problem, S solution, int n);
}
