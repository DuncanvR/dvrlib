/*
 * DvRLib - Generic
 * Duncan van Roermund, 2012
 * Tuple.java
 */

package dvrlib.generic;

public class Tuple<A, B> {
   public A a;
   public B b;

   /**
    * Tuple constructor.
    * @param a The first element in this tuple.
    * @param b The second element in this tuple.
    */
   public Tuple(A a, B b) {
      this.a = a;
      this.b = b;
   }

   /**
    * Returns true if the given object is equal to this one, false otherwise.
    * @return <code>(obj instanceof Tuple && this.a.equals(((Tuple) obj).a) && this.b.equals(((Tuple) obj) b))</code>
    * O(a.equals() + b.equals()) if the given object is a Tuple, O(1) otherwise.
    */
   @Override
   public boolean equals(Object obj) {
      if(obj instanceof Tuple) {
         Tuple that = (Tuple) obj;
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
      return this.getClass().getName() + "(" + a + ", " + b + ")";
   }
}
