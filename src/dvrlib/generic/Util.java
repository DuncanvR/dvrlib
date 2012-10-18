/*
 * DvRLib - Generic
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
}
