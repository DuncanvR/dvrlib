/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2010-2013
 * Pair.java
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

public class Pair<A, B> {
   public A a;
   public B b;

   /**
    * Pair constructor.
    * @param a The first element in this pair.
    * @param b The second element in this pair.
    */
   public Pair(A a, B b) {
      this.a = a;
      this.b = b;
   }

   /**
    * Returns <code>true</code> if the given object is equal to this one, <code>false</code> otherwise.
    * O(a.equals() + b.equals()) if the given object is an instance of Pair, O(1) otherwise.
    */
   @Override
   public boolean equals(Object obj) {
      if(obj instanceof Pair) {
         Pair that = (Pair) obj;
         return (a == null ? that.a == null : a.equals(that.a)) &&
                (b == null ? that.b == null : b.equals(that.b));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int hash = 547;
      hash += 2 * hash + (a == null ? 0 : a.hashCode());
      hash += 2 * hash + (b == null ? 0 : b.hashCode());
      return hash;
   }

   @Override
   public String toString() {
      return this.getClass().getName() + "(" + a + ", " + b + ")";
   }
}
