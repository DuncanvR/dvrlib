/*
 * DvRlib - Generic
 * Duncan van Roermund, 2013
 * Quadruple.java
 */

package dvrlib.generic;

public class Quadruple<A, B, C, D> {
   public A a;
   public B b;
   public C c;
   public D d;

   /**
    * Quadruple constructor.
    * @param a The first element in this quadruple.
    * @param b The second element in this quadruple.
    * @param c The third element in this quadruple.
    * @param d The fourth element in this quadruple.
    */
   public Quadruple(A a, B b, C c, D d) {
      this.a = a;
      this.b = b;
      this.c = c;
      this.d = d;
   }

   /**
    * Returns <code>true</code> if the given object is equal to this one, <code>false</code> otherwise.
    */
   @Override
   public boolean equals(Object obj) {
      if(obj instanceof Quadruple) {
         Quadruple that = (Quadruple) obj;
         return (a == null ? that.a == null : a.equals(that.a)) &&
                (b == null ? that.b == null : b.equals(that.b)) &&
                (c == null ? that.c == null : c.equals(that.c)) &&
                (d == null ? that.d == null : d.equals(that.c));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int hash = 951;
      hash += 4 * hash + (a == null ? 0 : a.hashCode());
      hash += 4 * hash + (b == null ? 0 : b.hashCode());
      hash += 4 * hash + (c == null ? 0 : c.hashCode());
      hash += 4 * hash + (d == null ? 0 : d.hashCode());
      return hash;
   }

   @Override
   public String toString() {
      return this.getClass().getName() + "(" + a + ", " + b + ", " + c + ", " + d + ")";
   }
}
