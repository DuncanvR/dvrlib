/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * AbstractBoundableProblem.java
 */

package dvrlib.localsearch;

public abstract class AbstractBoundableProblem<S extends Solution, E extends Comparable<E>> extends AbstractProblem<S, E> implements BoundableProblem<S, E> {
   /**
    * AbstractBoundableProblem constructor.
    * @param solutionPoolSize The maximum number of best solutions that will be kept track of.
    * @see AbstractProblem#AbstractProblem(int)
    */
   public AbstractBoundableProblem(int solutionPoolSize) {
      super(solutionPoolSize);
   }

   /**
    * Returns a bound on the evaluation of the given solution, ignoring <code>iterationNumber</code>.
    * Override this method to implement an evaluation-bound function which takes the iteration number into account.
    * @see BoundableProblem#evaluationBound(Solution)
    */
   @Override
   public E evaluationBound(S s, long iterationNumber) {
      return evaluationBound(s);
   }
   /**
    * Returns a bound on the evaluation of the current solution of the given search state, using the current iteration-number.
    * @see BoundableProblem#evaluationBound(Solution, long)
    */
   @Override
   public E evaluationBound(SearchState<? extends Problem<S, E>, S> ss) {
      return evaluationBound(ss.solution(), ss.iterationCount());
   }
}
