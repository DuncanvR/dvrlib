/*
 * DvRlib - Container
 * Copyright (C) Duncan van Roermund, 2010
 * BucketArray.java
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

public class BucketArray<I extends AbstractBucketItem> {
   protected final int min, max;
   protected int first, last, size;
   protected Bucket<I> buckets[];

   /**
    * BucketArray constructor.
    * @param min The minimum index that will be used.
    * @param max The maximum index that will be used.
    * O(1).
    */
   @SuppressWarnings("unchecked")
   public BucketArray(int min, int max) {
      this.min = min;
      this.max = max;
      first = max;
      last = min;
      size = 0;
      buckets = new Bucket[max - min + 1];
      buckets[0] = new Bucket<I>(0);
      for(int i = 1; i < buckets.length; i++) {
         buckets[i] = new Bucket<I>(i);
      }
   }

   /**
    * Returns the number of items in this BucketArray.
    * O(1).
    */
   public int getSize() {
      return size;
   }

   /**
    * Returns the index of the first non-empty bucket.
    * O(1).
    */
   public int getFirstBucketIndex() {
      return first;
   }

   /**
    * Returns the index of the last non-empty bucket.
    * O(1).
    */
   public int getLastBucketIndex() {
      return last;
   }

   /**
    * Returns the bucket with the given index.
    * O(1).
    */
   protected Bucket<I> getBucket(int bucketIndex) {
      return buckets[bucketIndex - min];
   }

   /**
    * Adds the given item to the specified bucket.
    * @return The index of the item in the bucket.
    * O(1).
    */
   public int add(int bucketIndex, I item) {
      // Add item
      Bucket<I> bucket = getBucket(bucketIndex);
      bucket.add(item);

      // Maintain data
      size++;
      if(bucket.size() == 1) {
         // The added item is the only item in the bucket
         if(bucketIndex < first)
            first = bucketIndex;
         if(bucketIndex > last)
            last = bucketIndex;
      }

      return item.itemIndex;
   }

   /**
    * Removes and returns the item with the specified index from the specified bucket.
    * O(b) if the item was the last one in the first or last bucket, O(1) otherwise.
    */
   public I remove(int bucketIndex, int itemIndex) {
      // Retreive item
      Bucket<I> bucket = getBucket(bucketIndex);
      I item = bucket.remove(itemIndex);

      // Maintain data
      size--;
      if(bucket.isEmpty()) {
         // The removed item was the only item in the bucket
         if(bucketIndex == first) {
            if(bucketIndex == last) {
               // There are only empty buckets left
               first = max;
               last = min;
            }
            else {
               // Find the first non-empty bucket
               for(first++; first < max && getBucket(first).isEmpty(); first++) ;
            }
         }
         else if(bucketIndex == last) {
            // Find the last non-empty bucket
            for(last--; last > min && getBucket(last).isEmpty(); last--) ;
         }
      }

      return item;
   }

   /**
    * Returns, but does not remove, the last item of the bucket with the given index.
    * O(1).
    */
   public I peek(int bucketIndex) {
      return getBucket(bucketIndex).getLast();
   }

   /**
    * Removes and returns the last item of the bucket with the given index.
    * @see BucketArray#remove(int, int)
    */
   public I pop(int bucketIndex) {
      return remove(bucketIndex, getBucket(bucketIndex).getLastIndex());
   }

   /**
    * Removes and returns the last item of the first non-empty bucket.
    * @see BucketArray#pop(int)
    */
   public I popFirst() {
      return pop(getFirstBucketIndex());
   }

   /**
    * Removes and returns the last item of the last non-empty bucket.
    * @see BucketArray#pop(int)
    */
   public I popLast() {
      return pop(getLastBucketIndex());
   }

   /**
    * Prints this BucketArray with its contents.
    */
   public void print() {
      System.out.println(this);
      for(int i = min; i <= max; i++) {
         Bucket<I> b = getBucket(i);
         System.out.println("\t" + i + ": " + (b == null ? "[]" : b));
      }
   }

   @Override
   public String toString() {
      return getClass().getName() + "[" + min + ", " + max + "](" + size + ")";
   }
}
