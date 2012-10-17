/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * MultiChanger.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Tuple;

public abstract class MultiChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C extends Change<P, S>> implements Changer<P, S, C> {
   /**
    * Returns a changer that will be used to make the next change.
    */
   public abstract Changer<P, S, C> get(SearchState<P, S> ss);

   /**
    * Generates, executes and returns a new change.
    * The change should be small, such that it transforms the solution into one that closely resembles it.
    * @see Changer#makeChange(SingularSearchState)
    */
   @Override
   public C makeChange(SingularSearchState<P, S> ss) {
      return get(ss).makeChange(ss);
   }
}
