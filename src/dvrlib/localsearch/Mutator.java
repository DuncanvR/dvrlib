/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Mutator.java
 */

package dvrlib.localsearch;

public interface Mutator<S extends Solution, M> {
   /**
    * Returns a mutation that changes the solution into one that closely resembles it.
    */
   public M generateMutation(S solution);

   /**
    * Executes the given mutation.
    */
   public void doMutation(S solution, M mutation);

   /**
    * Undoes the given mutation.
    */
   public void undoMutation(S solution, M mutation);
}
