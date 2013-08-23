/*
 * DvRlib - Local search
 * Copyright (C) Duncan van Roermund, 2013
 * PopulationTest.java
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dvrlib.localsearch;

import java.util.Random;

import org.junit.Test;
import static org.junit.Assert.*;

public class PopulationTest {
   public static final int PopulationSizeLimit = 20; // Must be even
   public static final Random Rnd = new Random();

   public class Sol implements Solution {
      protected int n, hc;

      public Sol(int n) {
         this.n = n;
         hc = Rnd.nextInt();
      }
      public long iterationCount() {
         return 0l;
      }
      public void setIterationCount(long n) {
      }
      public int hashCode() {
         return hc;
      }
   }
   public class Prob extends AbstractGeneticProblem<Sol, Integer> {
      public Prob() {
         super(PopulationSizeLimit);
      }
      public Sol cloneSolution(Sol sol) {
         return new Sol(sol.n);
      }
      public LocalSearch.SearchDirection direction() {
         return LocalSearch.SearchDirection.Maximisation;
      }
      public Integer evaluate(Sol sol) {
         return sol.n;
      }
      public Integer evaluationBound(Sol sol) {
         return sol.n;
      }
      public Sol randomSolution() {
         return new Sol(Rnd.nextInt());
      }
      public double weight(Integer i) {
         return i.doubleValue();
      }
   }

   @Test
   public void testPopulation() {
      Prob problem = new Prob();
      testPopulation(problem, new TreePopulation<Sol, Integer>(problem, PopulationSizeLimit));
      testPopulation(problem, new WeightedTreePopulation<Sol>(problem, PopulationSizeLimit));
   }
   protected void testPopulation(Prob problem, Population<Sol> pop) {
      Sol sols[] = new Sol[PopulationSizeLimit + 10];
      for(int i = 0; i < sols.length; i++) {
         sols[i] = new Sol(i);
         pop.add(sols[i]);
      }

      assertTrue(pop.contains(pop.peekBest()));
      assertEquals(sols.length - 1, pop.peekBest().n);
      assertTrue(pop.contains(pop.peekWorst()));
      assertEquals(sols.length - PopulationSizeLimit, pop.peekWorst().n);

      for(int i = sols.length - 1; i > sols.length - 1 - PopulationSizeLimit; i--) {
         assertTrue(pop.contains(sols[i]));
         assertFalse(pop.contains(new Sol(i)));
      }
      assertFalse(pop.contains(sols[sols.length - 1 - PopulationSizeLimit]));

      Sol sols2[] = new Sol[sols.length];
      for(int i = 0; i < sols2.length; i++) {
         sols2[i] = new Sol(i);
         pop.add(sols2[i]);
      }

      assertEquals(sols.length - 1, pop.peekBest().n);
      assertEquals(sols.length - PopulationSizeLimit / 2, pop.peekWorst().n);

      for(int i = sols.length - 1; i > sols.length - 1 - PopulationSizeLimit / 2; i--) {
         assertTrue(pop.contains(sols[i]));
         assertTrue(pop.contains(sols2[i]));
         assertFalse(pop.contains(new Sol(i)));
      }
      assertFalse(pop.contains(sols[sols.length - 1 - PopulationSizeLimit / 2]));
      assertFalse(pop.contains(sols2[sols.length - 1 - PopulationSizeLimit / 2]));

      java.util.Iterator<Sol> it = pop.iterator();
      for(int i = sols.length - 1; i > sols.length - 1 - PopulationSizeLimit / 2; i--) {
         Sol n1 = it.next(), n2 = it.next();
         assertNotEquals(n1, n2);
         assertTrue(n1 == sols[i] || n1 == sols2[i]);
         assertTrue(n2 == sols[i] || n2 == sols2[i]);
      }
   }
}
