/*
 * DvRlib - Container
 * Copyright (C) Duncan van Roermund, 2012-2013
 * AbstractDisjointSetForest.java
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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Disjoint set forest implementation.
 * @param E Element type.
 * @param S Set data type.
 */
public abstract class AbstractDisjointSetForest<E, S> implements java.util.Set<E> {
   protected HashMap<E, Pair<E, Integer>>    parents;
   protected HashMap<E, Pair<HashSet<E>, S>> sets    = new HashMap<E, Pair<HashSet<E>, S>>();

   /**
    * AbstractDisjointSetForest constructor.
    * O(1).
    */
   public AbstractDisjointSetForest() {
      parents = new HashMap<E, Pair<E, Integer>>();
   }

   /**
    * AbstractDisjointSetForest constructor.
    * @param initCapacity Sets the initial capacity of this disjoint set forest.
    * O(1).
    */
   public AbstractDisjointSetForest(int initCapacity) {
      parents = new HashMap<E, Pair<E, Integer>>(initCapacity);
   }

   /**
    * Adds the given element and data to this forest as a singleton set.
    * @param e The element to add.
    * @param s The associated data to add.
    * @return A boolean indicating whether the element was successfully added.
    * O(1).
    */
   public boolean add(E e, S s) {
      if(contains(e))
         return false;
      HashSet<E> es = new HashSet<E>();
      es.add(e);
      sets.put(e, new Pair<HashSet<E>, S>(es, s));
      parents.put(e, new Pair<E, Integer>(e, 0));
      return true;
   }

   /**
    * Merges the two given sets of elements and associated data.
    * @param p1 The first pair of elements and data.
    * @param p2 The second pair of elements and data, that is being merged into the first.
    * @return The union of the elements and data of the two sets.
    */
   protected abstract Pair<HashSet<E>, S> merge(Pair<HashSet<E>, S> p1, Pair<HashSet<E>, S> p2);

   /**
    * Finds the representative of the set to which the given element belongs.
    * @param e The element to find.
    * @return The representative of the set containing the given element.
    * @throws IllegalArgumentException If the supplied element is not a member of this forest.
    */
   public E representative(E e) {
      Pair<E, Integer> data = parents.get(e);
      if(data == null)
         throw new IllegalArgumentException("The supplied element is not a member of this forest");
      if(data.a != e)
         data.a = representative(data.a);
      return data.a;
   }

   /**
    * Returns the set to which the given element belongs.
    * @param e The element to find.
    * @return The set containing the given element and the associated data.
    * @throws IllegalArgumentException If the supplied element is not a member of this forest.
    */
   public Pair<HashSet<E>, S> retrieveSet(E e) {
      return sets.get(representative(e));
   }

   /**
    * Sets the given data as the associated data for the set to which the given element belongs.
    * @param e The representative element.
    * @param s The associated data to set.
    * @return The data previously associated with the indicated set.
    */
   public S setData(E e, S s) {
      Pair<HashSet<E>, S> p = sets.get(representative(e));
      S old = p.b;
      p.b = s;
      return old;
   }

   /**
    * Returns all the sets of this forest.
    * @return The collection of contained sets and their associated data.
    */
   public Collection<Pair<HashSet<E>, S>> retrieveSets() {
      return sets.values();
   }

   /**
    * Merges two sets in this forest by taking their union.
    * @param e1 The representative of the first set to merge.
    * @param e2 The representative of the second set to merge.
    * @return The representative of the resulting merged set.
    * @throws IllegalArgumentException If either of the given representatives is not a member of this forest.
    */
   public E union(E e1, E e2) {
      e1 = representative(e1);
      e2 = representative(e2);
      if(e1 == e2)
         return e1;

      Pair<E, Integer> d1 = parents.get(e1), d2 = parents.get(e2);
      if(d1.b > d2.b) { // If the rank of the first set is larger than that of the second
         d2.a = e1;                                       // Set e1 as the parent of the second set
         sets.put(e1, merge(sets.get(e1), sets.get(e2))); // Merge the sets
         sets.remove(e2);                                 // Remove the second set
         return e1;
      }
      else {
         d1.a = e2;                                       // Set e2 as the parent of the first set
         sets.put(e2, merge(sets.get(e1), sets.get(e2))); // Merge the sets
         sets.remove(e1);                                 // Remove the first set
         if(d1.b == d2.b)                                 // Update the rank of the second set if necessary
            d2.b++;
         return e2;
      }
   }

