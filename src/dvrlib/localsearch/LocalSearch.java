/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2010-2013
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
    * @param bound     When a solution is found that is better or equal to the given value, the search is stopped.
    * @param timeLimit The search will be stopped if more than the given amount of milliseconds have passed.
    *                  Note that most algorithms will only check the elapsed time once every iteration, so they might take a little longer.
    *                  A negative value indicates there is no time limit.
    * @see LocalSearch#search(Problem, Comparable, Solution)
    */
   public S search(P problem, E bound, long timeLimit) {
      return search(problem, bound, timeLimit, problem.randomSolution());
   }

   /**
    * Searches for a solution for the given problem, starting from the given solution.
    * With asserions enabled, this method first checks none of the arguments are null.
    * @param problem   The problem instance.
    * @param bound     The target evaluation value.
    * @param timeLimit The number of milliseconds after which the search is aborted.
    * @param solution  The starting solution.
    * @see LocalSearch#search(Object, Object)
    */
   public S search(P problem, E bound, long timeLimit, S solution) {
      assert (problem  != null) : "Problem should not be null";
      assert (bound    != null) : "Bound should not be null";
      assert (solution != null) : "Solution should not be null";
      return doSearch(problem, bound, (timeLimit >= 0 ? timeLimit + System.currentTimeMillis() : Long.MAX_VALUE), solution);
   }

   /**
    * Implementation of search(), overwritten by subclasses.
    * @param problem       The problem instance.
    * @param bound         The target value of the search.
    * @param maxTimeMillis The time limit, i.e. the bound on System.currentTimeMillis().
    * @param solution      The solution to start the search from.
    * @see LocalSearch#search(Problem, Comparable, long, Solution)
    */
   protected abstract S doSearch(P problem, E bound, long maxTimeMillis, S solution);
}
