/*
 * DvRLib - Local search
 * Duncan van Roermund, 2011-2012
 * AbstractSearchState.java
 */

package dvrlib.localsearch;

public abstract class AbstractSearchState<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> implements SearchState<P, S> {
   protected final P    problem  ;
   protected       long iteration;

   public      AbstractSearchState(P problem) {
      this.problem = problem;
   }

   @Override
   public P    problem()                      {
      return problem;
   }

   @Override
   public void saveSolution()                 {
      problem.saveSolution(solution());
   }

   @Override
   public long iterationCount()               {
      return iteration;
   }

   @Override
   public void increaseIterationCount(long n) {
      iteration += n;
   }

}
