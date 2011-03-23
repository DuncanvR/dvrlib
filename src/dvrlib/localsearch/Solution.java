/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Solution.java
 */

package dvrlib.localsearch;

public interface Solution {
   /**
    * Returns the number of iterations it took to reach this solution.
    */
   public long getIterationCount();

   /**
    * Sets the number of iterations it took to reach this solution.
    */
   public void setIterationCount(long n);

   /**
    * Makes this solution look most like the given one.
    * Optional operation used by GeneticLS for ambiguous solutions.
    */
   public void ensureMostCommon(Solution s);
}
