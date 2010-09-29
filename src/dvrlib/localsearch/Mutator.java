/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Mutator.java
 */

package dvrlib.localsearch;

public interface Mutator<S extends Solution> {
   public void mutate(S solution);
}
