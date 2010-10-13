/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Changer.java
 */

package dvrlib.localsearch;

public interface Changer<S extends Solution, C> {
   /**
    * Returns a change that can turn the solution into one that closely resembles it.
    */
   public C generateChange(S solution);

   /**
    * Executes the given change.
    */
   public void doChange(S solution, C change);

   /**
    * Undoes the given change.
    */
   public void undoChange(S solution, C change);
}
