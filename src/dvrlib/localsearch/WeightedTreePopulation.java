/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * WeightedTreePopulation.java
 */

package dvrlib.localsearch;

import dvrlib.container.WeightedTree;

import java.util.Iterator;

public class WeightedTreePopulation<S extends Solution> implements Population<S> {
   protected final WeightedTree<S> tree    = new WeightedTree<S>();
   protected final Problem<S, ?>   problem;

   public WeightedTreePopulation(Problem<S, ?> problem) {
      this.problem = problem;
   }

   @Override
   public void add(S solution) {
      tree.add(problem.weight(solution), solution);
   }

   @Override
   public void clear() {
      tree.clear();
   }

   public boolean contains(S solution) {
      return tree.contains(problem.weight(solution), solution);
   }

   @Override
   public Iterator<S> iterator() {
      return tree.iterator();
   }

   @Override
   public S peekBest() {
      return tree.peekMax().b;
   }

   @Override
   public S peekRandom() {
      return tree.getWeighted(Math.random()).b;
   }

   @Override
   public S peekWorst() {
      return tree.peekMin().b;
   }

   @Override
   public S popWorst() {
      return tree.popMin().b;
   }

   @Override
   public S replaceWorst(S solution) {
      if(problem.better(solution, peekWorst())) {
         add(solution);
         return popWorst();
      }
      else
         return null;
   }

   @Override
   public int size() {
      return tree.size();
   }
}
