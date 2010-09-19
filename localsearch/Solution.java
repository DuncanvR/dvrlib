/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Solution.java
 */

package dvrlib.localsearch;

public interface Solution {
   /**
    * Gives the evaluation of this Solution.
    * Less is better.
    */
   public int evaluate();
}
