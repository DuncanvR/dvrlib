/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Problem.java
 */

package dvrlib.localsearch;

public interface Problem<S extends Solution, M> {
   /**
    * Generates a random Solution to this Problem.
    */
   public S randomSolution();

   /**
    * Generates a mutation that changes the solution into one that closely resembles it.
    */
   public M generateMutation(S solution);

   /**
    * Executes the given mutation.
    */
   public void doMutation(S solution, M mutation);

   /**
    * Undoes the given Mutation.
    */
   public void undoMutation(S solution, M mutation);
}
