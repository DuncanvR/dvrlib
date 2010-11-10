/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * IterableOnce.java
 */

package dvrlib.generic;

import java.util.Iterator;

/**
 * This class wraps an Iterator in an Iterable, to allow use of it in a for-each loop.
 * As the name suggests, it can only be used once, succesive calls of iterator() will return null.
 */
public class IterableOnce<E> implements Iterable<E> {
   Iterator<E> iterator;

   public IterableOnce(Iterator<E> iterator) {
      this.iterator = iterator;
   }

   @Override
   public Iterator<E> iterator() {
      Iterator<E> it = iterator;
      iterator = null;
      return it;
   }
}
