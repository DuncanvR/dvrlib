/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2013
 * OffspringSelector.java
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

public interface OffspringSelector<S extends Solution, E extends Comparable<E>> {
   /**
    * Selects the solution that will be added to the population from the given offspring.
    * @param ss        The current state of the genetic search algorithm.
    * @param offspring The set of solutions generated by the combiner.
    * @param bound     The current worst solution in the population; any solutions that are not an improvement over this can be ignored. Can be <code>null</code>.
    */
   public S select(GeneticLS<S, E>.SearchState ss, java.util.NavigableSet<S> offspring, S bound);
}
