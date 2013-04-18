/*
 * DvRlib - Generic
 * Duncan van Roermund, 2012
 * ReadOnlyIterator.java
 */

package dvrlib.generic;

import java.util.Iterator;

public class ReadOnlyIterator<E> implements Iterator<E> {
   protected final Iterator<E> it;

   public ReadOnlyIterator(Iterator<E> iterator) {
      it = iterator;
   }

   @Override
   public boolean hasNext() {
      return it.hasNext();
   }

   @Override
   public E next() {
      return it.next();
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException("dvrlib.generic.ReadOnlyIterator#remove() is not supported");
   }
}
