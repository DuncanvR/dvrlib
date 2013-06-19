/*
 * DvRlib - Container
 * Copyright (C) Duncan van Roermund, 2013
 * AbstractBinaryTree.java
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

package dvrlib.container;

import java.util.Comparator;

/**
 * Base class for balanced binary trees.
 */
public abstract class AbstractBinaryTree<N extends AbstractBinaryTree<N>.Node> {
   protected static enum Rotation { Left, Right };
   /**
    * Base class for tree nodes.
    * The left child node will be <= than this one, the right >=.
    */
   protected abstract class Node {
      protected int size   = 1,
                    height = 0;
      protected N parent = null,
                  left   = null,
                  right  = null;

      protected Node(N parent) {
        this.parent = parent;
      }
   }

   protected Comparator<N> cmp;
   protected N             root = null;

   public AbstractBinaryTree(Comparator<N> cmp) {
      this.cmp = cmp;
   }

   /**
    * Removes all elements from this tree.
    * O(1).
    */
   public void clear() {
      root = null;
   }
   /**
    * Returns the height of this tree.
    * O(1).
    */
   public int height() {
      return height(root);
   }
   /**
    * Returns the height of the subtree rooted at the given node.
    * O(1).
    */
   protected int height(N node) {
      return (node == null ? -1 : node.height);
   }
   /**
    * Returns true if this tree holds no elements, false otherwise.
    * O(1).
    */
   public boolean isEmpty() {
      return root == null;
   }
   /**
    * Returns the number of elements in this tree.
    * O(1).
    */
   public int size() {
      return size(root);
   }
   /**
    * Returns the number of elements in the subtree rooted at the given node.
    * O(1).
    */
   protected int size(N node) {
      return (node == null ? 0 : node.size);
   }

   /**
    * Returns the leftmost node in this tree.
    * O(height).
    */
   protected N min() {
      return (root == null ? null : min(root));
   }
   /**
    * Return the leftmost node in the subtree rooted at the given node.
    */
   protected N min(N node) {
      while(node.left != null) {
         node = node.left;
      }
      return node;
   }
   /**
    * Returns the rightmost node in this tree.
    * O(height).
    */
   protected N max() {
      return (root == null ? null : max(root));
   }
   /**
    * Returns the rightmost node in the subtree rooted at the given node.
    */
   protected N max(N node) {
      while(node.right != null) {
         node = node.right;
      }
      return node;
   }

   /**
    * Removes the given node from this tree.
    * O(node.height + node.depth).
    */
   protected void remove(N node) {
      if(node == null)
         throw new IllegalArgumentException("Cannot remove null node");

      if(node.left == null || node.right == null) {
         if(node == root) {
            root = (node.left != null ? node.left : node.right);
            if(root != null)
               root.parent = null;
         }
         else {
            replaceChild(node.parent, node, (node.left != null ? node.left : node.right));
            rebalanceUp(node.parent);
            node.parent = null;
         }
      }
      else {
         N n;
         int dH = height(node.left) - height(node.right),
             dS = size(node.left) - size(node.right);
         if(dH > 0 || (dH == 0 && dS > 0)) {
            if(root == node) {
               root = node.left;
               root.parent = null;
            }
            else
               replaceChild(node.parent, node, node.left);

            n = max(node.left);
            if(node.right != null) {
               n.right = node.right;
               n.right.parent = n;
            }
         }
         else {
            if(root == node) {
               root = node.right;
               root.parent = null;
            }
            else
               replaceChild(node.parent, node, node.right);

            n = min(node.right);
            if(node.left != null) {
               n.left = node.left;
               n.left.parent = n;
            }
         }
         rebalanceUp(n);
      }
   }

   /**
    * Replaces the child node of the given <code>parent</code> that is equal to <code>oldChild</code> with <code>newChild</code>.
    * O(1).
    */
   protected void replaceChild(N parent, N oldChild, N newChild) {
      assert (parent != null)                                                    : "Cannot replace children of null nodes";
      assert (oldChild != null || (parent.left != null || parent.right != null)) : "Cannot replace both child nodes";

      if(parent.left == oldChild)
         parent.left = newChild;
      else if(parent.right == oldChild)
         parent.right = newChild;
      else
         throw new IllegalArgumentException("Given node is not a child of the parent node");

      if(newChild != null)
         newChild.parent = parent;
   }

   /**
    * Updates the size and height of the given node and all its ancestors.
    * @see AbstractBinaryTree#update(AbstractBinaryTree.Node)
    * O(node.depth).
    */
   protected void updateUp(N node) {
      for(; node != null; node = node.parent) {
         update(node);
      }
   }
   /**
    * Updates the size and height of the given node.
    * O(1).
    */
   protected void update(N node) {
      node.size   = size(node.left) + 1 + size(node.right);
      node.height = 1 + Math.max(height(node.left), height(node.right));
   }

