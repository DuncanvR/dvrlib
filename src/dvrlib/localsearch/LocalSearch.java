/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * LocalSearch.java
 */

package dvrlib.localsearch;

public abstract class LocalSearch<P extends Problem<S, E>, S extends Solution, E extends Comparable<E>> {
   /**
    * Searches for an optimal solution for the given problem, starting from a random solution.
    */
   public          S search(P problem) {
      return search(problem, problem.randomSolution());
   }

   /**
    * Searches for an optimal solution for the given problem, starting from the given solution.
    */
   public abstract S search(P problem, S solution);
}
