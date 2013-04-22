/*
 * DvRlib - Generic
 * Duncan van Roermund, 2010-2013
 * Pair.java
 */

package dvrlib.generic;

public class Pair<A, B> {
   public A a;
   public B b;

   /**
    * Pair constructor.
    * @param a The first element in this pair.
    * @param b The second element in this pair.
    */
   public Pair(A a, B b) {
      this.a = a;
      this.b = b;
   }

   /**
    * Returns <code>true</code> if the given object is equal to this one, <code>false</code> otherwise.
    * O(a.equals() + b.equals()) if the given object is an instance of Pair, O(1) otherwise.
    */
   @Override
   public boolean equals(Object obj) {
      if(obj instanceof Pair) {
         Pair that = (Pair) obj;
         return (a == null ? that.a == null : a.equals(that.a)) &&
                (b == null ? that.b == null : b.equals(that.b));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int hash = 547;
      hash += 2 * hash + (a == null ? 0 : a.hashCode());
      hash += 2 * hash + (b == null ? 0 : b.hashCode());
      return hash;
   }

   @Override
   public String toString() {
      return this.getClass().getName() + "(" + a + ", " + b + ")";
   }
}
