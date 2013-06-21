/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2013
 * ComparableTriple.java
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

public class ComparableTriple<A extends Comparable<A>, B extends Comparable<B>, C extends Comparable<C>>
             extends Triple<A, B, C> implements Comparable<ComparableTriple<A, B, C>> {
   /**
    * ComparableTriple constructor.
    * @param a The first element in this triple.
    * @param b The second element in this triple.
    * @param c The third element in this triple.
    * @see Triple#Triple(java.lang.Object, java.lang.Object, java.lang.Object)
    */
   public ComparableTriple(A a, B b, C c) {
      super(a, b, c);
   }

   /**
    * Compares this triple to the given one.
    * @see java.lang.Comparable#compareTo(Object)
    */
   @Override
   public int compareTo(ComparableTriple<A, B, C> that) {
      int cmp = a.compareTo(that.a);
      if(cmp != 0)
         return cmp;
      cmp = b.compareTo(that.b);
      if(cmp != 0)
         return cmp;
      return c.compareTo(that.c);
   }
}
