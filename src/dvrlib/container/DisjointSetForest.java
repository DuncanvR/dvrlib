/*
 * DvRlib - Container
 * Copyright (C) Duncan van Roermund, 2012-2013
 * DisjointSetForest.java
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

package dvrlib.container;

import dvrlib.generic.Pair;

import java.util.HashSet;

/**
 * Disjoint set forest implementation.
 * @param E Element type.
 * @see AbstractDisjointSetForest
 */
public class DisjointSetForest<E> extends AbstractDisjointSetForest<E, Object> {

   /**
    * DisjointSetForest constructor.
    * O(1).
    */
   public DisjointSetForest() {
      super();
   }

   /**
    * DisjointSetForest constructor.
    * @param initCapacity Sets the initial capacity of this disjoint set forest.
    * O(1).
    */
   public DisjointSetForest(int initCapacity) {
      super(initCapacity);
   }

   /**
    * Adds the given element to this forest as a singleton set.
    * @param e The element to add.
    * @return Whether the element was successfully added.
    * O(1).
    */
   @Override
   public boolean add(E e) {
      return add(e, null);
   }

   /**
    * Merges the two given sets of elements and associated data.
    * @param p1 The first pair of elements and data.
    * @param p2 The second pair of elements and data, that is being merged into the first.
    * @return The union of the elements and data of the two sets.
    * O(1).
    */
   @Override
   protected Pair<HashSet<E>, Object> merge(Pair<HashSet<E>, Object> p1, Pair<HashSet<E>, Object> p2) {
      p1.a.addAll(p2.a);
      return p1;
   }
}
