/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * HillClimbingLS.java
 */

package dvrlib.localsearch;

public class HillClimbingLS extends LocalSearch {

   @Override
   public Solution search(Problem p, Solution s) {
      Mutator<Solution, Object> mutator = p.getMutator();
      Object mutation = null;

      // Keep mutating as long as it improves the solution
      for(double e1 = Double.NEGATIVE_INFINITY, e2 = s.evaluate(); e1 < e2; e1 = e2, e2 = s.evaluate()) {
         mutation = mutator.generateMutation(s);
         mutator.doMutation(s, mutation);
      }
      if(mutation != null) {
         // Undo last mutation
         mutator.undoMutation(s, mutation);
      }

      return s;
   }
}
