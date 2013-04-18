/*
 * DvRlib - Local search
 * Duncan van Roermund, 2010-2012
 * LocalSearch.java
 */

package dvrlib.localsearch;

public abstract class LocalSearch<P extends Problem<S, E>, S extends Solution, E extends Comparable<E>> {
   public enum SavingCriterion { EveryIteration, EveryImprovement, NewBest, EndOnly };
   public enum SearchDirection { Maximization, Minimization };

   protected SavingCriterion savingCriterion = SavingCriterion.NewBest;

   public static final int asNumber(SearchDirection direction) {
      switch(direction) {
         case Maximization:
            return 1;
         case Minimization:
            return -1;
         default:
            throw new IllegalArgumentException("Unknown instance of LocalSearch.Direction supplied");
      }
   }

   /**
    * Sets the criterion for deciding when to save a solution back to the problem.
    * @see LocalSearch.SavingCriterion
    */
   public void setSavingCriterion(SavingCriterion savingCriterion) {
      this.savingCriterion = savingCriterion;
   }

   /**
    * Searches for a solution for the given problem, starting from a random solution.
    * When a solution is found that is better or equal to the given bound, the search is stopped.
    * @see LocalSearch#search(Problem, Comparable, Solution)
    */
   public S search(P problem, E bound) {
      return search(problem, bound, problem.randomSolution());
   }

   /**
    * Searches for a solution for the given problem, starting from the given solution.
    * When a solution is found that is better or equal to the given bound, the search is stopped.
    * With asserions enabled, this method first checks none of the arguments are null.
    * @param problem  The problem instance.
    * @param bound    The target evaluation value.
    * @param solution The starting solution.
    */
   public S search(P problem, E bound, S solution) {
      assert (problem  != null) : "Problem should not be null";
      assert (bound    != null) : "Bound should not be null";
      assert (solution != null) : "Solution should not be null";
      return doSearch(problem, bound, solution);
   }

   /**
    * Implementation of search(), overwritten by subclasses.
    * @see LocalSearch#search(Problem, Comparable, Solution)
    */
   protected abstract S doSearch(P problem, E bound, S solution);
}
