/*
 * DvRLib - Container
 * Duncan van Roermund, 2012
 * DisjointForest.java
 */

package dvrlib.container;

import dvrlib.generic.Tuple;

import java.util.Collection;
import java.util.HashMap;

public class DisjointForest<E> implements java.util.Set<E> {
   protected HashMap<E, Tuple<E, Integer>> forest;

   /**
    * DisjointForest constructor.
    * O(1).
    */
   public DisjointForest() {
      forest = new HashMap<E, Tuple<E, Integer>>();
   }

   /**
    * DisjointForest constructor.
    * @param initCapacity Sets the initial capacity of this disjoint forest.
    * O(1).
    */
   public DisjointForest(int initCapacity) {
      forest = new HashMap<E, Tuple<E, Integer>>(initCapacity);
   }

   /**
    * Finds the set containing the given element.
    * @param e The element to find.
    * @return The representative of the set containing the given element.
    * @throws IllegalArgumentException If the supplied element is not a member of this forest.
    */
   public E find(E e) {
      Tuple<E, Integer> data = forest.get(e);
      if(data == null)
         throw new IllegalArgumentException("The supplied element is not a member of this forest");
      if(data.a != e)
         data.a = find(data.a);
      return data.a;
   }

   /**
    * Merges two sets in this forest by taking their union.
    * @param e1 The representative of the first set to merge.
    * @param e2 The representative of the second set to merge.
    * @return The representative of the resulting merged set.
    * @throws IllegalArgumentException If either of the given representatives is not a member of this forest.
    */
   public E union(E e1, E e2) {
      e1 = find(e1);
      e2 = find(e2);
      if(e1 == e2)
         return e1;

      Tuple<E, Integer> d1 = forest.get(e1), d2 = forest.get(e2);
      if(d1.b > d2.b) { // If the rank of the first set is larger than that of the second
         d2.a = e1; // Set e1 as the parent of the second set
         return e1;
      }
      else {
         d1.a = e2; // Set e2 as the parent of the first set
         if(d1.b == d2.b)
            d2.b++; // Update the rank of the second set if necessary
         return e2;
      }
   }

   /**
    * Adds the given element to this forest as a singleton set.
    * @param e The element to add.
    * @return Whether the element was succesfully added.
    * @see java.util.Set#add(java.lang.Object)
    * O(1).
    */
   @Override
   public boolean add(E e) {
      if(forest.containsKey(e))
         return false;
      forest.put(e, new Tuple<E, Integer>(e, 0));
      return true;
   }

   /**
    * Adds all the elements in the given collection to this forest, each as a singleton set.
    * @param es The elements to add.
    * @return Whether at least element was succesfully added.
    * @see DisjointForest#add(java.lang.Object)
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
      forest.clear();
   }

   /**
    * Checks whether the given element is in this forest.
    * @param e The element to check.
    * @see java.util.Set#contains(java.lang.Object)
    * O(1).
    */
   @Override
   public boolean contains(Object e) {
      return forest.containsKey(e);
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
      return forest.isEmpty();
   }

   /**
    * Returns an iterator over the elements of this forest.
    * @see java.util.Set#iterator()
    */
   @Override
   public java.util.Iterator<E> iterator() {
      return new dvrlib.generic.ReadOnlyIterator(forest.keySet().iterator());
   }

   /**
    * Removals are not supported by DisjointForest.
    * @see java.util.Set#remove(java.lang.Object)
    */
   public boolean remove(Object e) {
      throw new UnsupportedOperationException("dvrlib.generic.DisjointForest#remove(java.lang.Object) is not implemented");
   }

   /**
    * Removals are not supported by DisjointForest.
    * @see java.util.Set#removeAll(java.util.Collection)
    */
   @Override
   public boolean removeAll(Collection<?> es) {
      throw new UnsupportedOperationException("dvrlib.generic.DisjointForest#removeAll(java.util.Collection) is not implemented");
   }

   /**
    * Removals are not supported by DisjointForest.
    * @see java.util.Set#retainAll(java.util.Collection)
    */
   @Override
   public boolean retainAll(Collection<?> es) {
      throw new UnsupportedOperationException("dvrlib.generic.DisjointForest#retainAll(java.util.Collection) is not implemented");
   }

   /**
    * Returns the size of this forest.
    * @see java.util.Set#size()
    * O(1).
    */
   @Override
   public int size() {
      return forest.size();
   }

   /**
    * Returns an array containing all elements of this forest.
    * @see java.util.Set#toArray()
    */
   @Override
   public Object[] toArray() {
      return forest.keySet().toArray();
   }

   /**
    * Returns an array of the given type containing all elements of this forest.
    * @see java.util.Set#toArray(java.lang.Object[])
    */
   @Override
   public <T> T[] toArray(T[] a) {
      return forest.keySet().toArray(a);
   }
}
