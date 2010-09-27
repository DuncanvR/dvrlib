/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * Bucket.java
 */

package dvrlib.generic;

import java.util.ArrayList;

public class Bucket<I extends AbstractBucketItem> {
   protected final int bucketIndex;
   private ArrayList<I> list;

   /**
    * Bucket constructor.
    * @param bucketCount The number of buckets in the BucketArray.
    * O(1).
    */
   public Bucket(int bucketIndex) {
      this.bucketIndex = bucketIndex;
      list = new ArrayList<I>();
   }

   /**
    * Returns true if this bucket holds no items, false otherwise.
    * O(1).
    */
   public boolean isEmpty() {
      return list.isEmpty();
   }

   /**
    * Returns the number of items in this bucket.
    * O(1).
    */
   public int size() {
      return list.size();
   }

   /**
    * Returns the index to the last item in this bucket.
    * O(1).
    */
   public int getLastIndex() {
      return size() - 1;
   }

   /**
    * Returns the last item in this bucket.
    * O(1).
    */
   public I getLast() {
      return list.get(getLastIndex());
   }

   /**
    * Adds the given item to the back of this bucket.
    * @return true if the item was added, false otherwise.
    * O(1).
    */
   public boolean add(I item) {
      if(list.add(item)) {
         item.itemIndex = getLastIndex();
         return true;
      }
      else
         return false;
   }

   /**
    * Replaces the item at the specified index with the given item.
    * O(1).
    */
   public I set(int itemIndex, I item) {
      item.itemIndex = itemIndex;
      return list.set(itemIndex, item);
   }

   /**
    * Removes and returns the given item.
    * @see Bucket#remove(int)
    * O(1).
    */
   public I remove(I item) {
      return remove(item.itemIndex);
   }

   /**
    * Removes and returns the item at the given index.
    * Since the ordering of items is not important, the removed item is replaced by the last item.
    * O(1).
    */
   public I remove(int itemIndex) {
      if(itemIndex == getLastIndex())
         return removeLast();
      return set(itemIndex, removeLast());
   }

   /**
    * Removes and returns the last item in this bucket.
    * O(1).
    */
   public I removeLast() {
      return list.remove(getLastIndex());
   }

   @Override
   public String toString() {
      String s = "[";
      for(I item : list) {
         s += item + ", ";
      }
      return s + "]";
   }
}
