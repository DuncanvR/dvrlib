/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2013
 * ComparableQuadruple.java
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

public class ComparableQuadruple<A extends Comparable<A>, B extends Comparable<B>, C extends Comparable<C>, D extends Comparable<D>>
             extends Quadruple<A, B, C, D> implements Comparable<ComparableQuadruple<A, B, C, D>> {
   /**
    * ComparableQuadruple constructor.
    * @param a The first element in this quadruple.
    * @param b The second element in this quadruple.
    * @param c The third element in this quadruple.
    * @param d The fourth element in this quadruple.
    * @see Quadruple#Quadruple(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object)
    */
   public ComparableQuadruple(A a, B b, C c, D d) {
      super(a, b, c, d);
   }

   /**
    * Compares this quadruple to the given one.
    * @see java.lang.Comparable#compareTo(Object)
    */
   @Override
   public int compareTo(ComparableQuadruple<A, B, C, D> that) {
      int cmp = a.compareTo(that.a);
      if(cmp != 0)
         return cmp;
      cmp = b.compareTo(that.b);
      if(cmp != 0)
         return cmp;
      cmp = c.compareTo(that.c);
      if(cmp != 0)
         return cmp;
      return d.compareTo(that.d);
   }
}
