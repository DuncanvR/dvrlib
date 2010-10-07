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
}
