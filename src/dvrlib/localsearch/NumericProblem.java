/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * NumericProblem.java
 */

package dvrlib.localsearch;

public interface NumericProblem<S extends Solution, E extends Number & Comparable<E>> extends Problem<S, E> {
   /**
    * Returns the difference between the two given evaluations, e.g. <tt>e1 - e2</tt>.
    */
   public double diffEval(E e1, E e2);
}
