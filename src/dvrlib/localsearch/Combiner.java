/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * Combiner.java
 */

package dvrlib.localsearch;

public interface Combiner<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> {
   /**
    * Reinitializes this combiner, used when a new search is started.
    * @see LocalSearch#search(dvrlib.localsearch.Problem, dvrlib.localsearch.Solution)
    */
   public void   reinitialize();

   /**
    * Returns a combination of the two given solutions.
    * This method could also introduce mutations after creating the new solution.
    */
   public S combine(SearchState<P, S> ss, S s1, S s2);

   /**
    * Creates a new population with at least the given solution and at most <tt>popSize</tt> solutions.
    * A simple implementation would include the given solution, and some random solutions.
    */
   public WeightedTree<S> createPopulation(P problem, S solution, int popSize);
}
