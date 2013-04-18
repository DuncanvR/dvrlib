/*
 * DvRlib - Local search
 * Duncan van Roermund, 2012
 * MultiChanger.java
 */

package dvrlib.localsearch;

import dvrlib.container.WeightedTree;

import java.util.Hashtable;

public class MultiChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C extends Changer<P, S, ?>.Change> extends Changer<P, S, C> {
   protected final Hashtable<Changer<P, S, C>, Double> changers;

   /**
    * MultiChanger constructor.
    * Creates an empty set of changers.
    * @see MultiChanger#add(Changer, double)
    */
   public MultiChanger() {
      changers = new Hashtable<Changer<P, S, C>, Double>();
   }

   /**
    * Adds the given changer with the given weight to the set of changers.
    */
   public void add(Changer<P, S, C> changer, Double weight) {
      changers.put(changer, weight);
   }

   /**
    * Generates, executes and returns a new change.
    * The change should be small, such that it transforms the solution into one that closely resembles it.
    * @throws CannotChangeException To indicate this changer was unable to change the given search state.
    */
   @Override
   public C makeChange(SingularSearchState<P, S> ss) throws CannotChangeException {
      WeightedTree<Changer<P, S, C>> tree = new WeightedTree<Changer<P, S, C>>();
      for(java.util.Map.Entry<Changer<P, S, C>, Double> e : changers.entrySet()) {
         tree.add(e.getValue(), e.getKey());
      }

      while(tree.size() > 0) {
         Changer<P, S, C> c = tree.getWeighted(Math.random()).b;
         try {
            return c.makeChange(ss);
         }
         catch(CannotChangeException ex) {
            assert (c == ex.changer);
            if(!tree.remove(changers.get(c), c))
               throw new IllegalStateException("Unable to remove changer");
         }
      }
      throw new CannotChangeException(this, "No eligible changers to choose from");
   }

   /**
    * Reinitializes this changer and all its children changers.
    * @see Changer#reinitialize(Problem)
    */
   @Override
   public void reinitialize(P problem) {
      for(Changer<P, S, C> c : changers.keySet()) {
         c.reinitialize(problem);
      }
   }
}
