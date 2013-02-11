/*
 * DvRLib - Algorithm
 * Duncan van Roermund, 2013
 * KnapsackObject.java
 */

package dvrlib.algorithm;

public interface KnapsackObject {
   /**
    * Returns the value of this knapsack object.
    */
   public double value();

   /**
    * Returns the weight of this knapsack object.
    */
   public int weight();
}
