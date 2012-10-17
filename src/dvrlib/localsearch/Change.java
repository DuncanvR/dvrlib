/*
 * DvRLib - Local search
 * Duncan van Roermund, 2012
 * Change.java
 */

package dvrlib.localsearch;

abstract class Change<P extends Problem<S, ? extends Comparable<?>>, S extends Solution> {
   protected Change<P, S> prev, next;

   /**
    * Undoes this change by executing the appropriate recursive calls.
    * This method should propagate as much of its functionality as possible to the enclosing Changer.
    */
   protected abstract void undo(SingularSearchState<P, S> ss);
}
