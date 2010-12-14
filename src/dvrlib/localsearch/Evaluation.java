/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * Evaluation.java
 */

package dvrlib.localsearch;

public interface Evaluation {
   /**
    * Returns the difference between this evaluation and the given one, i.e. <tt>this - that</tt>.
    */
   public double diff(Evaluation that);

   /**
    * Returns true if this evaluation is better than the given one, i.e. <tt>diff(that) &lt; 0</tt>.
    */
   public boolean better(Evaluation that);

   /**
    * Returns true if this evaluation is better than or equal to the given one, i.e. <tt>diff(that) &lt;= 0</tt>.
    */
   public boolean betterEq(Evaluation that);
}
