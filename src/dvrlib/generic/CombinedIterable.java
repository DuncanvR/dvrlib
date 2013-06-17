/*
 * DvRlib - Generic
 * Duncan van Roermund, 2013
 * CombinedIterable.java
 */

package dvrlib.generic;

import java.util.Iterator;

/**
 * <code>CombinedIterable</code> iterates in order over the elements of the given array of <code>Iterable</code>s, as if it was one big iterable.
 * <code>null</code> elements in the array are permissible, as well as iterables that return <code>null</code> iterators, like <code>dvrlib.generic.IterableOnce</code>.
 */
public class CombinedIterable<E> implements Iterable<E> {
   public class CombinedIterator implements Iterator<E> {
      protected int         nextIt = 0;
      protected Iterator<E> it     = null;

      public CombinedIterator() {
         nextIterator();
      }

      protected void nextIterator() {
         it = null;
         for(; es.length > nextIt && it == null; nextIt++) {
            if(es[nextIt] != null)
               it = es[nextIt].iterator();
         }
      }

      @Override
      public boolean hasNext() {
         return (it != null && it.hasNext());
      }

      @Override
      public E next() {
         E e = it.next();
         if(!it.hasNext())
            nextIterator();
         return e;
      }

      @Override
      public void remove() {
         it.remove();
      }
   }

   protected final Iterable<E>[] es;

   public CombinedIterable(Iterable<E>[] es) {
      this.es = es;
   }

   @Override
   public Iterator<E> iterator() {
      return new CombinedIterator();
   }
}