   /**
    * Ensures the given node and all its ancestors are balanced.
    * @see AbstractBinaryTree#rebalance(AbstractBinaryTree.Node)
    * O(node.depth).
    */
   protected void rebalanceUp(N node) {
      for(; node != null; node = node.parent) {
         update(node);
         node = rebalance(node);
      }
   }
   /**
    * Rebalances the given node by performing rotations if necessary.
    * @return The node at the original position of the given node after rebalancing this tree.
    * O(1).
    */
   protected N rebalance(N node) {
      if(node != null) {
         int balance = height(node.left) - height(node.right);
         if(balance > 1) {
            if(height(node.left.left) - height(node.left.right) > 0)
               node = singleRotation(node, node.left, node.left.left, Rotation.Right);
            else
               node = doubleRotation(node, node.left, node.left.right, Rotation.Right);
         }
         else if(balance < -1) {
            if(height(node.right.left) - height(node.right.right) > 0)
               node = doubleRotation(node, node.right, node.right.left, Rotation.Left);
            else
               node = singleRotation(node, node.right, node.right.right, Rotation.Left);
         }
         else
            update(node);
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
    * @param r The rotation mode: <code>Rotation.Left</code> indicates <code>z</code> ends up as the left child of <code>y</code> as the top node,
    *                             <code>Rotation.Right</code> indicates <code>z</code> ends up as the right child of <code>y</code>.
    * @return The new top node: <code>y</code>.
    * O(1).
    */
   protected N singleRotation(N z, N y, N x, Rotation r) {
      assert (z != null && y != null)        : "Cannot rotate on null nodes";
      assert (z.parent != null || z == root) : "Node z should have a parent or be the root";
      assert (y.parent == z)                 : "Node z should be the parent of y";
      assert (x == null || x.parent == y)    : "Node y should be the parent of x";
      switch(r) {
         case Left:
            assert (z.right == y) : "Node y should be the right child of z in order to perform a single left rotation";
            assert (y.right == x) : "Node x should be the right child of y in order to perform a single left rotation";
            break;
         case Right:
            assert (z.left == y) : "Node y should be the left child of z in order to perform a single right rotation";
            assert (y.left == x) : "Node x should be the left child of y in order to perform a single right rotation";
            break;
         default:
            throw new IllegalArgumentException("Unknown value of AbstractBinaryTree.Rotation given: " + r);
      }

      // Maintain root and parents
      if(z == root) {
         root = y;
         root.parent = null;
      }
      else
         replaceChild(z.parent, z, y);
      z.parent = y;

      // Set children
      switch(r) {
         case Left:
            replaceChild(z, z.right, y.left);
            y.left  = z;
            break;
         case Right:
            replaceChild(z, z.left, y.right);
            y.right = z;
            break;
      }

      // Maintain metadata
      update(z);
      update(y);

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
    * @param r The rotation mode: <code>Rotation.Left</code> indicates <code>z</code> ends up as the left child of <code>x</code> as the top node,
    *                             <code>Rotation.Right</code> indicates <code>z</code> ends up as the right child of <code>x</code>.
    * @return The new top node: <code>x</code>.
    * O(1).
    */
   protected N doubleRotation(N z, N y, N x, Rotation r) {
      assert (z != null && y != null && x != null) : "Cannot rotate on null nodes";
      assert (z.parent != null || z == root)       : "Node z should have a parent or be the root";
      assert (y.parent == z)                       : "Node z should be the parent of y";
      assert (x.parent == y)                       : "Node y should be the parent of x";
      switch(r) {
         case Left:
            assert (z.right == y) : "Node y should be the right child of z in order to perform a double left rotation";
            assert (y.left == x)  : "Node x should be the left child of y in order to perform a double left rotation";
            break;
         case Right:
            assert (z.left == y)  : "Node y should be the left child of z in order to perform a double right rotation";
            assert (y.right == x) : "Node x should be the right child of y in order to perform a double right rotation";
            break;
         default:
            throw new IllegalArgumentException("Unknown value of AbstractBinaryTree.Rotation given: " + r);
      }

      // Maintain root and parents
      if(z == root) {
         root = x;
         root.parent = null;
      }
      else
         replaceChild(z.parent, z, x);
      y.parent = x;
      z.parent = x;

      // Set children
      switch(r) {
         case Left:
            replaceChild(z, z.right, x.left);
            replaceChild(y, y.left, x.right);
            x.left  = z;
            x.right = y;
            break;
         case Right:
            replaceChild(z, z.left, x.right);
            replaceChild(y, y.right, x.left);
            x.left  = y;
            x.right = z;
            break;
      }

      // Maintain metadata
      update(z);
      update(y);
      update(x);

      return x;
   }
}
