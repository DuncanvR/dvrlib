/*
 * DvRLib - Local search
 * Duncan van Roermund, 2011
 * SingularSearchState.java
 */

package dvrlib.localsearch;

public class SingularSearchState<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> extends AbstractSearchState<P, S> {
   protected S solution;

   public SingularSearchState(P problem, S solution) {
      super(problem);
      this.solution = solution;
   }

   @Override
   public S getSolution() {
      return solution;
   }
}