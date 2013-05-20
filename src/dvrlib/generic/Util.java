/*
 * DvRlib - Generic
 * Duncan van Roermund, 2012
 * Util.java
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
