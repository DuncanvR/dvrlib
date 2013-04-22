/*
 * DvRlib - Generic
 * Duncan van Roermund, 2013
 * Triple.java
 */

package dvrlib.generic;

public class Triple<A, B, C> {
   public A a;
   public B b;
   public C c;

   /**
    * Triple constructor.
    * @param a The first element in this triple.
    * @param b The second element in this triple.
    * @param c The third element in this triple.
    */
   public Triple(A a, B b, C c) {
      this.a = a;
      this.b = b;
      this.c = c;
   }

   /**
    * Returns <code>true</code> if the given object is equal to this one, <code>false</code> otherwise.
    */
   @Override
   public boolean equals(Object obj) {
      if(obj instanceof Triple) {
         Triple that = (Triple) obj;
         return (a == null ? that.a == null : a.equals(that.a)) &&
                (b == null ? that.b == null : b.equals(that.b)) &&
                (c == null ? that.c == null : c.equals(that.c));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int hash = 94;
      hash += 3 * hash + (a == null ? 0 : a.hashCode());
      hash += 3 * hash + (b == null ? 0 : b.hashCode());
      hash += 3 * hash + (c == null ? 0 : c.hashCode());
      return hash;
   }

   @Override
   public String toString() {
      return this.getClass().getName() + "(" + a + ", " + b + ", " + c + ")";
   }
}
