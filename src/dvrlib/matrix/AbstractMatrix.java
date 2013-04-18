/*
 * DvRlib - Matrix
 * Duncan van Roermund, 2010-2012
 * AbstractMatrix.java
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
