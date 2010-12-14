/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Problem.java
 */

package dvrlib.localsearch;

public interface Problem<S extends Solution, E extends Evaluation> {
   /**
    * Generates a random solution to this problem.
    */
   public S randomSolution();

   /**
    * Returns the evaluation of the given solution.
    */
   public E evaluate(S s);

   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <tt>evaluate(s1).better(evaluate(s2))</tt>.
    */
   public boolean better(S s1, S s2);

   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <tt>evaluate(s1).betterEq(evaluate(s2))</tt>.
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
