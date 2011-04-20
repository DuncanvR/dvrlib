/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * AbstractMinProblem.java
 */

package dvrlib.localsearch;

public abstract class AbstractMinProblem<S extends Solution, E extends Number & Comparable<E>> extends AbstractProblem<S, E> {
   /**
    * Returns the direction of the search, e.g. <tt>-1d</tt>.
    * @see Math#signum(double)
    */
   @Override
   public double getDirection() {
      return -1d;
   }

   /**
    * Returns the weight of the given evaluation.
    * Optional operation used by GeneticLS to insert solutions into the population.
    */
   @Override
   public double getWeight(E e) {
      return (1 / e.doubleValue());
   }
}
