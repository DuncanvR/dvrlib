/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2010-2012
 * AbstractSolution.java
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

package dvrlib.localsearch;

public abstract class AbstractSolution implements Solution {
   protected long iterationCount = 0;

   /**
    * Returns the number of iterations it took to reach this solution.
    * O(1).
    */
   @Override
   public long iterationCount() {
      return iterationCount;
   }

   /**
    * Sets the number of iterations it took to reacht this solution.
    * O(1).
    */
   @Override
   public void setIterationCount(long n) {
      iterationCount = n;
   }
}
