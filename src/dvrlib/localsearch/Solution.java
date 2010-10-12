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
   public int getIterationCount();

   /**
    * Increases the number of iterations it took to reach this solution.
    */
   public void increaseIterationCount();
}
