/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010-2012
 * AbstractMatrix.java
 */

package dvrlib.generic;

public abstract class AbstractMatrix<E> {
   /**
    * Returns the value at the given index.
    * @param i The row index.
    * @param j The column index.
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
