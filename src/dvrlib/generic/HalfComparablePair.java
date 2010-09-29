/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * HalfComparablePair.java
 */

package dvrlib.generic;

public class HalfComparablePair<A extends Comparable, B> extends Pair<A, B> implements Comparable<HalfComparablePair<A, B>> {

   /**
    * HalfComparablePair constructor.
    * @see Pair#Pair(java.lang.Object, java.lang.Object)
    */
   public HalfComparablePair(A a, B b) {
      super(a, b);
   }

   /**
    * Returns true if the given object is equal to this one, false otherwise.
    * @return (obj instanceof Pair && this.a.equals(((Pair) obj).a))
    * O(a.equals()) if the given object is a Pair, O(1) otherwise.
    */
   @Override
   public boolean equals(Object obj) {
      if(obj instanceof HalfComparablePair) {
         HalfComparablePair that = (HalfComparablePair) obj;
         return (a == null ? that.a == null : a.equals(that.a));
      }
      return false;
   }

   @Override
   public int hashCode() {
      int hash = 9;
      hash = 69 * hash + (this.a == null ? 0 : this.a.hashCode());
      return hash;
   }

   public int compareTo(HalfComparablePair<A, B> that) {
      return a.compareTo(that.a);
   }
}
