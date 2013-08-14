/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2010-2013
 * MultiStartLS.java
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

public class MultiStartLS<S extends Solution, E extends Comparable<E>> extends LocalSearch<Problem<S, E>, S, E> {
   protected LocalSearch<Problem<S, E>, S, E> ls;
   protected int count;

   /**
    * MultiStartLS Constructor.
    * @param ls    The search algorithm that will be used repeatedly to search for a solution.
    * @param count The number of times to search for a solution.
    */
   public MultiStartLS(LocalSearch<Problem<S, E>, S, E> ls, int count) {
      this.ls    = ls;
      this.count = count;
   }

   /**
    * Sets the criterion for deciding when to save a solution back to the problem.
    * @see LocalSearch.setSavingCriterion(LocalSearch.SavingCriterion)
    */
   @Override
   public void setSavingCriterion(SavingCriterion savingCriterion) {
      ls.setSavingCriterion(savingCriterion);
   }

   /**
    * Searches for a solution for the given problem by applying the predefined search algorithm to multiple random solution.
    */
   @Override
   public S search(Problem<S, E> problem, E bound, long timeLimit) {
      S bestSolution = problem.randomSolution();
      long maxTimeMillis = (timeLimit >= 0 ? timeLimit + System.currentTimeMillis() : Long.MAX_VALUE);
      for(int i = 0; i < count && !problem.betterEq(bestSolution, bound); i++) {
         S newSolution = ls.search(problem, bound, maxTimeMillis - System.currentTimeMillis());
         if(problem.better(newSolution, bestSolution))
            bestSolution = newSolution;
      }
      return bestSolution;
   }

   /**
    * Searches for a solution for the given problem by applying the predefined search algorithm to the given solution.
    */
   @Override
   public S search(Problem<S, E> problem, E bound, long timeLimit, S startSolution) {
      S bestSolution = startSolution;
      long maxTimeMillis = (timeLimit >= 0 ? timeLimit + System.currentTimeMillis() : Long.MAX_VALUE);
      for(int i = 0; i < count && !problem.betterEq(bestSolution, bound); i++) {
         S newSolution = ls.search(problem, bound, maxTimeMillis - System.currentTimeMillis(), problem.cloneSolution(startSolution));
         if(problem.better(newSolution, bestSolution))
            bestSolution = newSolution;
      }
      return bestSolution;
   }

   /**
    * Required method for LocalSearch subclasses, not needed for this search strategy.
    */
   @Override
   protected S doSearch(Problem<S, E> problem, E bound, long maxTimeMillis, S solution) {
      throw new IllegalStateException();
   }
}
