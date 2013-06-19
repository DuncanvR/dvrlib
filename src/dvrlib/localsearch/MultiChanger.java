/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2012-2013
 * MultiChanger.java
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
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
    * Adds the given changer with the given weight to the set of underlying changers.
    */
   public void add(Changer<P, S, C> changer, Double weight) {
      changers.put(changer, weight);
   }

   /**
    * Generates, executes and returns a new change, by invoking one of its underlying changers.
    * @throws CannotChangeException To indicate none of the underlying changers were able to change the given search state.
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
    * Reinitialises this changer and all its underlying changers.
    * @see Changer#reinitialise(Problem)
    */
   @Override
   public void reinitialise(P problem) {
      for(Changer<P, S, C> c : changers.keySet()) {
         c.reinitialise(problem);
      }
   }
}
