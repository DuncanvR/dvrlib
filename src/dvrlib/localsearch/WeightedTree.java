/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * WeightedTree.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;

public class WeightedTree<E> {
   protected WeightedTreeNode<E> root = null;

   public int size() {
      return (root == null ? 0 : root.size);
   }

   public boolean isEmpty() {
      return root == null;
   }

   public boolean add(Double key, E value) {
      if(key == null)
         throw new NullPointerException("No null keys permitted in this Collection");

      WeightedTreeNode<E> node = new WeightedTreeNode(key, value);
      if(root == null)
         root = node;
      else {
         add(root, node);
         rebalanceUp(node);
      }
      return true;
   }

   protected void add(WeightedTreeNode<E> parent, WeightedTreeNode<E> node) {
      if(node.a > parent.a) {
         if(parent.right == null)
            parent.setRight(node);
         else
            add(parent.right, node);
      }
      else {
         if(parent.left == null)
            parent.setLeft(node);
         else
            add(parent.left, node);
      }
   }

   /*protected WeightedTreeNode<E> find(E e) {
      for(WeightedTreeNode<E> node = root; node != null;) {
         int compare = e.compareTo(node.key);
         if(compare > 0)
            node = node.right;
         else if(compare < 0)
            node = node.left;
         else
            return node;
      }
      return null;
   }*/

   /**
    * Returns the element from the tree, reached with the given normalized index.
    * In a tree with these four items: {(1, A), (2, B), (1.3, C), (1.7, D)}, the sum of the indices is 6.
    * A call to getWeighted(0.5) will then return the item with the summed index (0.5 * 6 = 3).
    * Moving through the tree from the smallest index, (A: 1 < 3), (C: 1.3 < (3 - 1)), (D: 1.7 >= (3 - 1 - 1.3).
    * This means the item with the largest index has the most chance to get selected.
    * @param normIndex A number between 0 and 1.
    * @return
    */
   protected E getWeighted(double normIndex) {
      if(normIndex < 0 || normIndex > 1)
         throw new IllegalArgumentException("The argument of WeightedTree.getWeighted(double) should be between 0 and 1");

      WeightedTreeNode<E> node = root;
      while(node != null) {
         double weight = node.getWeight();
         if(node.getLeftWeight() / weight > normIndex)
            node = node.left;
         else if((weight - node.getRightWeight()) / weight < normIndex)
            node = node.right;
         else
            return node.b;
      }
      return node.b;
   }

   protected WeightedTreeNode<E> getMin() {
      if(root == null)
         return null;

      WeightedTreeNode<E> node = root;
      while(node.left != null) {
         node = node.left;
      }
      return node;
   }

   protected WeightedTreeNode<E> getMax() {
      if(root == null)
         return null;

      WeightedTreeNode<E> node = root;
      while(node.right != null) {
         node = node.right;
      }
      return node;
   }

   public Pair<Double, E> peekMin() {
      return getMin();
   }

   public Pair<Double, E> peekMax() {
      return getMax();
   }

   public Pair<Double, E> popMin() {
      return removeExternal(getMin());
   }

   public Pair<Double, E> popMax() {
      return removeExternal(getMax());
   }

   protected WeightedTreeNode<E> removeExternal(WeightedTreeNode<E> node) {
      if(node == null)
         return null;
      if(node == root)
         root = (node.left != null ? node.left : node.right);
      else {
         node.parent.replace(node, (node.left != null ? node.left : node.right));
         rebalanceUp(node.parent);
      }
      node.parent = null;

      return node;
   }

   /**
    * Rebalances the given node and all its ancestors.
    * O(depth of the given node).
    */
   protected void rebalanceUp(WeightedTreeNode node) {
      for(; node != null; node = node.parent) {
         node = rebalance(node);
      }
   }

   /**
    * Rebalances the given node by performing rotations if necesary.
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
            node.setSize();
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
    * @param ZLeft   Indicates wheter the z node ends up as the left child of the node
    * @return        The new top node: y
    */
   protected WeightedTreeNode singleRotation(WeightedTreeNode z, WeightedTreeNode y, WeightedTreeNode x, boolean ZLeft) {
      // Maintain root
      if(z == root)
         root = y;

      // Set parents
      if(z.parent != null)
         z.parent.replace(z, y);
      y.parent = z.parent;
      z.parent = y;

      // Set children
      if(ZLeft) {
         z.right = y.left;
         y.left  = z;
      }
      else {
         z.left  = y.right;
         y.right = z;
      }

      // Maintain metadata
      z.setSize();
      y.setSize();

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
    * @param ZLeft   Indicates wheter the z node ends up as the left child of the top node
    * @return        The new top node: x
    */
   protected WeightedTreeNode doubleRotation(WeightedTreeNode z, WeightedTreeNode y, WeightedTreeNode x, boolean ZLeft) {
      // Maintain root
      if(z == root)
         root = x;

      // Set parents
      if(x.parent != null)
         x.parent.replace(z, x);
      x.parent = z.parent;
      y.parent = x;
      z.parent = x;

      // Set children
      if(ZLeft) {
         z.right = x.left;
         y.left  = x.right;
         x.left  = z;
         x.right = y;
      }
      else {
         z.left  = x.right;
         y.right = x.left;
         x.left  = y;
         x.right = z;
      }

      // Maintain metadata
      z.setSize();
      y.setSize();
      x.setSize();

      return x;
   }

   public void print() {
      System.out.println("dvrlib.generic.tree.AVLTree(" + size() + ")");
      if(root != null)
         root.print("");
   }
}
