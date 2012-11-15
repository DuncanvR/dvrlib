/*
 * DvRLib - Local search
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
    * Searches for an optimal solution for the given problem, starting from a random solution.
    */
   public S search(P problem) {
      return search(problem, problem.randomSolution());
   }

   /**
    * Searches for an optimal solution for the given problem, starting from the given solution.
    */
   public abstract S search(P problem, S solution);
}
