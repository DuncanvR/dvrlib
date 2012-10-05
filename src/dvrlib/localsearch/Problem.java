/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * Problem.java
 */

package dvrlib.localsearch;

public interface Problem<S extends Solution, E extends Comparable<E>> {
   /**
    * Returns true if the first of the given evaluations is better than the second.
    */
   public boolean better(E e1, E e2);
   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <tt>better(evaluate(s1), evaluate(s2))</tt>.
    */
   public boolean better(S s1, S s2);
   /**
    * Returns true if the current solution of the given search state is better than the given evaluation, i.e. <tt>better(evaluate(ss), e)</tt>.
    */
   public boolean better(SearchState<Problem<S, E>, S> ss, E e);
   /**
    * Returns true if the current solution of the given search state is better than the given solution, i.e. <tt>better(evaluate(ss), evaluate(s, ss.iterationNumber()))</tt>.
    */
   public boolean better(SearchState<Problem<S, E>, S> ss, S s);

   /**
    * Returns true if the first of the given evaluations is better than or equal to the second.
    */
   public boolean betterEq(E e1, E e2);
   /**
    * Returns true if the first of the given solutions is better than or equal to the second, i.e. <tt>betterEq(evaluate(s1), evaluate(s2))</tt>.
    */
   public boolean betterEq(S s1, S s2);
   /**
    * Returns true if the current solution of the given search state is better than or equal to the given evaluation, i.e. <tt>betterEq(evaluate(ss), e)</tt>.
    */
   public boolean betterEq(SearchState<Problem<S, E>, S> ss, E e);
   /**
    * Returns true if the current solution of the given search state is better than or equal to the given solution, i.e. <tt>betterEq(evaluate(ss), evaluate(s, ss.iterationNumber()))</tt>.
    */
   public boolean betterEq(SearchState<Problem<S, E>, S> ss, S s);

   /**
    * Returns the best solution currently known.
    */
   public S bestSolution();
   /**
    * Generates a random solution to this problem.
    */
   public S randomSolution();
   /**
    * Compares the given solution to the current best, and saves it if it is good enough.
    * Note that the solution should be copied in order to be saved, as it can (and probably will) be altered.
    */
   public void saveSolution(S s);

   /**
    * Returns the difference between the two given evaluations, e.g. <tt>e1 - e2</tt>.
    * Optional operation used by SimulatedAnnealingLS to determine how bad a worsening of a solution is.
    */
   public E diffEval(E e1, E e2);

   /**
    * Returns the direction of the search, e.g. 1 for a maximizing and -1 for a minimizing problem.
    */
   public int direction();

   /**
    * Returns the evaluation of the given solution.
    */
   public E evaluate(S s);
   /**
    * Returns the evaluation of the given solution at the indicated iteration.
    */
   public E evaluate(S s, long iterationNumber);
   /**
    * Returns the evaluation of the current solution of the given search state.
    */
   public E evaluate(SearchState<Problem<S, E>, S> ss);

   /**
    * Makes the first solution look most like the second one.
    * Optional operation used by GeneticLS for ambiguous solutions.
    */
   public void ensureMostCommon(S s1, S s2);

   /**
    * Returns the weight of the given evaluation.
    * Optional operation used by GeneticLS to insert solutions into the population.
    */
   public double weight(E e);
   /**
    * Returns the weight of the given solution.
    * Optional operation used by GeneticLS to insert solutions into the population.
    * @see Problem#weight(java.lang.Comparable)
    */
   public double weight(S s);
   /**
    * Returns the weight of the given solution at the given iteration.
    * Optional operation used by GeneticLS to insert solutions into the population.
    * @param iterationNumber Indicates the iteration number in the current search.
    * @see Problem#weight(java.lang.Comparable)
    */
   public double weight(S s, long iterationNumber);
   /**
    * Returns the weight of the current solution of the given search state.
    * Optional operation used by GeneticLS to insert solutions into the population.
    * @see Problem#weight(dvrlib.localsearch.Solution, long)
    */
   public double weight(SearchState<Problem<S, E>, S> ss);
}
