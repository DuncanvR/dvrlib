/*
 * DvRlib - Generic
 * Copyright (C) Duncan van Roermund, 2012
 * UnixColorCode.java
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

public class UnixColorCode {
   public static class Regular extends UnixColorCode {
      public static final UnixColorCode Black  = new Regular(UnixColorCode.Black),
                                        Red    = new Regular(UnixColorCode.Red),
                                        Green  = new Regular(UnixColorCode.Green),
                                        Yellow = new Regular(UnixColorCode.Yellow),
                                        Blue   = new Regular(UnixColorCode.Blue),
                                        Purple = new Regular(UnixColorCode.Purple),
                                        Cyan   = new Regular(UnixColorCode.Cyan),
                                        White  = new Regular(UnixColorCode.White);

      private Regular(char code) {
         super("0;3" + code);
      }
   }

   public static class Bold extends UnixColorCode {
      public static final UnixColorCode Black  = new Bold(UnixColorCode.Black),
                                        Red    = new Bold(UnixColorCode.Red),
                                        Green  = new Bold(UnixColorCode.Green),
                                        Yellow = new Bold(UnixColorCode.Yellow),
                                        Blue   = new Bold(UnixColorCode.Blue),
                                        Purple = new Bold(UnixColorCode.Purple),
                                        Cyan   = new Bold(UnixColorCode.Cyan),
                                        White  = new Bold(UnixColorCode.White);

      private Bold(char code) {
         super("1;3" + code);
      }
   }

   public static class Underline extends UnixColorCode {
      public static final UnixColorCode Black  = new Underline(UnixColorCode.Black),
                                        Red    = new Underline(UnixColorCode.Red),
                                        Green  = new Underline(UnixColorCode.Green),
                                        Yellow = new Underline(UnixColorCode.Yellow),
                                        Blue   = new Underline(UnixColorCode.Blue),
                                        Purple = new Underline(UnixColorCode.Purple),
                                        Cyan   = new Underline(UnixColorCode.Cyan),
                                        White  = new Underline(UnixColorCode.White);

      private Underline(char code) {
         super("4;3" + code);
      }
   }

   public static class Background extends UnixColorCode {
      public static final UnixColorCode Black  = new Background(UnixColorCode.Black),
                                        Red    = new Background(UnixColorCode.Red),
                                        Green  = new Background(UnixColorCode.Green),
                                        Yellow = new Background(UnixColorCode.Yellow),
                                        Blue   = new Background(UnixColorCode.Blue),
                                        Purple = new Background(UnixColorCode.Purple),
                                        Cyan   = new Background(UnixColorCode.Cyan),
                                        White  = new Background(UnixColorCode.White);

      private Background(char code) {
         super("4" + code);
      }
   }

   public static final UnixColorCode Reset = new UnixColorCode("0");

   private static final char Black  = '0',
                             Red    = '1',
                             Green  = '2',
                             Yellow = '3',
                             Blue   = '4',
                             Purple = '5',
                             Cyan   = '6',
                             White  = '7';

   private final String code;

   private UnixColorCode(String code) {
      this.code = code;
   }

   @Override
   public String toString() {
      return (char) 27 + ('[' + code + 'm');
   }
}
