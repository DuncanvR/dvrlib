/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2010-2011
 * IterableArray.java
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

package dvrlib.generic;

import java.util.Iterator;

public class IterableArray<E> implements Iterable<E> {
   public class ArrayIterator implements Iterator<E> {
      protected int i = 0;

      @Override
      public boolean hasNext() {
         return (i < array.length);
      }

      @Override
      public E next() {
         try {
            return array[i++];
         }
         catch(ArrayIndexOutOfBoundsException _) {
            throw new java.util.NoSuchElementException();
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
      }
   }

   protected final E array[];

   public IterableArray(E array[]) {
      this.array = array;
   }

   @Override
   public Iterator<E> iterator() {
      return new ArrayIterator();
   }
}
