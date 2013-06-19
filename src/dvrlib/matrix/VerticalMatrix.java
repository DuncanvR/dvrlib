/*
 * DvRlib - Matrix
 * Copyright (C) Duncan van Roermund, 2010-2012
 * VerticalMatrix.java
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

import java.util.Vector;

/**
 * VerticalMatrix is an implementation of AbstractAdjustableMatrix using vectors for each column.
 * This allows for O(1) addition and removal of columns, but O(m) operations affecting for rows.
 */
public class VerticalMatrix<E> extends AbstractAdjustableMatrix<E> {
   /**
    * VerticalMatrix constructor.
    * @param m The number of rows.
    * @param n The number of columns.
    * O(m).
    */
   public VerticalMatrix(int m, int n) {
      super(m, n);
      elements = new Vector<Vector<E>>(n);
      for(int i = 0; i < n; i++) {
         elements.add(i, new Vector<E>(m));
      }
   }

   /**
    * Returns the value at the given index.
    * @param i The row index.
    * @param j The column index.
    * @return The requested element.
    * @see AbstractMatrix#get(int,int)
    */
   public E get(int i, int j) {
      return elements.get(j).get(i);
   }

   /**
    * Set the value at the given index.
    * @param i The row index.
    * @param j The column index.
    * @param e The element that is to be set at the given index.
    * @see AbstractMatrix#set(int,int,E)
    */
   public void set(int i, int j, E e) {
      elements.get(j).set(i, e);
   }

   /**
    * Adds a column to this matrix.
    * @see AbstractAdjustableMatrix#addColumn()
    */
   public void addColumn() {
      elements.add(new Vector<E>(m));
   }

   /**
    * Adds a row to this matrix.
    * @see AbstractAdjustableMatrix#addRow()
    */
   public void addRow() {
      throw new IllegalStateException("VerticalMatrix.addRow() has not yet been implemented");
   }
}
