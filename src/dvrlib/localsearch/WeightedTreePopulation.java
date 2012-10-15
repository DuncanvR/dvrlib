/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * WeightedTreePopulation.java
 */

package dvrlib.localsearch;

import dvrlib.container.WeightedTree;

import java.util.Iterator;
import java.util.HashSet;

public class WeightedTreePopulation<S extends Solution> implements Population<S> {
   protected final HashSet<S>           keys    = new HashSet<S>();
   protected final GeneticProblem<S, ?> problem;
   protected final WeightedTree<S>      tree    = new WeightedTree<S>();

   public WeightedTreePopulation(GeneticProblem<S, ?> problem) {
      this.problem = problem;
   }

   /**
    * Adds the given solution to this population if it is not already present.
    * @return A boolean indicating whether the solution was actually added.
    * @see Population#add(Solution)
    */
   @Override
   public boolean add(S solution) {
      if(!contains(solution)) {
         keys.add(solution);
         tree.add(problem.weight(solution), solution);
         return true;
      }
      return false;
   }

   /**
    * Removes all solutions from this population.
    * @see Population#clear()
    */
   @Override
   public void clear() {
      keys.clear();
      tree.clear();
   }

   /**
    * Returns whether the given solution is part of this population.
    * @see Population#contains(Solution)
    */
   @Override
   public boolean contains(S solution) {
      return keys.contains(solution);
   }

   /**
    * Returns an iterator to the solutions of this population.
    * @see Iterable#iterator()
    */
   @Override
   public Iterator<S> iterator() {
      return keys.iterator();
   }

   /**
    * Returns but does not remove the best solution in this population.
    * @see Population#peekBest()
    */
   @Override
   public S peekBest() {
      return tree.peekMax().b;
   }

   /**
    * Returns but does not remove a random solution from this population.
    * @see Population#peekRandom()
    */
   @Override
   public S peekRandom() {
      return tree.getWeighted(Math.random()).b;
   }

   /**
    * Returns but does not remove the worst solution in this population.
    * @see Population#peekWorst()
    */
   @Override
   public S peekWorst() {
      return tree.peekMin().b;
   }

   /**
    * Removes and returns the worst solution in this population.
    * @see Population#popWorst()
    */
   @Override
   public S popWorst() {
      S worst = tree.popMin().b;
      keys.remove(worst);
      return worst;
   }

   /**
    * If the given solution is better than the worst solution in this population,
    *    replaces the worst solution with the given one and returns the removed worst.
    * Otherwise, nothing is changed and <tt>null</tt> is returned.
    * @return The replaced solution or <tt>null</tt> if no solution was replaced.
    * @see Population#replaceWorst(Solution)
    */
   @Override
   public S replaceWorst(S solution) {
      if(!contains(solution) && problem.better(solution, peekWorst())) {
         add(solution);
         return popWorst();
      }
      else
         return null;
   }

   /**
    * Returns the number of solutions in this population.
    * @see Population#size()
    */
   @Override
   public int size() {
      return keys.size();
   }
}
