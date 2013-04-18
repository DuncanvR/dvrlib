/*
 * DvRlib - Local search
 * Duncan van Roermund, 2012
 * GeneticPopulation.java
 */

package dvrlib.localsearch;

public abstract class GeneticPopulation<S extends Solution> extends Population<S> {
   /**
    * Returns but does not remove a random solution from this population.
    */
   public abstract S peekRandom();
}
