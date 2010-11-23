/*
 * DvRLib - Generic
 * Duncan van Roermund, 2010
 * WeightedTreeNode.java
 */

package dvrlib.localsearch;

import dvrlib.generic.Pair;
import java.util.LinkedList;
import java.util.List;

public class WeightedTreeNode<E> {
   protected WeightedTreeNode<E> parent = null, left = null, right = null;
   protected List<E> values;
   protected double key, weight;
   protected int size = 1, height = 0;

   public WeightedTreeNode(double key, E value) {
      values = new LinkedList<E>();
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

   public E getWeighted(double normIndex) {
      double leftRatio  = getLeftWeight() / weight,
             rightRatio = (weight - getRightWeight()) / weight;
      if(normIndex < leftRatio)
         return left.getWeighted(normIndex / leftRatio);
      else if(normIndex > rightRatio)
         return right.getWeighted(normIndex / rightRatio);
      else
         return values.get((int)((normIndex - leftRatio) * values.size()));
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
      updateSize();
   }

   public Pair<Double, E> peek() {
      return new Pair(key, values.get(values.size() - 1));
   }

   public Pair<Double, E> pop() {
      return new Pair(key, values.remove(values.size() - 1));
   }

   @Override
   public String toString() {
      return "TreeNode(" + key + "," + values + ")["+ size + "|" + height +"]{" + left + "," + right + "}";
   }

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
