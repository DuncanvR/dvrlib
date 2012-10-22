/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2012
 * CannotChangeException.java
 */

package dvrlib.localsearch;

public class CannotChangeException extends Exception {
   protected final Changer changer;

   public CannotChangeException(Changer changer, String message) {
      super(message);
      this.changer = changer;
   }

   public CannotChangeException(Changer changer, Throwable cause) {
      super(cause);
      this.changer = changer;
   }

   public Changer getChanger() {
      return changer;
   }
}
