/*
 * DvRlib - Local search
 * Duncan van Roermund, 2010-2011
 * SimulatedAnnealingLSTest.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

public class SimulatedAnnealingLSTest extends    AbstractProblem<SimulatedAnnealingLSTest.TestSolution, Integer>
                                      implements NumericProblem<SimulatedAnnealingLSTest.TestSolution, Integer> {
   protected class TestSolution extends AbstractSolution {
      protected int value;

      public TestSolution(int value) {
         this.value = value;
      }
   }
   protected class TestChanger extends SubstitutionChanger<SimulatedAnnealingLSTest, SimulatedAnnealingLSTest.TestSolution> {
      @Override
      public Change makeChange(SingularSearchState<SimulatedAnnealingLSTest, TestSolution> state) throws CannotChangeException {
         Change c = new Change(copySolution(state.solution()));
         state.solution().value = values[r.nextInt(values.length)];
         return c;
      }

      @Override
      public void reinitialize(SimulatedAnnealingLSTest p) { }
   }

   protected final int    values[] = new int[]{11, 22, 33, 44, 55, 66, 77, 88, 99, 111 };
   protected final Random r        = new Random()                                       ;

   public SimulatedAnnealingLSTest() {
      super(5);
   }

   // Problem methods
   @Override
   public TestSolution copySolution(TestSolution s) {
      return new TestSolution(s.value);
   }

   @Override
   public LocalSearch.SearchDirection direction() {
      return LocalSearch.SearchDirection.Minimization;
   }

   @Override
   public Integer evaluate(TestSolution s) {
      return s.value;
   }

   @Override
   public TestSolution randomSolution() {
      return new TestSolution(100);
   }

   @Override
   public double diffEval(Integer e1, Integer e2) {
      return (e1 - e2);
   }

   // Test methods
   @SuppressWarnings("unchecked")
   @Test
   public void testSearch() {
      SimulatedAnnealingLS ls = new SimulatedAnnealingLS(new TestChanger(), 100, 100);
      TestSolution s = (TestSolution) ls.search(this, 15);
      assertEquals(11, s.value);
   }
}
