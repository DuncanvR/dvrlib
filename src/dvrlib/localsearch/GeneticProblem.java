/*
 * DvRlib - Local search
 * Duncan van Roermund, 2012
 * GeneticProblem.java
 */

package dvrlib.localsearch;

public interface GeneticProblem<S extends Solution, E extends Comparable<E>> extends BoundableProblem<S, E> {
   /**
    * Makes the first solution look most like the second one.
    */
   public void ensureMostCommon(S s1, S s2);

   /**
    * Returns the weight of the given evaluation.
    */
   public double weight(E e);
   /**
    * Returns the weight of the given solution.
    * @see GeneticProblem#weight(java.lang.Comparable)
    */
   public double weight(S s);
   /**
    * Returns the weight of the given solution at the given iteration.
    * @param iterationNumber Indicates the iteration number in the current search.
    * @see GeneticProblem#weight(java.lang.Comparable)
    */
   public double weight(S s, long iterationNumber);
   /**
    * Returns the weight of the current solution of the given search state.
    * @see GeneticProblem#weight(Solution, long)
    */
   public double weight(SearchState<GeneticProblem<S, E>, S> ss);
}
