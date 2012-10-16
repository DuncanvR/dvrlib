/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * MultiChanger.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Tuple;

public abstract class MultiChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C> implements Changer<P, S, Tuple<Changer<P, S, C>, C>> {
   public abstract Changer<P, S, C> get();

   /**
    * Executes the given change.
    * @see Changer#doChange(SearchState, Object)
    */
   @Override
   public void doChange(SearchState<P, S> ss, Tuple<Changer<P, S, C>, C> change) {
      change.a.doChange(ss, change.b);
   }

   /**
    * Returns a change from one of the children changers.
    * @see MultiChanger#getChanger()
    * @see Changer#generateChange(SearchState)
    */
   @Override
   public Tuple<Changer<P, S, C>, C> generateChange(SearchState<P, S> ss) {
      Changer<P, S, C> changer = get();
      return new Tuple<Changer<P, S, C>, C>(changer, changer.generateChange(ss));
   }

   /**
    * Undoes the given change.
    * @see Changer#undoChange(SearchState, Object)
    */
   @Override
   public void undoChange(SearchState<P, S> ss, Tuple<Changer<P, S, C>, C> change) {
      change.a.undoChange(ss, change.b);
   }
}
