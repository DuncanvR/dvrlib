/*
 * DvRlib - Container
 * Duncan van Roermund, 2012-2013
 * DisjointSetForest.java
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
    * @param t1 The first tuple of elements and data.
    * @param t2 The second tuple of elements and data, that is being merged into the first.
    * @return The union of the elements and data of the two sets.
    * O(1).
    */
   @Override
   protected Pair<HashSet<E>, Object> merge(Pair<HashSet<E>, Object> t1, Pair<HashSet<E>, Object> t2) {
      t1.a.addAll(t2.a);
      return t1;
   }
}
