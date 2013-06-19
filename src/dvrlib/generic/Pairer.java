/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2013
 * Pairer.java
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

/**
 * <code>Pairer</code> creates an iterable of all possible pairs of the input elements.
 */
public class Pairer<E> implements Iterable<IdenticalPair<E>> {
   public class PairerIterator implements Iterator<IdenticalPair<E>> {
      protected Iterator<E> itA, itB;
      protected E           a;

      public PairerIterator() {
         itA = elems.iterator();
         itB = elems.iterator();
         if(itA.hasNext()) {
            a = itA.next();
            itB.next();
         }
      }

      public boolean hasNext() {
         return itB.hasNext();
      }

      public IdenticalPair<E> next() {
         IdenticalPair<E> p = new IdenticalPair<E>(a, itB.next());
         if(!itB.hasNext()) {
            a = itA.next();
            itB = elems.iterator();
            while(itB.next() != a) { }
         }
         return p;
      }

      public void remove() {
         throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
      }
   }

   protected final Iterable<E> elems;

   public Pairer(Iterable<E> elems) {
      this.elems = elems;
   }

   @Override
   public Iterator<IdenticalPair<E>> iterator() {
      return new PairerIterator();
   }
}
