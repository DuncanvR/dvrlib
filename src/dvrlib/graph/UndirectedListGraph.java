/*
 * DvRlib - Graph
 * Copyright (C) Duncan van Roermund, 2010-2013
 * UndirectedListGraph.java
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dvrlib.graph;

public class UndirectedListGraph<Id extends Comparable<Id>, NodeData, EdgeData> extends ListGraph<Id, NodeData, EdgeData> {

   /**
    * Adds an edge between nodes <code>a</code> and <code>b</code>.
    * @param ed The data that will be associated with the edge.
    * @return <code>true</code> if the edge was successfully added, <code>false</code> otherwise.
    * @see ListGraph#addEdge(ListGraphNode, ListGraphNode, java.lang.Object)
    * O(1) if the edge already existed, O(e) otherwise.
    */
   @Override
   public boolean addEdge(ListGraphNode<Id, NodeData, EdgeData> a, ListGraphNode<Id, NodeData, EdgeData> b, EdgeData ed) {
      if(a == b)
         return false;
      if(super.addEdge(a, b, ed)) {
         if(super.addEdge(b, a, ed)) {
            edgeCount--;
            return true;
         }
         else
            throw new IllegalStateException("Unable to add both edges " + a + "," + b + " and " + b + "," + a);
      }
      return false;
   }

   /**
    * Sets the data associated with the edge between nodes <code>a</code> and <code>b</code>, and returns the old data.
    * @return The old data associated with the indicated edge.
    * @see ListGraph#replaceEdge(ListGraphNode, ListGraphNode, java.lang.Object)
    */
   @Override
   public EdgeData replaceEdge(ListGraphNode<Id, NodeData, EdgeData> a, ListGraphNode<Id, NodeData, EdgeData> b, EdgeData ed) {
      EdgeData edA = super.replaceEdge(a, b, ed),
               edB = super.replaceEdge(b, a, ed);
      assert(edA == edB);
      return edA;
   }

   /**
    * Removes the edge between nodes a and b.
    * @return The data that was associated with the edge.
    * @throws IllegalArgumentException If the given edge does not exist in this graph.
    * @see ListGraph#removeEdge(ListGraphNode, ListGraphNode)
    * O(1).
    */
   @Override
   public EdgeData removeEdge(ListGraphNode<Id, NodeData, EdgeData> a, ListGraphNode<Id, NodeData, EdgeData> b) {
      EdgeData edA = super.removeEdge(a, b);
      edgeCount++;
      EdgeData edB = super.removeEdge(b, a);
      assert(edA == edB);
      return edA;
   }
}
