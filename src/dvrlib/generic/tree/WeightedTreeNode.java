/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * WeightedTreeNode.java
 */

package dvrlib.generic.tree;

import dvrlib.generic.Pair;

public class WeightedTreeNode<K extends Comparable, V> extends Pair<K, V> {
   protected WeightedTreeNode parent = null, left = null, right = null;
   protected int size = 1, height = 0;

   public WeightedTreeNode(K key, V value) {
      super(key, value);
   }

   /**
    * Updates the size and height of this node.
    * O(1).
    */
   protected void setSize() {
      size = getLeftSize() + 1 + getRightSize();
      height = Math.max(getLeftHeight() + 1, getRightHeight() + 1);
   }

   /**
    * Sets the given node as the left child, updates the size and height, and returns the replaced node.
    * O(1).
    */
   protected WeightedTreeNode setLeft(WeightedTreeNode that) {
      WeightedTreeNode old = left;
      left = that;
      left.parent = this;
      setSize();
      return old;
   }

   /**
    * Sets the given node as the right child, updates the size and height, and returns the replaced node.
    * O(1).
    */
   protected WeightedTreeNode setRight(WeightedTreeNode that) {
      WeightedTreeNode old = right;
      right = that;
      right.parent = this;
      setSize();
      return old;
   }

   protected void replace(WeightedTreeNode old, WeightedTreeNode that) {
      if(old == left) {
         left = that;
         if(that != null)
            that.parent = this;
      }
      else if(old == right) {
         right = that;
         if(that != null)
            that.parent = this;
      }
      setSize();
   }

   public int getHeight() {
      return height;
   }

   public int getLeftHeight() {
      return (left == null ? -1 : left.height);
   }

   public int getRightHeight() {
      return (right == null ? -1 : right.height);
   }
   
   public int getSize() {
      return size;
   }
   
   public int getLeftSize() {
      return (left == null ? 0 : left.size);
   }
   
   public int getRightSize() {
      return (right == null ? 0 : right.size);
   }

   @Override
   public String toString() {
      return "TreeNode(" + a + "," + b + ")["+ size + "|" + height +"]{" + left + "," + right + "}";
   }

   public void print(String prefix) {
      if(left != null)
         left.print(prefix + "\t");
      else
         System.out.println(prefix + "\tnull");
      System.out.println(prefix + a + ": " + b);
      if(right != null)
         right.print(prefix + "\t");
      else
         System.out.println(prefix + "\tnull");
   }
}
