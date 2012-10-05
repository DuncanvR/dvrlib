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
}
