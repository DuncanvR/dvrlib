/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * Problem.java
 */

package dvrlib.localsearch;

public interface Problem<S extends Solution, E extends Comparable<E>> {
   /**
    * Returns the difference between the two given evaluations, e.g. <tt>e1 - e2</tt>.
    */
   public S randomSolution();

   /**
    * Returns the evaluation of the given solution.
    * @param iterationNumber Indicates the iteration number in the current search, which can be used to calculate a bonus for certain properties in early iterations.
    *                        A negative value indicates no bonus should be included, and only the real evaluation of the solution should be considered.
    */
   public E evaluate(S s, long iterationNumber);

   /**
    * Returns the difference between the two given evaluations, e.g. <tt>e1 - e2</tt>.
    */
   public E diffEval(E e1, E e2);

   /**
    * Returns true if the first of the given evaluations is better than the second.
    */
   public boolean better(E e1, E e2);

   /**
    * Returns true if the first of the given solutions is better than the second, e.g. <tt>better(evaluate(s1), evaluate(s2))</tt>.
    * @param iterationNumber Indicates the iteration number in the current search.
    */
   public boolean better(S s1, S s2, long iterationNumber);

   /**
    * Returns true if the first of the given evaluations is better than or equal to the second.
    */
   public boolean betterEq(E e1, E e2);

   /**
    * Returns true if the first of the given solutions is better than or equal to the second, e.g. <tt>betterEq(evaluate(s1), evaluate(s2))</tt>.
    * @param iterationNumber Indicates the iteration number in the current search.
    */
   public boolean betterEq(S s1, S s2, long iterationNumber);

   /**
    * Compares the given solution to the current best, and saves it if it is good enough.
    * Note that the solution should be copied in order to be saved, as it can (and probably will) be altered.
    */
   public void saveSolution(S s);

   /**
    * Returns the best solution currently known.
    */
   public S getBestSolution();

   /**
    * Returns the weight of the given evaluation.
    * Optional operation used by GeneticLS to insert solutions into the population.
    */
   public double getWeight(E e);

   /**
    * Returns the weight of the given solution.
    * Optional operation used by GeneticLS to insert solutions into the population.
    * @param iterationNumber Indicates the iteration number in the current search.
    */
   public double getWeight(S s, long iterationNumber);
}