   //--- java.util.Set methods
   /**
    * Adds the given element to this forest as a singleton set.
    * The default implementation throws an UnsupportedOperationException, override to make meaningful.
    * @param e The element to add.
    * @return Whether the element was successfully added.
    * @see AbstractDisjointSetForest#add(java.lang.Object, java.lang.Object)
    * @see java.util.Set#add(java.lang.Object)
    */
   public boolean add(E e) {
      throw new UnsupportedOperationException("dvrlib.container.AbstractDisjointForest#add(java.lang.Object) is not implemented");
   }

   /**
    * Adds all the elements in the given collection to this forest, each as a singleton set.
    * @param es The elements to add.
    * @return Whether at least element was successfully added.
    * @see AbstractDisjointSetForest#add(java.lang.Object)
    * @see java.util.Set#addAll(java.util.Collection)
    * O(n).
    */
   @Override
   public boolean addAll(Collection<? extends E> es) {
      boolean changed = false;
      for(E e : es) {
         changed |= add(e);
      }
      return changed;
   }

   /**
    * Removes all elements from this forest.
    */
   @Override
   public void clear() {
      parents.clear();
      sets.clear();
   }

   /**
    * Checks whether the given element is in this forest.
    * @param e The element to check.
    * @see java.util.Set#contains(java.lang.Object)
    * O(1).
    */
   @Override
   public boolean contains(Object e) {
      return parents.containsKey(e);
   }

   /**
    * Checks whether all elements of the given collection are in this forest.
    * @param es The elements to check.
    * @see java.util.Set#containsAll(java.util.Collection)
    * O(n).
    */
   @Override
   public boolean containsAll(Collection<?> es) {
      for(Object e : es) {
         if(!contains(e)) return false;
      }
      return true;
   }

   /**
    * Returns whether this forest is empty.
    * @see java.util.Set#isEmpty()
    * O(1).
    */
   @Override
   public boolean isEmpty() {
      return parents.isEmpty();
   }

   /**
    * Returns an iterator over the elements of this forest.
    * @see java.util.Set#iterator()
    */
   @Override
   public java.util.Iterator<E> iterator() {
      return new dvrlib.generic.ReadOnlyIterator<E>(parents.keySet().iterator());
   }

   /**
    * Removes the specified element from this set if it is present.
    * @param e Element to be removed from this set.
    * @return <code>true</code> if this set contained the specified element, <code>false</code> otherwise.
    * @see java.util.Set#remove(java.lang.Object)
    */
   @Override
   public boolean remove(Object e) {
      if(contains(e)) {
         @SuppressWarnings("unchecked")
         E r = representative((E) e);
         Pair<HashSet<E>, S> t = retrieveSet(r);
         parents.remove(e);
         t.a.remove(e);
         if(e == r) {
            for(E n : t.a) {
               r = n;
               sets.put(r, t);
               break;
            }
            sets.remove(e);
         }
         for(E n : t.a) {
            parents.get(n).a = r;
         }
         return true;
      }
      return false;
   }

   /**
    * Removes all the elements of the given collection from this set.
    * @param os The collection of elements to remove from this set.
    * @return <code>true</code> if this set changed as a result of this call.
    * @see java.util.Set#removeAll(java.util.Collection)
    */
   @Override
   public boolean removeAll(Collection<?> os) {
      boolean changed = false;
      for(Object o : os) {
         changed |= remove(o);
      }
      return changed;
   }

   /**
    * Not supported by DisjointSetForest.
    * @see java.util.Set#retainAll(java.util.Collection)
    */
   @Override
   public boolean retainAll(Collection<?> es) {
      throw new UnsupportedOperationException("dvrlib.container.AbstractDisjointSetForest#retainAll(java.util.Collection) is not implemented");
   }

   /**
    * Returns the size of this forest.
    * @see java.util.Set#size()
    * O(1).
    */
   @Override
   public int size() {
      return parents.size();
   }

   /**
    * Returns an array containing all elements of this forest.
    * @see java.util.Set#toArray()
    */
   @Override
   public Object[] toArray() {
      return parents.keySet().toArray();
   }

   /**
    * Returns an array of the given type containing all elements of this forest.
    * @see java.util.Set#toArray(java.lang.Object[])
    */
   @Override
   public <T> T[] toArray(T[] a) {
      return parents.keySet().toArray(a);
   }
}
