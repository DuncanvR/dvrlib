/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Combiner.java
 */

package dvrlib.localsearch;

public interface Combiner<S extends Solution> {
   /**
    * Returns a combination of the two given solutions.
    */
   public S combine(S s1, S s2);

   /**
    * Mutates the given solution into one that closely resembles it.
    */
   public void mutate(S solution);
}
