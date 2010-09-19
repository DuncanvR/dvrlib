/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Problem.java
 */

package dvrlib.localsearch;

public interface Problem {
   /**
    * Generates a random Solution to this Problem.
    */
   public Solution randomSolution();
   /**
    * Returns the Mutator for this Problem, an object which generates Mutations for Solutions.
    */
   public Mutator getMutator();
}
