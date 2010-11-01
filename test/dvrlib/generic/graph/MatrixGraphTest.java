/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * AbstractGraphTest.java
 */

package dvrlib.generic.graph;

import static org.junit.Assert.*;

public class MatrixGraphTest extends AbstractGraphTest {

   @Override
   public AbstractGraph newInstance(int nodeCount) {
      return new MatrixGraph<Edge>(nodeCount);
   }

   @Override
   public void testHasEdge() {
      super.testHasEdge();

      // Test getFirstEdge(int) and getLastEdge(int)
      for(int i = 0; i < instance.nodeCount; i++) {
         Edge f = ((MatrixGraph) instance).getFirstEdge(i), l = ((MatrixGraph) instance).getLastEdge(i);
         if(i % 2 == 1) {
            assertNotNull(f);
            assertNotNull(l);
            assertEquals(0, f.b);
            assertEquals(i - 1, l.b);
         }
         else {
            assertNull(f);
            assertNull(l);
         }
      }
   }

   @Override
   protected Edge addEdge(int a, int b) {
      if(instance.addEdge(a, b))
         return ((MatrixGraph) instance).getEdge(a, b);
      else
         return null;
   }

   @Override
   public void assertEdges(AbstractEdge edges[]) {
      // Test getFirstEdge() and getLastEdge()
      assertEdge(edges[0], ((MatrixGraph) instance).getFirstEdge());
      assertEdge(edges[edges.length - 1], ((MatrixGraph) instance).getLastEdge());

      // Test previous and next pointers
      assertNull(((Edge) edges[0]).previous);
      for(int i = 1; i < edges.length - 1; i++) {
         assertEquals(edges[i - 1], ((Edge) edges[i]).previous);
         assertEquals(edges[i], ((Edge) edges[i - 1]).next);
         assertEquals(edges[i], ((Edge) edges[i + 1]).previous);
         assertEquals(edges[i + 1], ((Edge) edges[i]).next);
      }
      assertNull(((Edge) edges[edges.length - 1]).next);

      super.assertEdges(edges);
   }

   protected void assertEdge(AbstractEdge a, AbstractEdge b) {
      assertEquals((Edge) a, (Edge) b);
   }
}