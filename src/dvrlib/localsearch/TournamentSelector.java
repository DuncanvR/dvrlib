/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2013
 * TournamentSelector.java
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

import java.util.Comparator;
import java.util.LinkedList;

public class TournamentSelector<S extends Solution> implements Selector<S> {
   protected final int tournamentSize;

   /**
    * TournamentSelector constructor.
    * @param tournamentSize The number of solutions that will be included in each tournament.
    */
   public TournamentSelector(int tournamentSize) {
      this.tournamentSize = tournamentSize;
   }

   /**
    * Selects <code>count</code> solutions from the given population using tournament selection.
    */
   public Iterable<S> select(GeneticLS<S, ?>.SearchState ss, int count) {
      LinkedList<S> ls = new LinkedList<S>();
      for(int i = 0; i < count; i++) {
         S winner = null;
         for(int j = 0; j < tournamentSize; j++) {
            S candidate = ss.population.peekRandom();
            if(ss.problem.better(candidate, winner))
               winner = candidate;
         }
         ls.add(winner);
      }
      return ls;
   }
}
