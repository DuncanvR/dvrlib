/*
 * DvRlib - Algorithm
 * Copyright (C) Duncan van Roermund, 2013
 * SubSets.java
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

package dvrlib.algorithm;

import java.util.Arrays;
import java.util.LinkedList;

public final class SubSets {
   /**
    * Returns a list of all permutations of the given array.
    */
   public static <T> LinkedList<T[]> permutations(T arr[]) {
      LinkedList<T[]> lst = new LinkedList<T[]>();
      permutations(arr, 0, lst);
      return lst;
   }

   /**
    * @see SubSets#permutations(Object[])
    */
   protected static <T> void permutations(T arr[], int i, LinkedList<T[]> lst) {
      if(i < arr.length - 1) {
         permutations(arr, i + 1, lst);
         for(int j = i + 1; j < arr.length; j++) {
            T arr2[] = Arrays.copyOf(arr, arr.length);
            swap(arr2, i, j);
            permutations(arr2, i + 1, lst);
         }
      }
      else
         lst.add(arr);
   }

   /**
    * Returns a list of all subsets of the given array.
    */
   public static <T> LinkedList<T[]> subSets(T arr[]) {
      LinkedList<T[]> lst = new LinkedList<T[]>();
      subSets(arr, 0, lst);
      return lst;
   }

   /**
    * @see SubSets#subSets(Object[])
    */
   protected static <T> void subSets(T arr[], int i, LinkedList<T[]> lst) {
      if(i < arr.length) {
         subSets(arr, i + 1, lst);
         T arr2[] = Arrays.copyOf(arr, arr.length - 1);
         for(int j = arr2.length; j > i; j--) {
            arr2[j - 1] = arr[j];
         }
         subSets(arr2, i, lst);
      }
      else if(arr.length > 0)
         lst.add(arr);
   }

   /**
    * Swaps the items in the given array at indices <code>i</code> and <code>j</code>.
    */
   public static void swap(Object arr[], int i, int j) {
      Object tmp = arr[i];
      arr[i] = arr[j];
      arr[j] = tmp;
   }
}
