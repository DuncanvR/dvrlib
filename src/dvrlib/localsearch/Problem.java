/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Problem.java
 */

package dvrlib.localsearch;

public interface Problem<S extends Solution> {
   /**
    * Generates a random solution to this problem.
    */
   public S randomSolution();

   /**
    * Returns the evaluation of the given solution, where less is better.
    */
   public double evaluate(S solution);

   /**
    * Compares the given solution to the current best, and saves it if it is good enough.
    * Note that the solution should be copied in order to be saved, as it can (and probably will) be altered.
    */
   public void saveSolution(S s);

   /**
    * Returns the best solution currently known.
    */
   public S getBestSolution();
}
