/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * Combiner.java
 */

package dvrlib.localsearch;

public interface Combiner<P extends GeneticProblem<S, ?>, S extends Solution> {
   /**
    * Reinitializes this combiner, used when a new search is started.
    * @see LocalSearch#search(Problem, Solution)
    */
   public void reinitialize();

   /**
    * Returns a set of combinations of the two given solutions.
    * The resulting set should provide an appropriate ordering on the solutions if GeneticLS.CombinerStrategy.FirstImprovement is used.
    * This method may also introduce mutations after creating a new solution.
    * @see GeneticLS.CombinerStrategy
    */
   public java.util.TreeSet<S> combine(SearchState<P, S> ss, S s1, S s2);

   /**
    * Creates a new population with at least the given solutions and at most <code>popSize</code> solutions.
    * A simple implementation would include the given solutions, and some random solutions.
    */
   public GeneticPopulation<S> createPopulation(P problem, Iterable<S> solution, int popSize);
}
