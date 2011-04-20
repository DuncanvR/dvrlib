/*
 * DvRLib - Local search
 * Duncan van Roermund, 2011
 * AbstractProblem.java
 */

package dvrlib.localsearch;

public abstract class AbstractProblem<S extends Solution, E extends Number & Comparable<E>> implements Problem<S, E> {
   @Override
   /**
    * Returns the evaluation of the given solution, ignoring <tt>iterationNumber</tt>.
    * Override this method to implement an evaluation function which takes the iteration number into account.
    */
   public E evaluate(S s, long iterationNumber) {
      return evaluate(s);
   }
   /**
    * Returns the evaluation of the current solution of the given search state.
    * @see Problem#evaluate(dvrlib.localsearch.Solution, long)
    */
   @Override
   public E evaluate(SearchState<Problem<S, E>, S> ss) {
      return evaluate(ss.getSolution(), ss.getIterationNumber());
   }

   /**
    * Returns true if the first of the given evaluations is better than the second.
    */
   @Override
   public boolean better(E e1, E e2) {
      return (Math.signum(diffEval(e1, e2).doubleValue()) == getDirection());
   }
   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <tt>better(evaluate(s1), evaluate(s2))</tt>.
    */
   @Override
   public boolean better(S s1, S s2) {
      return (s1 == null ? false : (s2 == null ? true : better(evaluate(s1), evaluate(s2))));
   }
   /**
    * Returns true if the current solution of the given search state is better than the given evaluation, i.e. <tt>better(evaluate(ss), e)</tt>.
    */
   @Override
   public boolean better(SearchState<Problem<S, E>, S> ss, E e) {
      return (ss == null ? false : better(evaluate(ss), e));
   }
   /**
    * Returns true if the current solution of the given search state is better than the given solution, i.e. <tt>better(evaluate(ss), evaluate(s, ss.getIterationNumber()))</tt>.
    */
   @Override
   public boolean better(SearchState<Problem<S, E>, S> ss, S s) {
      return (ss == null ? false : (s == null ? true : better(evaluate(ss), evaluate(s, ss.getIterationNumber()))));
   }
   /**
    * Returns true if the first of the given evaluations is better than or equal to the second.
    */
   @Override
   public boolean betterEq(E e1, E e2) {
      double sgn = Math.signum(diffEval(e1, e2).doubleValue());
      return sgn == 0 || sgn == getDirection();
   }
   /**
    * Returns true if the first of the given solutions is better than or equal to the second, i.e. <tt>betterEq(evaluate(s1), evaluate(s2))</tt>.
    */
   @Override
   public boolean betterEq(S s1, S s2) {
      return (s1 == null ? false : (s2 == null ? true : betterEq(evaluate(s1), evaluate(s2))));
   }
   /**
    * Returns true if the current solution of the given search state is better than or equal to the given evaluation, i.e. <tt>betterEq(evaluate(ss), e)</tt>.
    */
   @Override
   public boolean betterEq(SearchState<Problem<S, E>, S> ss, E e) {
      return (ss == null ? false : betterEq(evaluate(ss), e));
   }
   /**
    * Returns true if the current solution of the given search state is better than or equal to the given solution, i.e. <tt>betterEq(evaluate(ss), evaluate(s, ss.getIterationNumber()))</tt>.
    */
   @Override
   public boolean betterEq(SearchState<Problem<S, E>, S> ss, S s) {
      return (ss == null ? false : (s == null ? true : betterEq(evaluate(ss), evaluate(s, ss.getIterationNumber()))));
   }

   /**
    * Returns the weight of the given solution.
    * Optional operation used by GeneticLS to insert solutions into the population.
    * @param iterationNumber Indicates the iteration number in the current search.
    * @see Problem#getWeight(java.lang.Comparable)
    * @see AbstractProblem#evaluate(dvrlib.localsearch.Solution, long)
    */
   @Override
   public double getWeight(S s, long iterationNumber) {
      return getWeight(evaluate(s, iterationNumber));
   }
   /**
    * Returns the weight of the current solution of the given search state.
    * Optional operation used by GeneticLS to insert solutions into the population.
    * @see AbstractProblem#getWeight(dvrlib.localsearch.Solution, long)
    */
   @Override
   public double getWeight(SearchState<Problem<S, E>, S> ss) {
      return getWeight(ss.getSolution(), ss.getIterationNumber());
   }
}
