/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * SimulatedAnnealingLSTest.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimulatedAnnealingLSTest implements Problem<SimulatedAnnealingLSTest.TestSolution>, Changer<SimulatedAnnealingLSTest.TestSolution, Pair<Integer, Integer>> {
   protected class TestSolution extends AbstractSolution {
      protected int value;
      public TestSolution(int value) {
         this.value = value;
      }
      @Override
      public void ensureMostCommon(dvrlib.localsearch.Solution s) { }
   }

   protected int values[] = new int[]{11, 22, 33, 44, 55, 66, 77, 88, 99, 111 };

   @Override
   public Pair<Integer, Integer> generateChange(TestSolution s) {
      Random r = new Random();
      return new Pair(s.value, values[r.nextInt(values.length)]);
   }

   @Override
   public void doChange(TestSolution s, Pair<Integer, Integer> c) {
      s.value = c.b;
   }

   @Override
   public void undoChange(TestSolution s, Pair<Integer, Integer> c) {
      s.value = c.a;
   }

   @Override
   public TestSolution randomSolution() {
      return new TestSolution(100);
   }

   @Override
   public double evaluate(TestSolution s) {
      return s.value;
   }

   @Override
   public void saveSolution(TestSolution s) { }

   @Override
   public TestSolution getBestSolution() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Test
   public void testSearch() {
      SimulatedAnnealingLS ls = new SimulatedAnnealingLS(this, 100, 100);
      TestSolution s = (TestSolution) ls.search(this);
      assertEquals(11, s.value);
   }
}