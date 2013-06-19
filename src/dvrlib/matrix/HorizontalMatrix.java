/*
 * DvRlib - Matrix
 * Copyright (C) Duncan van Roermund, 2010-2012
 * HorizontalMatrix.java
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
 * HorizontalMatrix is an implementation of AbstractAdjustableMatrix using vectors for each row.
 * This allows for O(1) addition and removal of rows, but O(m) operations affecting for columns.
 */
public class HorizontalMatrix<E> extends AbstractAdjustableMatrix<E> {
   /**
    * HorizontalMatrix constructor.
    * @param m The number of rows.
    * @param n The number of columns.
    * O(m).
    */
   public HorizontalMatrix(int m, int n) {
      super(m, n);
      elements = new Vector<Vector<E>>(m);
      for(int i = 0; i < m; i++) {
         elements.add(i, new Vector<E>(n));
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
      return elements.get(i).get(j);
   }

   /**
    * Set the value at the given index.
    * @param i The row index.
    * @param j The column index.
    * @param e The element that is to be set at the given index.
    * @see AbstractMatrix#set(int,int,E)
    */
   public void set(int i, int j, E e) {
      elements.get(i).set(j, e);
   }

   /**
    * Adds a column to this matrix.
    * @see AbstractAdjustableMatrix#addColumn()
    */
   public void addColumn() {
      throw new IllegalStateException("HorizontalMatrix.addColumn() has not yet been implemented");
   }

   /**
    * Adds a row to this matrix.
    * @see AbstractAdjustableMatrix#addRow()
    */
   public void addRow() {
      elements.add(new Vector<E>(n));
   }
}
