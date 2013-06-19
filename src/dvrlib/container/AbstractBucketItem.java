/*
 * DvRlib - Container
 * Copyright (C) Duncan van Roermund, 2010
 * AbstractBucketItem.java
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
