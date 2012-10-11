/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * AbstractMinProblem.java
 */

package dvrlib.localsearch;

public abstract class AbstractMinProblem<S extends Solution, E extends Comparable<E>> extends AbstractProblem<S, E> {
   /**
    * Returns the direction of the search, e.g. <tt>-1d</tt>.
    * @see Problem#direction()
    */
   @Override
   public int direction() {
      return -1;
   }
}
