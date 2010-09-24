/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Bucket.java
 */

package dvrlib.generic;

public class Bucket<E> extends java.util.ArrayList<E> {
   protected int previous, next;

   /**
    * Bucket constructor.
    * O(1).
    */
   public Bucket() {
      super();
   }

   /**
    * Returns the last element in this bucket.
    * O(1).
    */
   public E getLast() {
      return get(size() - 1);
   }

   /**
    * Removes the element at the given index.
    * Since the ordering of elements is not important, the removed element is replaced by the last element.
    * O(1).
    */
   @Override
   public E remove(int index) {
      // Sanity check
      if(index < 0 || index > size() - 1)
         return null;

      if(index == size() - 1)
         return removeLast();

      E element = get(index);
      set(index, removeLast());
      return element;
   }

   /**
    * Removes and returns the last element in this bucket.
    * O(1).
    */
   public E removeLast() {
      return remove(size() - 1);
   }

   @Override
   public String toString() {
      String s = "[";
      for(E e : this) {
         s += e + ", ";
      }
      return s + "]";
   }
}
