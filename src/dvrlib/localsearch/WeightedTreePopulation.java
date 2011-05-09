/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * WeightedTreePopulation.java
 */

package dvrlib.localsearch;

import dvrlib.generic.WeightedTree;
import java.util.Iterator;

public class WeightedTreePopulation<S extends Solution> implements Population<S> {
   protected final WeightedTree<S> tree    = new WeightedTree();
   protected final Problem<S, ?>   problem;

   public WeightedTreePopulation(Problem<S, ?> problem) {
      this.problem = problem;
   }

   @Override
   public void add(S solution) {
      tree.add(problem.getWeight(solution), solution);
   }

   @Override
   public void clear() {
      tree.clear();
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

   @Override
   public Iterator<S> iterator() {
      return tree.iterator();
   }
}
