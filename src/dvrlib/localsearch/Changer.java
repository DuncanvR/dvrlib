/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * Changer.java
 */

package dvrlib.localsearch;

public interface Changer<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C> {
   /**
    * Returns a change that can turn the solution into one that closely resembles it.
    */
   public C    generateChange(SearchState<P, S> ss);

   /**
    * Executes the given change.
    */
   public void       doChange(SearchState<P, S> ss, C change);

   /**
    * Undoes the given change.
    */
   public void     undoChange(SearchState<P, S> ss, C change);
}
