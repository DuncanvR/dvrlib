/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * AbstractEvaluation.java
 */

package dvrlib.localsearch;

public abstract class AbstractEvaluation implements Evaluation {
   /**
    * Returns true if this evaluation is better than the given one, i.e. <tt>diff(that) &lt; 0</tt>.
    */
   @Override
   public boolean better(Evaluation that) {
      return (diff(that) < 0);
   }

   /**
    * Returns true if this evaluation is better than or equal to the given one, i.e. <tt>diff(that) &lt;= 0</tt>.
    */
   @Override
   public boolean betterEq(Evaluation that) {
      return (diff(that) <= 0);
   }
}
