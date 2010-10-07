/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * AbstractSolution.java
 */

package dvrlib.localsearch;

public class AbstractSolution implements Solution {
   protected int iterationCount = 0;

   public int getIterationCount() {
      return iterationCount;
   }

   public void setIterationCount(int iterations) {
      iterationCount = iterations;
   }
}
