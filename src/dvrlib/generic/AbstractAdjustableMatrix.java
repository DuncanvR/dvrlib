/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010-2012
 * AbstractAdjustableMatrix.java
 */

package dvrlib.generic;

import java.util.Vector;

public abstract class AbstractAdjustableMatrix<E> extends AbstractMatrix<E> {
   protected Vector<Vector<E>> elements;
   protected int               m, n;

   /**
    * AbstractAdjustableMatrix constructor.
    * @param m The number of rows.
    * @param n The number of columns.
    * O(1).
    */
   public AbstractAdjustableMatrix(int m, int n) {
      this.m = m;
      this.n = n;
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
