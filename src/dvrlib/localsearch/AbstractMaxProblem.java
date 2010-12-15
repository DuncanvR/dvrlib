/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * AbstractMaxProblem.java
 */

package dvrlib.localsearch;

public abstract class AbstractMaxProblem<S extends Solution, E extends Number & Comparable<E>> implements Problem<S, E> {
   /**
    * Returns an evaluation with the value 0.
    */
   protected abstract E zeroEval();

   /**
    * Returns true if the first of the given evaluations is better than the second, i.e. <tt>diffEval(e1, e2) &gt; 0</tt>.
    */
   @Override
   public boolean better(E e1, E e2) {
      return (diffEval(e1, e2).compareTo(zeroEval()) > 0);
   }

   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <tt>evaluate(s1).better(evaluate(s2))</tt>.
    */
   @Override
   public boolean better(S s1, S s2) {
      return (s1 == null ? false : (s2 == null ? true : better(evaluate(s1), evaluate(s2))));
   }

   /**
    * Returns true if the first of the given evaluations is better than or equal to the second, i.e. <tt>diffEval(e1, e2) &gt;= 0</tt>.
    */
   @Override
   public boolean betterEq(E e1, E e2) {
      return (diffEval(e1, e2).compareTo(zeroEval()) >= 0);
   }

   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <tt>evaluate(s1).betterEq(evaluate(s2))</tt>.
    */
   @Override
   public boolean betterEq(S s1, S s2) {
      return (s1 == null ? false : (s2 == null ? true : betterEq(evaluate(s1), evaluate(s2))));
   }

   @Override
   public double getWeight(S s) {
      return evaluate(s).doubleValue();
   }
}
