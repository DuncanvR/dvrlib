/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2012-2013
 * ComparableIdenticalPair.java
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

public class ComparableIdenticalPair<A extends Comparable<A>> extends ComparablePair<A, A> {
   /**
    * ComparableIdenticalPair constructor.
    * @see ComparablePair#ComparablePair(java.lang.Object, java.lang.Object)
    */
   public ComparableIdenticalPair(A a, A b) {
      super(a, b);
   }
}
