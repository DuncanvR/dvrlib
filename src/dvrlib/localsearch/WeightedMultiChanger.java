/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * WeightedMultiChanger.java
 */

package dvrlib.localsearch;

import dvrlib.container.WeightedTree;

import java.util.Collection;

public class WeightedMultiChanger<P extends Problem<S, ? extends Comparable<?>>, S extends Solution, C extends Changer<P, S, ?>.Change> extends MultiChanger<P, S, C> {
   protected final WeightedTree<Changer<P, S, C>> changers;

   /**
    * WeightedMultiChanger constructor.
    * Creates an empty set of changers.
    * @see WeightedMultiChanger#WeightedMultiChanger(WeightedTree)
    * @see WeightedMultiChanger#add(Changer, double)
    */
   public WeightedMultiChanger() {
      changers = new WeightedTree<Changer<P, S, C>>();
   }

   /**
    * WeightedMultiChanger constructor.
    * @param changers The changers to be used.
    * @see WeightedMultiChanger#add(Changer, double)
    */
   public WeightedMultiChanger(WeightedTree<Changer<P, S, C>> changers) {
      this.changers = changers;
   }

   /**
    * Adds the given changer with the given weight.
    */
   public void add(Changer<P, S, C> changer, double weight) {
      changers.add(weight, changer);
   }

   /**
    * Returns a changer that will be used to make the next change.
    * @param ignored A collection of changers that should be ignored while picking a changer.
    * @throws CannotChangeException If there is no available changer.
    */
   @Override
   public Changer<P, S, C> get(SearchState<P, S> ss, Collection<Changer<P, S, C>> ignored) throws CannotChangeException {
      if(ignored.size() >= changers.size())
         throw new CannotChangeException(this, "No eligible changers to choose from");
      Changer<P, S, C> changer;
      do { // TODO: Implement this properly, such that we can guarantee termination
         changer = changers.getWeighted(Math.random()).b;
      }
      while(ignored.contains(changer));
      return changer;
   }

   /**
    * Reinitializes this changer and all its children changers.
    * @see Changer#reinitialize(Problem)
    */
   @Override
   public void reinitialize(P problem) {
      for(Changer<P, S, C> c : changers) {
         c.reinitialize(problem);
      }
   }
}
