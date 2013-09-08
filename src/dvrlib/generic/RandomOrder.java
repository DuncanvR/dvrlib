/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2013
 * RandomOrder.java
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

import java.util.List;
import java.util.TreeMap;

public class RandomOrder<E> implements Iterable<E> {
   protected TreeMap<Double, E> map = new TreeMap<Double, E>();

   public RandomOrder(E elements[]) {
      this(new IterableArray<E>(elements));
   }

   public RandomOrder(Iterable<E> elements) {
      for(E e : elements) {
         Double k;
         do {
            k = Math.random();
         }
         while(map.containsKey(k));
         map.put(k, e);
      }
   }

   @Override
   public java.util.Iterator<E> iterator() {
      return map.values().iterator();
   }

   /**
    * Randomises the order of the elements in this iterable.
    */
   public void randomise() {
      java.util.Collection<E> old = map.values();
      map = new TreeMap<Double, E>();
      for(E e : old) {
         Double k;
         do {
            k = Math.random();
         }
         while(map.containsKey(k));
         map.put(k, e);
      }
   }
}
