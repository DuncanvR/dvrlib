/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * BucketArray.java
 */

package dvrlib.generic;

public class BucketArray<E> {
   protected final int min, max;
   protected int low, high, size;
   protected Bucket<E> buckets[];

   /**
    * BucketArray constructor.
    * @param min The minimum index that will be used.
    * @param max The maximum index that will be used.
    * O(1).
    */
   public BucketArray(int min, int max) {
      this.min = min;
      this.max = max;

      low  = max;
      high = min;
      size = 0;
      buckets = new Bucket[max - min + 1];
   }

   /**
    * Returns the number of elements in this BucketArray.
    * O(1).
    */
   public int getSize() {
      return size;
   }

   /**
    * Returns the index of the lowest non-empty bucket.
    * O(1).
    */
   public int getLowestIndex() {
      return low;
   }

   /**
    * Returns the index of the highest non-empty bucket.
    * O(1).
    */
   public int getHighestIndex() {
      return high;
   }

   /**
    * Returns the bucket with the given index, or null if it doesn't exist.
    * O(1).
    */
   protected Bucket<E> getBucket(int index) {
      // Sanity check
      if(index < min || index > max)
         return null;
      return buckets[index - min];
   }

   /**
    * Adds a bucket at the given index.
    * O(b) - The number of buckets.
    */
   protected Bucket<E> addBucket(int index) {
      Bucket<E> bucket = new Bucket<E>();
      buckets[index - min] = bucket;

      // Maintain data
      int i = index - 1;
      // Get previous bucket
      while(i > min && getBucket(i) == null)
         i--;
      bucket.previous = i;

      // Set previous pointers
      Bucket<E> b = getBucket(i);
      if(b != null) {
         bucket.next = b.next;
         b.next = index;
      }
      else {
         // Get next bucket
         i = index + 1;
         while(i < max && getBucket(i) == null)
            i++;
         bucket.next = i;
      }

      // Set next pointers
      b = getBucket(bucket.next);
      if(b != null)
         b.previous = index;

      return bucket;
   }

   /**
    * Removes the bucket at the given index.
    * O(1).
    */
   protected void removeBucket(int index) {
      Bucket bucket = getBucket(index);
      if(bucket != null) {
         if(index == high) {
            high = bucket.previous;
            Bucket b = getBucket(high);
            if(b != null)
               b.next = max;
         }
         else if(index == low) {
            low = bucket.next;
            Bucket b = getBucket(low);
            if(b != null)
               b.previous = min;
         }

         Bucket<E> b = getBucket(bucket.previous);
         if(b != null)
            b.next = bucket.next;

         b = getBucket(bucket.next);
         if(b != null)
            b.previous = bucket.previous;

         // Remove bucket
         buckets[index - min] = null;
      }
   }

   /**
    * Adds an element to the specified bucket.
    * @return The index of the element in the bucket.
    * O(b) if a new bucket has to be added, O(1) otherwise.
    */
   public int add(int index, E element) {
      // Add element
      Bucket<E> bucket = getBucket(index);
      if(bucket == null)
         bucket = addBucket(index);
      bucket.add(element);

      // Maintain data
      size++;
      if(index < low)
         low  = index;
      else if(index > high)
         high = index;

      return bucket.size() - 1;
   }

   /**
    * Returns, but does not remove, the first element in the bucket at the given index.
    * O(1).
    */
   public E peek(int index) {
      Bucket<E> bucket = getBucket(index);
      if(bucket == null)
         return null;
      return bucket.getLast();
   }

   /**
    * Removes and returns the first element in the bucket at the given index.
    * O(1).
    */
   public E pop(int index) {
      // Retreive element
      Bucket<E> bucket = getBucket(index);
      if(bucket == null)
         return null;
      E element = bucket.removeLast();

      // Maintain data
      size--;
      if(bucket.isEmpty())
         removeBucket(index);

      return element;
   }

   /**
    * Removes and returns the first element of the lowest non-empty bucket.
    * O(1).
    */
   public E popLowest() {
      return pop(low);
   }

   /**
    * Removes and returns the first element of the highest non-empty bucket.
    * O(1).
    */
   public E popHighest() {
      return pop(high);
   }

   /**
    * Prints this BucketArray with its contents.
    */
   public void print() {
      System.out.println(this);
      for(int i = min; i <= max; i++) {
         Bucket<E> b = getBucket(i);
         System.out.println("\t" + i + ": " + (b == null ? "[]" : b));
      }
   }

   @Override
   public String toString() {
      return getClass().getName() + "[" + min + ", " + max + "](" + size + ")";
   }
}
