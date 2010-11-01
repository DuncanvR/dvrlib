/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * ListGraphTest.java
 */

package dvrlib.generic.graph;

public class ListGraphTest extends AbstractGraphTest {

   @Override
   public AbstractGraph newInstance(int nodeCount) {
      return new ListGraph(nodeCount);
   }

   @Override
   protected AbstractEdge addEdge(int a, int b) {
      if(instance.addEdge(a, b))
         return new AbstractEdge(a, b);
      else
         return null;
   }
}