/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * AbstractMaxProblem.java
 */

package dvrlib.localsearch;

public abstract class AbstractMaxProblem<S extends Solution, E extends Comparable<E>> extends AbstractProblem<S, E> {
   /**
    * Returns the direction of the search, e.g. <tt>1d</tt>.
    * @see dvrlib.localsearch.Problem#direction()
    */
   @Override
   public int direction() {
      return 1;
   }
}
