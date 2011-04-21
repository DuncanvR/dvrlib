/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010-2011
 * WeightedTree.java
 */

package dvrlib.localsearch;

import dvrlib.generic.IterableOnce;
import dvrlib.generic.Pair;
import java.util.Collection;
import java.util.Iterator;

public class WeightedTree<E> implements Iterable<Pair<Double, E>> {
   protected WeightedTreeNode<E> root = null;

   /**
    * Returns the number of elements in this tree.
    * O(1).
    */
   public int size() {
      return (root == null ? 0 : root.size);
   }

   /**
    * Returns true if this tree holds no elements, false otherwise.
    * O(1).
    */
   public boolean isEmpty() {
      return root == null;
   }

   /**
    * Adds a node with the given key and value to this tree.
    * @return true, indicating the operation succeeded.
    * @throws IllegalArgumentException When the given key is <tt>null</tt>.
    * @see WeightedTree#rebalanceUp(dvrlib.localsearch.WeightedTreeNode)
    * O(height)
    */
   public boolean add(Double key, E value) {
      if(key == null)
         throw new NullPointerException("No null keys permitted in this Collection");
      if(root == null)
         root = new WeightedTreeNode<E>(key, value);
      else
         rebalanceUp(root.add(key, value));
      return true;
   }

   /**
    * Returns a (key, element) from the tree, reached with the given normalized index.
    * In a tree with these four items: {(1, A), (2, B), (1.3, C), (1.7, D)}, the sum of the indices is 6.
    * A call to getWeighted(0.5) will then return the item with the summed index (0.5 * 6 = 3).
    * Moving through the tree from the smallest index, (A: 1 &lt; 3), (C: 1.3 &lt; (3 - 1)), (D: 1.7 &gt;= (3 - 1 - 1.3)).
    * This means the item with the largest index has the most chance of getting selected.
    * @param normIndex A double between 0 and 1.
    */
   protected Pair<Double, E> getWeighted(double normIndex) {
      if(normIndex < 0 || normIndex > 1)
         throw new IllegalArgumentException("The argument of WeightedTree.getWeighted(double) should be between 0 and 1");
      return (root == null ? null : root.getWeighted(normIndex));
   }

   /**
    * Returns the node with the smallest key in this tree.
    * O(height).
    */
   protected WeightedTreeNode<E> getMin() {
      return (root == null ? null : root.getMin());
   }

   /**
    * Returns the node with the largest key in this tree.
    * O(height).
    */
   protected WeightedTreeNode<E> getMax() {
      return (root == null ? null : root.getMax());
   }

   /**
    * Returns an element from the node with the smallest key along with that key.
    * @see WeightedTree#getMin()
    * @see WeightedTreeNode#peek()
    */
   public Pair<Double, E> peekMin() {
      WeightedTreeNode node = getMin();
      return new Pair(node.key, node.peek());
   }

   /**
    * Returns an element from the node with the largest key along with that key.
    * @see WeightedTree#getMax()
    * @see WeightedTreeNode#peek()
    */
   public Pair<Double, E> peekMax() {
      WeightedTreeNode node = getMax();
      return new Pair(node.key, node.peek());
   }

   /**
    * Removes an element from the given node and returns its key along with its data.
    * If the node contains only one element, it is removed from the tree.
    * @see WeightedTree#remove(dvrlib.localsearch.WeightedTreeNode)
    * O(node.depth).
    */
   protected Pair<Double, E> pop(WeightedTreeNode<E> node) {
      Pair<Double, E> item = new Pair(node.key, node.pop());
      if(node.values.isEmpty())
         remove(node);
      else
         updateSizeUp(node);
      return item;
   }

   /**
    * Removes an element from the node with the smallest key and returns its key and its data.
    * @see WeightedTree#getMin()
    * @see WeightedTree#pop(dvrlib.localsearch.WeightedTreeNode)
    */
   public Pair<Double, E> popMin() {
      return pop(getMin());
   }

