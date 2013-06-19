/*
 * DvRlib - Matrix
 * Copyright (C) Duncan van Roermund, 2010-2012
 * AbstractAdjustableMatrix.java
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

public abstract class AbstractAdjustableMatrix<E> extends AbstractMatrix<E> {
   protected Vector<Vector<E>> elements;

   /**
    * AbstractAdjustableMatrix constructor.
    * @param m The number of rows.
    * @param n The number of columns.
    * @see AbstractMatrix#AbstractMatrix(int, int)
    * O(1).
    */
   public AbstractAdjustableMatrix(int m, int n) {
      super(m, n);
   }

   /**
    * Adds a column to this matrix.
    */
   public abstract void addColumn();

   /**
    * Adds a row to this matrix.
    */
   public abstract void addRow();
}
