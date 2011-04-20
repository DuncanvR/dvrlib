/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * AbstractMinProblem.java
 */

package dvrlib.localsearch;

public abstract class AbstractMinProblem<S extends Solution, E extends Number & Comparable<E>> implements Problem<S, E> {
   /**
    * Returns an evaluation with the value 0.
    */
   protected abstract E zeroEval();

   /**
    * Returns true if the first of the given evaluations is better than the second, i.e. <tt>diffEval(e1, e2) &lt; 0</tt>.
    */
   @Override
   public boolean better(E e1, E e2) {
      return (diffEval(e1, e2).compareTo(zeroEval()) < 0);
   }

   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <tt>evaluate(s1).better(evaluate(s2))</tt>.
    * @param iterationNumber Indicates the iteration number in the current search.
    */
   @Override
   public boolean better(S s1, S s2, long iterationNumber) {
      return (s1 == null ? false : (s2 == null ? true : better(evaluate(s1, iterationNumber), evaluate(s2, iterationNumber))));
   }

   /**
    * Returns true if the first of the given evaluations is better than or equal to the second, i.e. <tt>diffEval(e1, e2) &lt;= 0</tt>.
    */
   @Override
   public boolean betterEq(E e1, E e2) {
      return (diffEval(e1, e2).compareTo(zeroEval()) <= 0);
   }

   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <tt>evaluate(s1).betterEq(evaluate(s2))</tt>.
    * @param iterationNumber Indicates the iteration number in the current search.
    */
   @Override
   public boolean betterEq(S s1, S s2, long iterationNumber) {
      return (s1 == null ? false : (s2 == null ? true : betterEq(evaluate(s1, iterationNumber), evaluate(s2, iterationNumber))));
   }

   /**
    * Returns the weight of the given evaluation.
    * Optional operation used by GeneticLS to insert solutions into the population.
    */
   @Override
   public double getWeight(E e) {
      return (1 / e.doubleValue());
   }

   /**
    * Returns the weight of the given solution.
    * Optional operation used by GeneticLS to insert solutions into the population.
    * @param iterationNumber Indicates the iteration number in the current search.
    */
   @Override
   public double getWeight(S s, long iterationNumber) {
      return getWeight(evaluate(s, iterationNumber));
   }
}
