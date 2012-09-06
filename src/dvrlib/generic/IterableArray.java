/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010-2011
 * IterableArray.java
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
         return array[i++];
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
