/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * SimulatedAnnealingLSTest.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimulatedAnnealingLSTest extends    AbstractMinProblem<SimulatedAnnealingLSTest.TestSolution, Integer>
                                      implements Changer<SimulatedAnnealingLSTest, SimulatedAnnealingLSTest.TestSolution, Pair<Integer, Integer>> {
   protected class TestSolution extends AbstractSolution {
      protected int value;
      public TestSolution(int value) {
         this.value = value;
      }
      @Override
      public void ensureMostCommon(dvrlib.localsearch.Solution s) { }
   }

   protected final int    values[] = new int[]{11, 22, 33, 44, 55, 66, 77, 88, 99, 111 };
   protected final Random r        = new Random()                                       ;

   // Problem methods
   @Override
   public Integer evaluate(TestSolution s) {
      return s.value;
   }

   @Override
   public TestSolution randomSolution() {
      return new TestSolution(100);
   }

   @Override
   public void saveSolution(TestSolution s) { }

   @Override
   public TestSolution getBestSolution() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Integer diffEval(Integer e1, Integer e2) {
      return (e1 - e2);
   }

   // Changer methods
   @Override
   public void reinitialize() { }

   @Override
   public Pair<Integer, Integer> generateChange(SearchState<SimulatedAnnealingLSTest, TestSolution> state) {
      return new Pair(state.getSolution().value, values[r.nextInt(values.length)]);
   }

   @Override
   public void doChange(SearchState<SimulatedAnnealingLSTest, TestSolution> state, Pair<Integer, Integer> c) {
      state.getSolution().value = c.b;
   }

   @Override
   public void undoChange(SearchState<SimulatedAnnealingLSTest, TestSolution> state, Pair<Integer, Integer> c) {
      state.getSolution().value = c.a;
   }

   @Override
   public boolean better(Integer e1, Integer e2) {
      boolean b = super.better(e1, e2);
      if((e1 >= e2 && b) || (e1 < e2 && !b))
         System.out.println("\tSATest.better(" + e1 + ", " + e2 + ") = " + b);
      return b;
   }

   @Override
   public boolean betterEq(Integer e1, Integer e2) {
      boolean b = super.betterEq(e1, e2);
      if((e1 > e2 && b) || (e1 <= e2 && !b))
         System.out.println("\tSATest.betterEq(" + e1 + ", " + e2 + ") = " + b);
      return b;
   }

   // Test methods
   @Test
   public void testSearch() {
      SimulatedAnnealingLS ls = new SimulatedAnnealingLS(this, 100, 100);
      TestSolution s = (TestSolution) ls.search(this);
      assertEquals(11, s.value);
   }
}