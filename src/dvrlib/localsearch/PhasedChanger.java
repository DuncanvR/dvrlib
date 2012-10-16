/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * PhasedChanger.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Tuple;

public class PhasedChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C> implements Changer<P, S, Tuple<C, Integer>> {
   protected Changer<P, S, C>[] changers;
   protected int                phase;

   /**
    * PhasedChanger constructor.
    * @param changers The changers that will be used for each phase.
    */
   public PhasedChanger(Changer<P, S, C>[] changers) {
      this.changers = changers;
   }

   /**
    * Executes the given change and advances the phase.
    * @see Changer#doChange(SearchState, Object)
    */
   @Override
   public void doChange(SearchState<P, S> ss, Tuple<C, Integer> change) {
      changers[change.b].doChange(ss, change.a);
      phase = (change.b + 1) % changers.length;
   }

   /**
    * Returns a change from the current phase.
    * @see Changer#generateChange(SearchState)
    */
   @Override
   public Tuple<C, Integer> generateChange(SearchState<P, S> ss) {
      return new Tuple<C, Integer>(changers[phase].generateChange(ss), phase);
   }

   /**
    * Reinitializes this changer and all its phases.
    * @see Changer#reinitialize(Problem)
    */
   @Override
   public void reinitialize(P problem) {
      for(Changer<P, S, C> c : changers) {
         c.reinitialize(problem);
      }
      phase = 0;
   }

   /**
    * Regresses the phase and undoes the given change.
    * @see Changer#undoChange(SearchState, Object)
    */
   @Override
   public void undoChange(SearchState<P, S> ss, Tuple<C, Integer> change) {
      changers[change.b].undoChange(ss, change.a);
      phase = (change.b - 1) % changers.length;
   }
}
