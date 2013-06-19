/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2010-2012
 * LocalSearch.java
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dvrlib.localsearch;

public abstract class LocalSearch<P extends Problem<S, E>, S extends Solution, E extends Comparable<E>> {
   public enum SavingCriterion { EveryIteration, EveryImprovement, NewBest, EndOnly };
   public enum SearchDirection { Maximisation, Minimisation };

   protected SavingCriterion savingCriterion = SavingCriterion.NewBest;

   public static final int asNumber(SearchDirection direction) {
      switch(direction) {
         case Maximisation:
            return 1;
         case Minimisation:
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
