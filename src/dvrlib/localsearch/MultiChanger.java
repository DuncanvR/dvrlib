/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * MultiChanger.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Tuple;

import java.util.Collection;
import java.util.HashSet;

public abstract class MultiChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C extends Changer<P, S, ?>.Change> extends Changer<P, S, C> {
   /**
    * Returns a changer that will be used to make the next change.
    * @param ignored A collection of changers that should be ignored while picking a changer.
    * @return The selected changer. This method should never return <code>null</code> to indicate no changer could be found, but throw a CannotChangeException instead.
    * @throws CannotChangeException If there is no available changer.
    */
   public abstract Changer<P, S, C> get(SearchState<P, S> ss, Collection<Changer<P, S, C>> ignored) throws CannotChangeException;

   /**
    * Generates, executes and returns a new change.
    * The change should be small, such that it transforms the solution into one that closely resembles it.
    * @see Changer#undoChange(SingularSearchState, Change)
    * @throws CannotChangeException To indicate this changer was unable to change the given search state.
    */
   @Override @SuppressWarnings("unchecked")
   public C makeChange(SingularSearchState<P, S> ss) throws CannotChangeException {
      HashSet<Changer<P, S, C>> ignored = new HashSet<Changer<P, S, C>>();

      while(true) {
         try {
            return get(ss, ignored).makeChange(ss);
         }
         catch(CannotChangeException ex) {
            assert ex.changer != null;
            ignored.add(ex.changer);
         }
      }
   }
}
