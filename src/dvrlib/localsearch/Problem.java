/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Problem.java
 */

package dvrlib.localsearch;

public interface Problem<S extends Solution> {
   /**
    * Generates a random Solution to this Problem.
    */
   public S randomSolution();

   /**
    * Returns the mutator for this problem.
    */
   public Mutator<S, ?> getMutator();
}
