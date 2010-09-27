/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * UndirectedGraphTest.java
 */

package dvrlib.generic;

import java.util.Collection;
import static org.junit.Assert.*;

public class UndirectedGraphTest extends GraphTest {
   @Override
   public Graph newInstance(int nodeCount) {
      return new UndirectedGraph(nodeCount);
   }

   @Override
   public void assertNeighbours(Edge edges[]) {
      // Test getNeighbours(int)
      for(int i = 0; i < instance.getNodeCount(); i++) {
         Collection<AbstractNode> neighbours = instance.getNeighbours(i);
         for(Edge e : edges) {
            if(i == e.a)
               assertTrue(neighbours.remove(instance.getNode(e.b)));
            else if(i == e.b)
               assertTrue(neighbours.remove(instance.getNode(e.a)));
         }
         assertTrue(neighbours.isEmpty());
      }
   }

   @Override
   public void testDegrees() {
      instance = newInstance(6);
      assertDegrees(0, new int[]{0,0,0,0,0,0});
      instance.addEdge(0, 1);
      assertDegrees(1, new int[]{1,1,0,0,0,0});
      instance.addEdge(2, 1);
      assertDegrees(2, new int[]{1,2,1,0,0,0});
      instance.addEdge(2, 3);
      assertDegrees(2, new int[]{1,2,2,1,0,0});
      instance.addEdge(2, 5);
      assertDegrees(3, new int[]{1,2,3,1,0,1});
      instance.addEdge(4, 5);
      assertDegrees(3, new int[]{1,2,3,1,1,2});

      instance = newInstance(5);
      assertDegrees(0, new int[]{0,0,0,0,0});
      instance.addEdge(0, 1);
      assertDegrees(1, new int[]{1,1,0,0,0});
      instance.addEdge(2, 3);
      assertDegrees(1, new int[]{1,1,1,1,0});
      instance.addEdge(0, 4);
      assertDegrees(2, new int[]{2,1,1,1,1});
      instance.addEdge(2, 4);
      assertDegrees(2, new int[]{2,1,2,1,2});
      instance.addEdge(0, 3);
      assertDegrees(3, new int[]{3,1,2,2,2});
      instance.removeEdge(2, 4);
      assertDegrees(3, new int[]{3,1,1,2,1});
      instance.removeEdge(2, 3);
      assertDegrees(3, new int[]{3,1,0,1,1});
      instance.removeEdge(0, 1);
      assertDegrees(2, new int[]{2,0,0,1,1});
      instance.removeEdge(0, 4);
      assertDegrees(1, new int[]{1,0,0,1,0});
      instance.removeEdge(0, 3);
      assertDegrees(0, new int[]{0,0,0,0,0});
   }
}
