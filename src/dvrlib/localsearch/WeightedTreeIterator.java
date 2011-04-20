/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * WeightedTreeIterator.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
import java.util.Iterator;

public class WeightedTreeIterator<E> implements Iterator<Pair<Double, E>> {
   protected final WeightedTree<E> tree;
   protected WeightedTreeNode<E> node;
   protected Iterator<E> nodeIterator = null;

   public WeightedTreeIterator(WeightedTree<E> tree) {
      this.tree = tree;
      node = tree.getMin();
      if(node != null)
         nodeIterator = node.values.iterator();
   }

   @Override
   public boolean hasNext() {
      return (nodeIterator != null);
   }

   @Override
   public Pair<Double, E> next() {
      // Sanity check
      if(!hasNext()) throw new NullPointerException("!hasNext()");
      if(node == null) throw new NullPointerException("node == null");

      Pair<Double, E> next = new Pair(node.key, nodeIterator.next());
      if(!nodeIterator.hasNext()) {
         if(node.right != null)
            node = node.right.getMin();
         else {
            while(node.parent != null && node.parent.right == node) {
               node = node.parent;
            }
            node = node.parent;
         }

         if(node != null)
            nodeIterator = node.values.iterator();
         else
            nodeIterator = null;
      }
      return next;
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException(this.getClass().getName() + ".remove() is not supported");
   }

}
