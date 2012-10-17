/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * ChangeList.java
 */

package dvrlib.localsearch;

public class ChangeList<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> {
   protected Change<P, S> first, last;
   protected int size = 0;

   public void add(Change<P, S> change) {
      if(size++ > 0) {
         change.prev = last;
         last.next   = change;
         last        = change;
      }
      else
         first = last = change;
   }

   public void clear() {
      first = last = null;
      size = 0;
   }

   public int size() {
      return size;
   }

   public void undoAll(SingularSearchState<P, S> ss) {
      if(size > 0) {
         first.undo(ss);
         first = null;
         last  = null;
         size  = 0;
      }
   }

   public void undoLast(SingularSearchState<P, S> ss) {
      if(size > 0) {
         last.undo(ss);
         last = last.prev;
         size--;
      }
   }
}
