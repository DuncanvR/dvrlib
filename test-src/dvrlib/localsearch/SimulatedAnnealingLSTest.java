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

public class SimulatedAnnealingLSTest extends AbstractProblem<SimulatedAnnealingLSTest.TestSolution, Integer> {
   protected class TestSolution extends AbstractSolution {
      protected int value;

      public TestSolution(int value) {
         this.value = value;
      }
   }
   protected class TestChanger extends SubstitutionChanger<SimulatedAnnealingLSTest, SimulatedAnnealingLSTest.TestSolution> {
      @Override
      public Change makeChange(SingularSearchState<SimulatedAnnealingLSTest, TestSolution> state) throws CannotChangeException {
         return setSolution(state, new TestSolution(values[r.nextInt(values.length)]));
      }

      @Override
      public void reinitialise(SimulatedAnnealingLSTest p) { }
   }

   protected final int    values[] = new int[]{11, 22, 33, 44, 55, 66, 77, 88, 99, 111 };
   protected final Random r        = new Random()                                       ;

   public SimulatedAnnealingLSTest() {
      super(5);
   }

   // Problem methods
   @Override
   public TestSolution cloneSolution(TestSolution s) {
      return new TestSolution(s.value);
   }

   @Override
   public LocalSearch.SearchDirection direction() {
      return LocalSearch.SearchDirection.Minimisation;
   }

   @Override
   public Integer evaluate(TestSolution s) {
      return s.value;
   }

   @Override
   public TestSolution randomSolution() {
      return new TestSolution(100);
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
