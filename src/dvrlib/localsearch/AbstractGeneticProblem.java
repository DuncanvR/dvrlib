/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * AbstractGeneticProblem.java
 */

package dvrlib.localsearch;

public abstract class AbstractGeneticProblem<S extends Solution, E extends Comparable<E>> extends AbstractProblem<S, E> implements GeneticProblem<S, E> {
   /**
    * AbstractGeneticProblem constructor.
    * @param solutionPoolSize The maximum number of best solutions that will be kept track of.
    * @see AbstractProblem#AbstractProblem(int)
    */
   public AbstractGeneticProblem(int solutionPoolSize) {
      super(solutionPoolSize);
   }

   /**
    * Makes the first solution look most like the second one.
    * Default implementation of this method throws an UnsupportedOperationException.
    * @see GeneticProblem#ensureMostCommon(Solution)
    */
   @Override
   public void ensureMostCommon(S s1, S s2) {
      throw new UnsupportedOperationException(this.getClass().getName() + ".ensureMostCommon(dvrlib.localsearch.Solution) has not been implemented");
   }

   /**
    * Returns the weight of the given solution.
    * @see GeneticProblem#weight(Solution)
    * @see AbstractProblem#evaluate(Solution)
    */
   @Override
   public double weight(S s) {
      return weight(evaluate(s));
   }
   /**
    * Returns the weight of the given solution at the given iteration.
    * @param iterationNumber Indicates the iteration number in the current search.
    * @see AbstractProblem#weight(java.lang.Comparable)
    * @see AbstractProblem#evaluate(Solution, long)
    */
   @Override
   public double weight(S s, long iterationNumber) {
      return weight(evaluate(s, iterationNumber));
   }
   /**
    * Returns the weight of the current solution of the given search state.
    * @see AbstractProblem#weight(Solution, long)
    */
   @Override
   public double weight(SearchState<GeneticProblem<S, E>, S> ss) {
      return weight(ss.solution(), ss.iterationCount());
   }
}
