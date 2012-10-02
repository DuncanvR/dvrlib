/*
 * DvRLib - Generic
 * Duncan van Roermund, 2012
 * Util.java
 */

package dvrlib.generic;

import java.util.Scanner;

public class Util {
   public static class UnixColorCode {
      protected static final String escape = (char) 27 + "[";
      public    static final String Reset  = escape + "0m";

      public static class Regular {
         public static final String Black  = escape + "0;30m",
                                    Red    = escape + "0;31m",
                                    Green  = escape + "0;32m",
                                    Yellow = escape + "0;33m",
                                    Blue   = escape + "0;34m",
                                    Purple = escape + "0;35m",
                                    Cyan   = escape + "0;36m",
                                    White  = escape + "0;37m";
      }
      public static class Bold {
         public static final String Black  = escape + "1;30m",
                                    Red    = escape + "1;31m",
                                    Green  = escape + "1;32m",
                                    Yellow = escape + "1;33m",
                                    Blue   = escape + "1;34m",
                                    Purple = escape + "1;35m",
                                    Cyan   = escape + "1;36m",
                                    White  = escape + "1;37m";
      }
      public static class Underline {
         public static final String Black  = escape + "4;30m",
                                    Red    = escape + "4;31m",
                                    Green  = escape + "4;32m",
                                    Yellow = escape + "4;33m",
                                    Blue   = escape + "4;34m",
                                    Purple = escape + "4;35m",
                                    Cyan   = escape + "4;36m",
                                    White  = escape + "4;37m";
      }
      public static class Background {
         public static final String Black  = escape + "40m",
                                    Red    = escape + "41m",
                                    Green  = escape + "42m",
                                    Yellow = escape + "43m",
                                    Blue   = escape + "44m",
                                    Purple = escape + "45m",
                                    Cyan   = escape + "46m",
                                    White  = escape + "47m";
      }
   }

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
