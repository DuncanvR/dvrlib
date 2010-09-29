/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
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
    * Returns true if the given object is equal to this one, false otherwise.
    * @return (obj instanceof Pair && this.a.equals(((Pair) obj).a) && this.b.equals(((Pair) obj) b))
    * O(a.equals() + b.equals()) if the given object is a Pair, O(1) otherwise.
    */
   @Override
   public boolean equals(Object obj) {
      if(obj instanceof Pair) {
         Pair that = (Pair) obj;
         return (a == null ? that.a == null : a.equals(that.a)) && (b == null ? that.b == null : b.equals(that.b));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 29 * hash + (this.a == null ? 0 : this.a.hashCode());
      hash = 29 * hash + (this.b == null ? 0 : this.b.hashCode());
      return hash;
   }

   @Override
   public String toString() {
      return "dvrlib.generic.Pair(" + a + ", " + b + ")";
   }
}
