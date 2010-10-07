/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * EdgeCreator.java
 */

package dvrlib.generic;

public interface EdgeCreator<E extends Edge> {
   /**
    * Creates and returns a new edge between the nodes with the given indices.
    */
   public E newEdge(int a, int b);
}
