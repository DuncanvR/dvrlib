/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * AbstractSolution.java
 */

package dvrlib.localsearch;

public class AbstractSolution implements Solution {
   protected int iterationCount = 0;

   /**
    * Returns the number of iterations it took to reach this solution.
    * O(1).
    */
   public int getIterationCount() {
      return iterationCount;
   }

   /**
    * Increases the number of iterations it took to reach this solution.
    * O(1).
    */
   public void increaseIterationCount(int i) {
      iterationCount += i;
   }
}
