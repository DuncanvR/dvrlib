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
    * Returns true if the given evaluation is better than the given solution, i.e. <code>better(e, evaluate(s))</code.
    */
   public boolean better(E e, S s);
   /**
    * Returns true if the given solution is better than the given evaluation, i.e. <code>better(evaluate(s), e)</code.
    */
   public boolean better(S s, E e);
   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <code>better(evaluate(s1), evaluate(s2))</code>.
    */
   public boolean better(S s1, S s2);
   /**
    * Returns true if the current solution of the given search state is better than the given evaluation, i.e. <code>better(evaluate(ss), e)</code>.
    */
   public boolean better(SearchState<? extends Problem<S, E>, S> ss, E e);
   /**
    * Returns true if the current solution of the given search state is better than the given solution, i.e. <code>better(evaluate(ss), evaluate(s, ss.iterationNumber()))</code>.
    */
   public boolean better(SearchState<? extends Problem<S, E>, S> ss, S s);

   /**
    * Returns true if the first of the given evaluations is better than or equal to the second.
    */
   public boolean betterEq(E e1, E e2);
   /**
    * Returns true if the given evaluation is better than or equal to the given solution, i.e. <code>betterEq(e, evaluate(s))</code>.
    */
   public boolean betterEq(E e, S s);
   /**
    * Returns true if the given solution is better than or equal to the given evaluation, i.e. <code>betterEq(evaluate(s), e)</code>.
    */
   public boolean betterEq(S s, E e);
   /**
    * Returns true if the first of the given solutions is better than or equal to the second, i.e. <code>betterEq(evaluate(s1), evaluate(s2))</code>.
    */
   public boolean betterEq(S s1, S s2);
   /**
    * Returns true if the current solution of the given search state is better than or equal to the given evaluation, i.e. <code>betterEq(evaluate(ss), e)</code>.
    */
   public boolean betterEq(SearchState<? extends Problem<S, E>, S> ss, E e);
   /**
    * Returns true if the current solution of the given search state is better than or equal to the given solution, i.e. <code>betterEq(evaluate(ss), evaluate(s, ss.iterationNumber()))</code>.
    */
   public boolean betterEq(SearchState<? extends Problem<S, E>, S> ss, S s);

   /**
    * Returns the best solution currently known.
    */
   public S bestSolution();

   /**
    * Returns a copy of the given solution.
    */
   public S copySolution(S s);

   /**
    * Returns the direction of the search.
    * @see LocalSearch.SearchDirection
    */
   public LocalSearch.SearchDirection direction();

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
   public E evaluate(SearchState<? extends Problem<S, E>, S> ss);

   /**
    * Generates a random solution to this problem.
    */
   public S randomSolution();

   /**
    * Compares the given solution to the current best, and saves it if it is good enough.
    * Note that the solution should be copied in order to be saved, as the given one can (and probably will) be altered.
    * @see Problem#copySolution(Solution)
    */
   public boolean saveSolution(S s);
}
