/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010-2012
 * FlatMatrix.java
 */

package dvrlib.generic;

public class FlatMatrix<E> extends AbstractMatrix<E> {
   protected E elements[][];

   /**
    * FlatMatrix constructor.
    * @param m The number of rows.
    * @param n The number of columns.
    */
   public FlatMatrix(Class<E> c, int m, int n) {
      elements = (E[][]) java.lang.reflect.Array.newInstance(c, m, n);
   }

   /**
    * Returns the value at the given index.
    * @param i The row index.
    * @param j The column index.
    * @see AbstractMatrix#get(int,int)
    */
   public E get(int i, int j) {
      return elements[i][j];
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
