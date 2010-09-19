/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * MultiStartLS.java
 */

package dvrlib.localsearch;

public class MultiStartLS extends LocalSearch {
   protected LocalSearch ls;
   protected int count;

   /**
    * MultiStartLS Constructor.
    * @param ls    This LocalSearch will be used repeatedly to search for a Solution.
    * @param count The number of times a Solution is searched for.
    */
   public MultiStartLS(LocalSearch ls, int count) {
      this.ls    = ls;
      this.count = count;
   }

   @Override
   public Solution search(Problem p, Solution bestSolution) {
      for(int i = 0; i < count; i++) {
         Solution newSolution = ls.search(p, p.randomSolution());
         if(newSolution.evaluate() < bestSolution.evaluate())
            bestSolution   = newSolution;
      }
      return bestSolution;
   }
}