   /**
    * Removes an element from the node with the largest key and returns its key along with its data.
    * @see WeightedTree#getMax()
    * @see WeightedTree#pop(dvrlib.localsearch.WeightedTreeNode)
    */
   public Pair<Double, E> popMax() {
      return pop(getMax());
   }

   /**
    * Removes the given node from this tree.
    * O(node.height + node.depth).
    */
   protected void remove(WeightedTreeNode<E> node) {
      if(node.left == null || node.right == null)
         removeExternal(node);
      else {
         throw new IllegalArgumentException("WeightedTree.remove(WeightedTreeNode) is only implemented for external nodes");
         /*WeightedTreeNode<E> n;
         int dH = node.getLeftHeight() - node.getRightHeight(),
             dS = node.getLeftSize() - node.getRightSize();
         if(dH > 0 || (dH == 0 && dS > 0)) {
            if(root == node) {
               root = node.left;
               root.parent = null;
            }
            else
               node.parent.replace(node, node.left);

            n = getMax(node.left);
            n.right = node.right;
            rebalanceUp(n);
         }
         else {
            if(root == node) {
               root = node.right;
               root.parent = null;
            }
            else
               node.parent.replace(node, node.right);

            n = getMax(node.right);
            n.left = node.left;
            rebalanceUp(n);
         }*/
      }
   }

   /**
    * Removes the given external node from this tree.
    * Do not call this method directly but use <tt>remove(WeightedTreeNode)</tt>.
    * @param node The node that is to be removed. This has to be an external node, i.e. it may not have both a left and a right subtree.
    * @see WeightedTree#remove(dvrlib.localsearch.WeightedTreeNode)
    * O(node.depth).
    */
   protected void removeExternal(WeightedTreeNode<E> node) {
      if(node == null)
         throw new IllegalArgumentException("Cannot remove null node");
      else if(node == root) {
         root = (node.left != null ? node.left : node.right);
         if(root != null)
            root.parent = null;
      }
      else {
         node.parent.replaceChild(node, (node.left != null ? node.left : node.right));
         rebalanceUp(node.parent);
         updateSizeUp(node.parent);
         node.parent = null;
      }
   }

   /**
    * Replaces the element with the smallest key with the given element if the given key is larger.
    * @return The replaced elements data, or <tt>null</tt> if the given key is smaller.
    */
   public Pair<Double, E> replaceMin(Double key, E value) {
      WeightedTreeNode<E> min = getMin();
      if(key > min.key) {
         add(key, value);
         return pop(min);
      }
      else
         return null;
   }

   /**
    * Updates the size, weight and height of the given node and all its ancestors.
    * @see WeightedTreeNode#updateSize()
    * O(node.depth).
    */
   protected void updateSizeUp(WeightedTreeNode node) {
      for(; node.parent != null; node = node.parent) {
         node.updateSize();
      }
      node.updateSize();
   }

   /**
    * Ensures the given node and all its ancestors are balanced.
    * @see WeightedTree#rebalance(dvrlib.localsearch.WeightedTreeNode)
    * O(node.depth).
    */
   protected void rebalanceUp(WeightedTreeNode node) {
      for(; node != null; node = node.parent) {
         node = rebalance(node);
      }
   }

   /**
    * Rebalances the given node by performing rotations if necessary.
    * @return The node at the original position of the given node after the rebalancing.
    * O(1).
    */
   protected WeightedTreeNode rebalance(WeightedTreeNode node) {
      if(node != null) {
         int balance = node.getLeftHeight() - node.getRightHeight();
         if(balance > 1) {
            balance = node.left.getLeftHeight() - node.left.getRightHeight();
            if(balance > 0)
               node = singleRotation(node, node.left, node.left.left, false);
            else
               node = doubleRotation(node, node.left, node.left.right, false);
         }
         else if(balance < -1) {
            balance = node.right.getLeftHeight() - node.right.getRightHeight();
            if(balance > 0)
               node = doubleRotation(node, node.right, node.right.left, true);
            else
               node = singleRotation(node, node.right, node.right.right, true);
         }
         else
            node.updateSize();
      }
      return node;
   }

