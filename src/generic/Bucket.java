/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Bucket.java
 */

package dvrlib.generic;

import java.util.LinkedList;

public class Bucket<E> {
   protected final LinkedList<E> list;
   protected int previous, next;

   /**
    * Bucket constructor.
    * O(1).
    */
   public Bucket() {
      list = new LinkedList<E>();
   }

   /**
    * Returns true if this bucket is empty, false otherwise.
    * O(1).
    */
   public boolean isEmpty() {
      return list.isEmpty();
   }

   /**
    * Returns the number of elements in this bucket.
    * O(1).
    */
   public int getSize() {
      return list.size();
   }

   /**
    * Adds an element to this bucket.
    * O(1).
    */
   public void add(E element) {
      list.add(element);
   }

   /**
    * Returns, but does not remove, the first element in this bucket.
    * O(1).
    */
   public E peek() {
      return list.peek();
   }

   /**
    * Removes and returns the first element in this bucket.
    * O(1).
    */
   public E pop() {
      return list.pop();
   }

   @Override
   public String toString() {
      String s = "[";
      for(E e : list) {
         s += e + ", ";
      }
      return s + "]";
   }
}
