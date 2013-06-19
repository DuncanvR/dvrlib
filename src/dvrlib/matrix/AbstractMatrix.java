/*
 * DvRlib - Matrix
 * Copyright (C) Duncan van Roermund, 2010-2012
 * AbstractMatrix.java
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

package dvrlib.matrix;

public abstract class AbstractMatrix<E> {
   protected int m, n;

   /**
    * AbstractMatrix constructor.
    * @param m The number of rows.
    * @param n The number of columns.
    * O(1).
    */
   public AbstractMatrix(int m, int n) {
      this.m = m;
      this.n = n;
   }

   /**
    * Returns the number of columns in this matrix.
    * @return The number of columns.
    */
   public int countColumns() {
      return m;
   }

   /**
    * Returns the number of rows in this matrix.
    * @return The number of rows.
    */
   public int countRows() {
      return n;
   }

   /**
    * Returns the value at the given index.
    * @param i The row index.
    * @param j The column index.
    * @return The requested element.
    */
   public abstract E get(int i, int j);

   /**
    * Set the value at the given index.
    * @param i The row index.
    * @param j The column index.
    * @param e The element that is to be set at the given index.
    */
   public abstract void set(int i, int j, E e);
}
