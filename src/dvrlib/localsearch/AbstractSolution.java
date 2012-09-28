/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * AbstractSolution.java
 */

package dvrlib.localsearch;

public abstract class AbstractSolution implements Solution {
   protected long iterationCount = 0;

   /**
    * Returns the number of iterations it took to reach this solution.
    * O(1).
    */
   @Override
   public long iterationCount() {
      return iterationCount;
   }

   /**
    * Sets the number of iterations it took to reacht this solution.
    * O(1).
    */
   @Override
   public void setIterationCount(long n) {
      iterationCount = n;
   }

   /**
    * Default implementation of this method throws an exception.
    * @see dvrlib.localsearch.Solution#ensureMostCommon(dvrlib.localsearch.Solution)
    */
   @Override
   public void ensureMostCommon(Solution that) {
      throw new RuntimeException("dvrlib.localsearch.AbstractSolution#ensureMostCommon(dvrlib.localsearch.Solution) has not been implemented");
   }
}
