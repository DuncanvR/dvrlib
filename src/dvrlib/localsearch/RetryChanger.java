/*
 * DvRlib - Local search
 * Duncan van Roermund, 2013
 * RetryChanger.java
 */

package dvrlib.localsearch;

public class RetryChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> extends Changer<P, S, Changer<P, S, ?>.Change> {
   protected Changer<P, S, Changer<P, S, ?>.Change> changer;
   protected int                                    tries;

   /**
    * RetryChanger constructor.
    * @param changers The changers that will be invoked.
    * @param tries    The maximum number of times the given changer will be called.
    */
   public RetryChanger(Changer<P, S, Changer<P, S, ?>.Change> changer, int tries) {
      this.changer = changer;
      this.tries   = tries;
   }

   // dvrlib.localsearch.Changer methods
   /**
    * Generates, executes and returns a new change, by invoking the underlying changer up to <code>tries</code> times.
    * @see Changer#undoChange(SingularSearchState, Change)
    * @throws CannotChangeException To indicate the underlying changer was unable to change the given search state within the number of tries.
    */
   @Override
   public Changer<P, S, ?>.Change makeChange(SingularSearchState<P, S> ss) throws CannotChangeException {
      for(int i = 1; i < tries; i++) {
         try {
            return changer.makeChange(ss);
         }
         catch(CannotChangeException _) { }
      }
      try {
         return changer.makeChange(ss);
      }
      catch(CannotChangeException ex) {
         throw new CannotChangeException(this, ex);
      }
   }

   /**
    * Reinitialises the underlying changer.
    * @see Changer#reinitialise(Problem)
    */
   @Override
   public void reinitialise(P problem) {
      changer.reinitialise(problem);
   }
}