   /**
    * Performs a single rotation with the three given nodes:
    * z                             z
    *  \              y            /            y
    *   y   becomes  / \   and    y   becomes  / \
    *    \          z   x        /            x   z
    *     x                     x
    * @param ZLeft   Indicates whether the z node ends up as the left child of the node
    * @return        The new top node: y
    * O(1).
    */
   protected WeightedTreeNode singleRotation(WeightedTreeNode z, WeightedTreeNode y, WeightedTreeNode x, boolean ZLeft) {
      /*if(z == null || y == null)
         throw new IllegalArgumentException("Cannot rotate on null nodes");
      if(z != null && z.parent == null && z != root)
         throw new IllegalStateException("Node without parent other than the root encountered");*/

      // Maintain root and parents
      if(z == root) {
         root = y;
         root.parent = null;
      }
      else
         z.parent.replaceChild(z, y);
      z.parent = y;

      // Set children
      if(ZLeft) {
         z.replaceChild(z.right, y.left);
         y.left  = z;
      }
      else {
         z.replaceChild(z.left, y.right);
         y.right = z;
      }

      // Maintain metadata
      z.updateSize();
      y.updateSize();

      return y;
   }

   /**
    * Performs a double rotation with the three given nodes:
    * z                             z
    *  \                           /
    *   \             x           /             x
    *    \  becomes  / \   and   /    becomes  / \
    *     y         z   y       y             y   z
    *    /                       \
    *   x                         x
    *
    * @param ZLeft   Indicates whether the z node ends up as the left child of the top node
    * @return        The new top node: x
    * O(1).
    */
   protected WeightedTreeNode doubleRotation(WeightedTreeNode z, WeightedTreeNode y, WeightedTreeNode x, boolean ZLeft) {
      /*if(z == null || y == null)
         throw new IllegalArgumentException("Cannot rotate on null nodes");
      if(z != null && z.parent == null && z != root)
         throw new IllegalStateException("Node without parent other than the root encountered");*/

      // Maintain root and parents
      if(z == root) {
         root = x;
         root.parent = null;
      }
      else
         z.parent.replaceChild(z, x);
      y.parent = x;
      z.parent = x;

      // Set children
      if(ZLeft) {
         z.replaceChild(z.right, x.left);
         y.replaceChild(y.left, x.right);
         x.left  = z;
         x.right = y;
      }
      else {
         z.replaceChild(z.left, x.right);
         y.replaceChild(y.right, x.left);
         x.left  = y;
         x.right = z;
      }

      // Maintain metadata
      z.updateSize();
      y.updateSize();
      x.updateSize();

      return x;
   }

   @Override
   public Iterator<Pair<Double, E>> iterator() {
      return new WeightedTreeIterator<E>(this);
   }

   /**
    * @see Collection#toArray()
    */
   public Object[] toArray() {
      Object array[] = new Object[size()];
      int i = 0;
      for(Pair<Double, E> p : new IterableOnce<Pair<Double, E>>(iterator())) {
         array[i++] = p.b;
      }
      return array;
   }

   /**
    * @see Collection#toArray(T[])
    */
   public E[] toArray(E[] array) {
      if(size() > array.length)
         throw new IllegalArgumentException("Unable to fit all items in the given array, provide a larger one");
      int i = 0;
      for(Pair<Double, E> p : new IterableOnce<Pair<Double, E>>(iterator())) {
         array[i++] = p.b;
      }
      if(i < array.length)
         array[i] = null;
      return array;
   }

   /**
    * Prints this tree to stdout.
    * O(n).
    */
   public void print() {
      System.out.println("dvrlib.localsearch.WeightedTree(" + size() + ")");
      if(root != null)
         root.print("");
   }
}
