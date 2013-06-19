/*
 * DvRlib - Matrix
 * Copyright (C) Duncan van Roermund, 2010-2012
 * FlatMatrix.java
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

public class FlatMatrix<E> extends AbstractMatrix<E> {
   protected Object elements[][];

   /**
    * FlatMatrix constructor.
    * @param m The number of rows.
    * @param n The number of columns.
    */
   public FlatMatrix(int m, int n) {
      super(m, n);
      elements = new Object[m][n];
   }

   /**
    * Returns the value at the given index.
    * @param i The row index.
    * @param j The column index.
    * @return The requested element.
    * @see AbstractMatrix#get(int,int)
    */
   @SuppressWarnings("unchecked")
   public E get(int i, int j) {
      return (E) elements[i][j];
   }

   /**
    * Set the value at the given index.
    * @param i The row index.
    * @param j The column index.
    * @param e The element that is to be set at the given index.
    * @see AbstractMatrix#set(int,int,E)
    */
   public void set(int i, int j, E e) {
      elements[i][j] = e;
   }
}
