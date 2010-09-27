/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * AbstractBucketItem.java
 */

package dvrlib.generic;

public class AbstractBucketItem {
   protected int itemIndex;

   /**
    * Returns the index of this item in its bucket.
    */
   public int getItemIndex() {
      return itemIndex;
   }

   @Override
   public String toString() {
      return "dvrlib.generic.AbstractBucketItem#" + itemIndex;
   }
}
