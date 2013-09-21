/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2013
 * MemoryThreshold.java
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

import java.lang.management.MemoryPoolMXBean;
import java.util.ArrayList;

public class MemoryThreshold {
   public static interface Listener {
      public void memoryThresholdReached(java.lang.management.MemoryUsage memoryUsage);
   }

   protected static final MemoryPoolMXBean heapPool;
   static {
      MemoryPoolMXBean pool = null;
      for(MemoryPoolMXBean mp : java.lang.management.ManagementFactory.getMemoryPoolMXBeans()) {
         if(mp.getType() == java.lang.management.MemoryType.HEAP && mp.isUsageThresholdSupported()) {
            pool = mp;
            break;
         }
      }
      if(pool == null)
         throw new IllegalStateException("Heap pool not found");
      heapPool = pool;
   }

   protected final ArrayList<Listener> listeners = new ArrayList<Listener>();

   public MemoryThreshold() {
      ((javax.management.NotificationEmitter) java.lang.management.ManagementFactory.getMemoryMXBean()).addNotificationListener(
         new javax.management.NotificationListener() {
            public void handleNotification(javax.management.Notification n, Object hb) {
               if(n.getType().equals(java.lang.management.MemoryNotificationInfo.MEMORY_THRESHOLD_EXCEEDED)) {
                  for(Listener listener : listeners) {
                     listener.memoryThresholdReached(heapPool.getUsage());
                  }
               }
            }
         }, null, null);
   }
   public MemoryThreshold(Listener listener, double percentage) {
      this();
      addListener(listener);
      setThreshold(percentage);
   }

   public boolean addListener(Listener listener) {
      return listeners.add(listener);
   }

   public boolean removeListener(Listener listener) {
      return listeners.remove(listener);
   }

   public void setThreshold(int bytes) {
      heapPool.setCollectionUsageThreshold(bytes);
      heapPool.setUsageThreshold(bytes);
   }
   public void setThreshold(double percentage) {
      if(percentage <= 0d || percentage > 1d)
         throw new IllegalArgumentException("Threshold percentage should be between 0 and 1");
      setThreshold((int) Math.floor(percentage * heapPool.getUsage().getMax()));
   }
}
