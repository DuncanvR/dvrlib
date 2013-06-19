/*
 * DvRlib - Container
 * Copyright (C) Duncan van Roermund, 2010
 * Bucket.java
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

import java.util.ArrayList;

public class Bucket<I extends AbstractBucketItem> {
   protected final int bucketIndex;
   private ArrayList<I> list;

   /**
    * Bucket constructor.
    * @param bucketIndex The index of this bucket in the array.
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
    * Removes and returns the given item using its index.
    * Does not check whether the given item is actually in this bucket!
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
      else
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
