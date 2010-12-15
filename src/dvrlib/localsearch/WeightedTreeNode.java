/*
 * DvRLib - Local search
 * Duncan van Roermund, 2010
 * WeightedTreeNode.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
import java.util.ArrayList;
import java.util.List;

public class WeightedTreeNode<E> {
   protected WeightedTreeNode<E> parent = null, left = null, right = null;
   protected List<E> values;
   protected double key, weight;
   protected int size = 1, height = 0;

   public WeightedTreeNode(double key, E value) {
      values = new ArrayList<E>();
      values.add(value);
      this.key = key;
      weight = key;
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

   public double getWeight() {
      return weight;
   }

   public double getLeftWeight() {
      return (left == null ? 0 : left.weight);
   }

   public double getRightWeight() {
      return (right == null ? 0 : right.weight);
   }

   /**
    * Updates the size, height and weight of this node.
    * O(1).
    */
   protected void updateSize() {
      size   = getLeftSize() + values.size() + getRightSize();
      height = Math.max(getLeftHeight() + 1, getRightHeight() + 1);
      weight = getLeftWeight() + key * values.size() + getRightWeight();
   }

   /**
    * @see WeightedTree#getWeighted(double)
    */
   public Pair<Double, E> getWeighted(double normIndex) {
      double leftRatio  = getLeftWeight() / weight,
             rightRatio = (weight - getRightWeight()) / weight;
      if(normIndex < leftRatio)
         return new Pair(key, left.getWeighted(normIndex / leftRatio));
      else if(normIndex > rightRatio)
         return new Pair(key, right.getWeighted(normIndex / rightRatio));
      else
         return new Pair(key, values.get((int)((normIndex - leftRatio) * values.size())));
   }

   /**
    * Adds the given (key, value) to this subtree and returns the node it ends up in.
    * O(height).
    */
   public WeightedTreeNode<E> add(double key, E value) {
      WeightedTreeNode<E> node = null;
      if(key < this.key) {
         if(left == null) {
            node = new WeightedTreeNode<E>(key, value);
            left = node;
            left.parent = this;
         }
         else
            node = left.add(key, value);
      }
      else if(key > this.key) {
         if(right == null) {
            node  = new WeightedTreeNode<E>(key, value);
            right = node;
            right.parent = this;
         }
         else
            node = right.add(key, value);
      }
      else { // key == this.key
         values.add(value);
         node = this;
      }
      updateSize();
      return node;
   }

   /**
    * Adds the given (key, value) to this subtree and returns the node it ends up in.
    * @see WeightedTreeNode#add(double, java.lang.Object)
    */
   public WeightedTreeNode<E> add(Pair<Double, E> kv) {
      return add(kv.a, kv.b);
   }

   /**
    * Replaces the old subtree by the new one.
    * @param old The subtree that will be replaced, either <tt>old == left</tt> or <tt>old == right</tt> should hold.
    * O(1).
    */
   protected void replace(WeightedTreeNode<E> old, WeightedTreeNode<E> that) {
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
      else
         throw new IllegalArgumentException("WeightedTreeNode.replace(WeightedTreeNode, WeightedTreeNode) expects the first argument to be equal to either the left or the right subtree");
      updateSize();
   }

   /**
    * Returns the element that was added last.
    * O(1).
    */
   public E peek() {
      return values.get(values.size() - 1);
   }

   /**
    * Removes the element that was added last.
    * O(1).
    */
   public E pop() {
      return values.remove(values.size() - 1);
   }

   /**
    * Returns the node with the smallest key in the subtree of this node.
    * O(height).
    */
   protected WeightedTreeNode<E> getMin() {
      return (left == null ? this : left.getMin());
   }

   /**
    * Returns the node with the largest key in the subtree of this node.
    * O(height).
    */
   protected WeightedTreeNode<E> getMax() {
      return (right == null ? this : right.getMax());
   }

   @Override
   public String toString() {
      return "TreeNode(" + key + "," + values + ")["+ size + "|" + height +"]{" + left + "," + right + "}";
   }

   /**
    * Prints this node and its children to stdout.
    * O(height).
    */
   public void print(String prefix) {
      if(left != null)
         left.print(prefix + "\t");
      else
         System.out.println(prefix + "\tnull");
      System.out.println(prefix + key + ": " + values);
      if(right != null)
         right.print(prefix + "\t");
      else
         System.out.println(prefix + "\tnull");
   }
}
