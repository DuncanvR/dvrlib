/*
 * DvRLib - Local search
 * Duncan van Roermund, 2011-2012
 * AbstractProblem.java
 */

package dvrlib.localsearch;

public abstract class AbstractProblem<S extends Solution, E extends Comparable<E>> implements Problem<S, E> {
   protected final Population<S> solutionPool;

   /**
    * AbstractProblem constructor.
    * @param solutionPoolSize The maximum number of best solutions that will be kept track of.
    */
   public AbstractProblem(int solutionPoolSize) {
      solutionPool = new TreePopulation<S, E>(this, solutionPoolSize);
   }

   /**
    * Returns the solution pool.
    */
   public final Population<S> solutions() {
      return solutionPool;
   }

   /**
    * Returns the best solution currently known.
    */
   @Override
   public final S bestSolution() {
      return solutionPool.peekBest();
   }

   /**
    * Returns true if the first of the given evaluations is better than the second.
    */
   @Override
   public boolean better(E e1, E e2) {
      return (e1 == null ? false : e1.compareTo(e2) == direction());
   }
   /**
    * Returns true if the first of the given solutions is better than the second, i.e. <tt>better(evaluate(s1), evaluate(s2))</tt>.
    */
   @Override
   public boolean better(S s1, S s2) {
      return (s1 == null ? false : (s2 == null ? true : (s1.equals(s2) ? false : better(evaluate(s1), evaluate(s2)))));
   }
   /**
    * Returns true if the current solution of the given search state is better than the given evaluation, i.e. <tt>better(evaluate(ss), e)</tt>.
    */
   @Override
   public boolean better(SearchState<? extends Problem<S, E>, S> ss, E e) {
      return (ss == null ? false : better(evaluate(ss), e));
   }
   /**
    * Returns true if the current solution of the given search state is better than the given solution, i.e. <tt>better(evaluate(ss), evaluate(s, ss.iterationCount()))</tt>.
    */
   @Override
   public boolean better(SearchState<? extends Problem<S, E>, S> ss, S s) {
      return (ss == null ? false : (s == null ? true : better(evaluate(ss), evaluate(s, ss.iterationCount()))));
   }
   /**
    * Returns true if the first of the given evaluations is better than or equal to the second.
    */
   @Override
   public boolean betterEq(E e1, E e2) {
      return (e1 == null ? false : e1.compareTo(e2) != -direction());
   }
   /**
    * Returns true if the first of the given solutions is better than or equal to the second, i.e. <tt>betterEq(evaluate(s1), evaluate(s2))</tt>.
    */
   @Override
   public boolean betterEq(S s1, S s2) {
      return (s1 == null ? false : (s2 == null ? true : (s1.equals(s2) ? true : betterEq(evaluate(s1), evaluate(s2)))));
   }
   /**
    * Returns true if the current solution of the given search state is better than or equal to the given evaluation, i.e. <tt>betterEq(evaluate(ss), e)</tt>.
    */
   @Override
   public boolean betterEq(SearchState<? extends Problem<S, E>, S> ss, E e) {
      return (ss == null ? false : betterEq(evaluate(ss), e));
   }
   /**
    * Returns true if the current solution of the given search state is better than or equal to the given solution, i.e. <tt>betterEq(evaluate(ss), evaluate(s, ss.iterationCount()))</tt>.
    */
   @Override
   public boolean betterEq(SearchState<? extends Problem<S, E>, S> ss, S s) {
      return (ss == null ? false : (s == null ? true : betterEq(evaluate(ss), evaluate(s, ss.iterationCount()))));
   }

   /**
    * Returns the evaluation of the given solution, ignoring <tt>iterationNumber</tt>.
    * Override this method to implement an evaluation function which takes the iteration number into account.
    */
   @Override
   public E evaluate(S s, long iterationNumber) {
      return evaluate(s);
   }
   /**
    * Returns the evaluation of the current solution of the given search state.
    * @see Problem#evaluate(Solution, long)
    */
   @Override
   public E evaluate(SearchState<? extends Problem<S, E>, S> ss) {
      return evaluate(ss.solution(), ss.iterationCount());
   }

   /**
    * Compares the given solution to the current best, and saves it if it is good enough.
    * @return A boolean indicating whether the given solution was actually saved.
    * @see Problem#copySolution(Solution)
    */
   @Override
   public final boolean saveSolution(S s) {
      return solutionPool.add(copySolution(s));
   }
}
