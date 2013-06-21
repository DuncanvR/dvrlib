/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2012
 * Util.java
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

import java.util.Scanner;

public class Util {
   /**
    * Reads a number from stdin and returns it.
    * @param lowerBound The minimum value of the returned number.
    * @param upperBound The maximum value of the returned number.
    * @param msg        The message that will be printed before the prompt.
    * @return The number that was read from standard input.
    */
   public static int readNumber(int lowerBound, int upperBound, String msg) {
      if(lowerBound > upperBound)
         throw new IllegalArgumentException("Unable to read a number in the range [" + lowerBound + "," + upperBound + "]");

      while(true) {
         System.out.print(msg + " -- [" + lowerBound + "," + upperBound + "]: ");
         try {
            Scanner in = new Scanner(new java.io.BufferedReader(new java.io.InputStreamReader(System.in)).readLine());
            int result = in.nextInt();
            if(result >= lowerBound && result <= upperBound)
               return result;
         }
         catch(java.io.IOException | java.util.InputMismatchException _) { }
         System.out.println("Invalid number entered");
      }
   }

   /**
    * E.g. <code>Util.pluralise(0, "apple")</code> returns <code>"apples"</code>, but <code>Util.pluralise(1, "pear")</code> returns "pear".
    * @param num  The number to use.
    * @param word The word to pluralise.
    * @return The pluralised word.
    */
   public static String pluralise(int num, String word) {
      return (num == 1 ? word : word + "s");
   }

   /**
    * Returns the given word, pluralised by adding a postfix 's' if applicable, and prefixed by the given number.
    * This is a convenience function that returns <code>num + " " + pluralise(num, word)</code>.
    * @param num  The number to use and include.
    * @param word The word to pluralise.
    * @return A string containing the number and the pluralised word.
    * @see Util#pluralise(int, String)
    */
   public static String pluraliseInc(int num, String word) {
      return num + " " + pluralise(num, word);
   }
}
