/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2013
 * Triple.java
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

public class Triple<A, B, C> {
   public A a;
   public B b;
   public C c;

   /**
    * Triple constructor.
    * @param a The first element in this triple.
    * @param b The second element in this triple.
    * @param c The third element in this triple.
    */
   public Triple(A a, B b, C c) {
      this.a = a;
      this.b = b;
      this.c = c;
   }

   /**
    * Returns <code>true</code> if the given object is equal to this one, <code>false</code> otherwise.
    */
   @Override
   public boolean equals(Object obj) {
      if(obj instanceof Triple) {
         Triple that = (Triple) obj;
         return (a == null ? that.a == null : a.equals(that.a)) &&
                (b == null ? that.b == null : b.equals(that.b)) &&
                (c == null ? that.c == null : c.equals(that.c));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int hash = 94;
      hash += 3 * hash + (a == null ? 0 : a.hashCode());
      hash += 3 * hash + (b == null ? 0 : b.hashCode());
      hash += 3 * hash + (c == null ? 0 : c.hashCode());
      return hash;
   }

   @Override
   public String toString() {
      return this.getClass().getName() + "(" + a + ", " + b + ", " + c + ")";
   }
}
