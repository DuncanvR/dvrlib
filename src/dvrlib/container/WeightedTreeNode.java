/*
 * DvRLib - Container
 * Duncan van Roermund, 2010-2012
 * WeightedTreeNode.java
 */

package dvrlib.container;

import dvrlib.generic.Tuple;

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

   public boolean contains(double key, E value) {
      if(key < this.key)
         return (left == null ? false : left.contains(key, value));
      else if(key > this.key)
         return (right == null ? false : right.contains(key, value));
      else // key == this.key
         return values.contains(value);
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
   public Tuple<Double, E> getWeighted(double normIndex) {
      if(normIndex < 0 || normIndex >= 1.0)
         throw new IllegalArgumentException("normIndex should be between 0 (inclusive) and 1 (exclusive), got " + normIndex);

      double l = getLeftWeight();
      if(l > 0) {
         l = l / weight;
         if(normIndex < l)
            return left.getWeighted(normIndex / l);
      }
      double r = getRightWeight();
      if(r > 0) {
         r = (weight - r) / weight;
         if(normIndex > r)
            return right.getWeighted((normIndex - r) / (1 - r));
      }
      else
         r = 1.0;

      return new Tuple<Double, E>(key, values.get((int)(values.size() * (normIndex - l) / (r - l))));
   }

   /**
    * Adds the given (key, value) to this subtree and returns the node it ends up in.
    * O(height).
    */
   public WeightedTreeNode<E> add(double key, E value) {
      WeightedTreeNode<E> node = null;
      if(key < this.key) {
         if(left == null) {
            left = node = new WeightedTreeNode<E>(key, value);
            left.parent = this;
         }
         else
            node = left.add(key, value);
      }
      else if(key > this.key) {
         if(right == null) {
            right = node = new WeightedTreeNode<E>(key, value);
            right.parent = this;
         }
         else
            node = right.add(key, value);
      }
      else { // key == this.key
         if(values.contains(value))
            return null;
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
   public WeightedTreeNode<E> add(Tuple<Double, E> kv) {
      return add(kv.a, kv.b);
   }

   /**
    * Replaces the old subtree by the new one.
    * @param old The subtree that will be replaced, either <tt>old == left</tt> or <tt>old == right</tt> should hold.
    * O(1).
    */
   protected void replaceChild(WeightedTreeNode<E> old, WeightedTreeNode<E> that) {
      if(old == left)
         left = that;
      else if(old == right)
         right = that;
      else
         throw new IllegalArgumentException("WeightedTreeNode.replaceChild(WeightedTreeNode, WeightedTreeNode) expects the first argument to be one of it's subnodes");

      if(that != null)
         that.parent = this;
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
