/*
 * DvRLib - Container
 * Duncan van Roermund, 2012-2013
 * DisjointForest.java
 */

package dvrlib.container;

import dvrlib.generic.Tuple;

import java.util.HashSet;

public class DisjointForest<E> extends AbstractDisjointForest<E, Object> {
   /**
    * Adds the given element to this forest as a singleton set.
    * @param e The element to add.
    * @return Whether the element was succesfully added.
    * O(1).
    */
   @Override
   public boolean add(E e) {
      return add(e, null);
   }

   /**
    * Merges the two given sets of elements and associated data.
    * @param t1 The first tuple of elements and data.
    * @param t2 The second tuple of elements and data, that is being merged into the first.
    * @return The union of the elements and data of the two sets.
    * O(1).
    */
   @Override
   protected Tuple<HashSet<E>, Object> merge(Tuple<HashSet<E>, Object> t1, Tuple<HashSet<E>, Object> t2) {
      t1.a.addAll(t2.a);
      return t1;
   }
}
