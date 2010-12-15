/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Problem.java
 */

package dvrlib.localsearch;

public interface Problem<S extends Solution, E extends Comparable<E>> {
   /**
    * Generates a random solution to this problem.
    */
   public S randomSolution();

   /**
    * Returns the evaluation of the given solution.
    */
   public E evaluate(S s);

   /**
    * Returns the difference between the two given evaluations, i.e. <tt>e1 - e2</tt>, where a negative value means an improvement.
    */
   public E diffEval(E e1, E e2);

   /**
    * Returns true if the first of the given evaluations is better than the second, i.e. <tt>diffEval(e1, e2) &lt; 0</tt>.
    */
   public boolean better(E e1, E e2);

   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <tt>better(evaluate(s1), evaluate(s2))</tt>.
    */
   public boolean better(S s1, S s2);

   /**
    * Returns true if the first of the given evaluations is better than or equal to the second, i.e. <tt>diffEval(e1, e2) &lt;= 0</tt>.
    */
   public boolean betterEq(E e1, E e2);

   /**
    * Returns true if the first of the given solutions is better than or equal to the second, i.e. <tt>betterEq(evaluate(s1), evaluate(s2))</tt>.
    */
   public boolean betterEq(S s1, S s2);

   /**
    * Compares the given solution to the current best, and saves it if it is good enough.
    * Note that the solution should be copied in order to be saved, as it can (and probably will) be altered.
    */
   public void saveSolution(S s);

   /**
    * Returns the best solution currently known.
    */
   public S getBestSolution();
}
