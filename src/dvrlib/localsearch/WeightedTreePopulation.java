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
   protected final int                  size;

   public WeightedTreePopulation(GeneticProblem<S, ?> problem, int size) {
      this.problem = problem;
      this.size    = size;
   }

   /**
    * Adds the given solution to this population if it is not already present.
    * If this population is full and the given solution is better than the the current worst, the worst solution is removed.
    * @return A boolean indicating whether the solution was actually added.
    * @see Population#add(Solution)
    */
   @Override
   public boolean add(S solution) {
      if(!contains(solution)) {
         if(size() >= size) {
            if(problem.better(solution, peekWorst()))
               popWorst();
            else
               return false;
         }

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
    * Returns the number of solutions in this population.
    * @see Population#size()
    */
   @Override
   public int size() {
      return keys.size();
   }
}
