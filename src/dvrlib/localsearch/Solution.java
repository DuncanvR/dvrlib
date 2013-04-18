/*
 * DvRlib - Local search
 * Duncan van Roermund, 2010-2012
 * Solution.java
 */

package dvrlib.localsearch;

public interface Solution {
   /**
    * Returns the number of iterations it took to reach this solution.
    */
   public long iterationCount();

   /**
    * Sets the number of iterations it took to reach this solution.
    */
   public void setIterationCount(long n);
}
