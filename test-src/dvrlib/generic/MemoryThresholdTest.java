/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2013
 * MemoryThresholdTest.java
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

package dvrlib.generic;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class MemoryThresholdTest implements MemoryThreshold.Listener {
   protected static double threshold = 0.01;

   protected boolean keepGoing = true;

   @Override
   public void memoryThresholdReached(java.lang.management.MemoryUsage memoryUsage) {
      assertTrue((double) memoryUsage.getUsed() / (double) memoryUsage.getMax() > threshold);
      keepGoing = false;
   }

   @Test
   public void testMemoryThreshold() {
      MemoryThreshold mt = new MemoryThreshold(this, threshold);
      ArrayList<Double> list = new ArrayList<Double>();
      while(keepGoing) {
         list.add(new Double(Math.random()));
      }
   }
}
