/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2013
 * CombinedIterable.java
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
import java.util.LinkedList;

/**
 * <code>CombinedIterable</code> iterates in order over the elements of the given array of <code>Iterable</code>s, as if it was one big iterable.
 * <code>null</code> elements in the array are permissible, as well as iterables that return <code>null</code> iterators, like <code>dvrlib.generic.IterableOnce</code>.
 */
public class CombinedIterable<E> implements Iterable<E> {
   public class CombinedIterator implements Iterator<E> {
      protected Iterator<Iterable<E>> esIt = ess.iterator();
      protected Iterator<E>           eIt  = null;

      public CombinedIterator() {
         nextIterator();
      }

      protected void nextIterator() {
         eIt = null;
         while(esIt.hasNext() && (eIt == null || !eIt.hasNext())) {
            Iterable<E> es = esIt.next();
            if(es != null)
               eIt = es.iterator();
         }
      }

      @Override
      public boolean hasNext() {
         return (eIt != null && eIt.hasNext());
      }

      @Override
      public E next() {
         E e = eIt.next();
         if(!eIt.hasNext())
            nextIterator();
         return e;
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
      }
   }

   protected final LinkedList<Iterable<E>> ess;

   public CombinedIterable() {
      ess = new LinkedList<Iterable<E>>();
   }

   public CombinedIterable(LinkedList<Iterable<E>> ess) {
      this.ess = ess;
   }

   public CombinedIterable(Iterable<E>[] ess) {
      this();
      for(Iterable<E> es : ess) {
         combine(es);
      }
   }

   /**
    * Appends the given iterable to the list.
    */
   public void combine(Iterable<E> es) {
      ess.add(es);
   }

   @Override
   public Iterator<E> iterator() {
      return new CombinedIterator();
   }
}
