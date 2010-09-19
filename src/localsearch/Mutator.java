/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Mutator.java
 */

package dvrlib.localsearch;

public interface Mutator<Solution, Mutation> {
   /**
    * Returns a mutation that changes the solution into one that closely resembles it.
    * @see Mutator#doMutation(java.lang.Object, java.lang.Object)
    */
   public Mutation generateMutation(Solution solution);

   /**
    * Executes the given mutation.
    * @see Mutator#undoMutation(java.lang.Object, localsearch.Mutation)
    */
   public void doMutation(Solution solution, Mutation m);

   /**
    * Undoes the given Mutation.
    * @see Mutator#doMutation(java.lang.Object, localsearch.Mutation)
    */
   public void undoMutation(Solution solution, Mutation m);
}
