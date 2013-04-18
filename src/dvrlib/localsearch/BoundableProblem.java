/*
 * DvRlib - Local search
 * Duncan van Roermund, 2012
 * BoundableProblem.java
 */

package dvrlib.localsearch;

public interface BoundableProblem<S extends Solution, E extends Comparable<E>> extends Problem<S, E> {
   /**
    * Returns a bound on the evaluation of the given solution.
    * This method should return quickly and give a value that in no case will be worse than the actual evaluation,
    *    i.e. <code>betterEq(evaluationBound(s), evaluate(s))</code> should always hold, for any solution <code>s</code>.
    */
   public E evaluationBound(S s);
   /**
    * Returns a bound on the evaluation of the given solution at the indicated iteration.
    * This method should return quickly and give a value that in no case will be worse than the actual evaluation,
    *    i.e. <code>betterEq(evaluationBound(s, n), evaluate(s, n))</code> should always hold, for any solution <code>s</code>.
    */
   public E evaluationBound(S s, long iterationNumber);
   /**
    * Returns a bound on the evaluation of the current solution of the given search state.
    * This method should return quickly and give a value that in no case will be worse than the actual evaluation,
    *    i.e. <code>betterEq(evaluationBound(ss), evaluate(ss))</code> should always hold, for any solution <code>s</code>.
    */
   public E evaluationBound(SearchState<? extends Problem<S, E>, S> ss);
}
