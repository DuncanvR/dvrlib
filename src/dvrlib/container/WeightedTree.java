/*
 * DvRlib - Container
 * Copyright (C) Duncan van Roermund, 2010-2013
 * WeightedTree.java
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

import dvrlib.generic.Pair;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class WeightedTree<E> extends AbstractBinaryTree<WeightedTree<E>.Node> implements Iterable<E> {
   protected class Node extends AbstractBinaryTree<WeightedTree<E>.Node>.Node {
      protected double       key, weight;
      protected ArrayList<E> values = new ArrayList<E>(1);

      protected Node(Node parent, double key, E value) {
         super(parent);
         this.key = key;
         values.add(value);
         weight = key;
      }

      @Override
      public String toString() {
         return "WeightedTree.Node(" + key + "," + values + ")[" + height + "|" + size + "|" + weight + "]";
      }
   }

   public WeightedTree() {
      super(new java.util.Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
               return Double.compare(a.key, b.key);
            }
         });
   }

   /**
    * Adds a node with the given key and value to this tree.
    * @return A boolean indicating whether the operation succeeded.
    * @throws IllegalArgumentException When the given key is <code>null</code>.
    * @see WeightedTree#rebalanceUp(WeightedTreeNode)
    * O(height)
    */
   public boolean add(double key, E value) {
      if(root == null)
         root = new Node(null, key, value);
      else {
         Node node = root;
         while(true) {
            if(key < node.key) {
               if(node.left == null) {
                  node.left = new Node(node, key, value);
                  break;
               }
               else
                  node = node.left;
            }
            else if(key > node.key) {
               if(node.right == null) {
                  node.right = new Node(node, key, value);
                  break;
               }
               else
                  node = node.right;
            }
            else { // key == node.key
               node.values.add(value);
               break;
            }
         }
         rebalanceUp(node);
      }
      return true;
   }

   /**
    * Returns whether this tree contains a node with the given key and value.
    * @return true if this tree contains the given key/value combination, false otherwise.
    * O(height)
    */
   public boolean contains(double key, E value) {
      Node node = node(key);
      return (node == null ? false : node.values.contains(value));
   }

   /**
    * Returns a (key, element) from the tree, reached with the given index.
    * An index of 0 refers to an item with the smallest key.
    * @param index An integer between 0 (inclusive) and the size of this tree (exclusive).
    */
   public Pair<Double, E> getIndexed(int index) {
      if(index < 0 || index >= size())
         throw new IllegalArgumentException("The argument of WeightedTree.getIndexed(int) should be between 0 (inclusive) and the size of the tree (exclusive), got " + index);
      return (root == null ? null : getIndexed(root, index));
   }
   /**
    * Returns a (key, element) from the subtree rooted at the given node, reached with the given index.
    * An index of 0 refers to an item with the smallest key.
    * @param index An integer between 0 (inclusive) and the size of this subtree (exclusive).
    */
   protected Pair<Double, E> getIndexed(Node node, int index) {
      assert (index >= 0 && index < node.size) : "index should be between 0 (inclusive) and the size of the tree (exclusive), got " + index;
      if(node.left != null) {
         if(index < node.left.size)
            return getIndexed(node.left, index);
         else index -= node.left.size;
      }
      if(node.right != null && index >= node.values.size())
         return getIndexed(node.right, index - node.values.size());
      return new Pair<Double, E>(node.key, node.values.get(index));
   }

   /**
    * Returns a (key, element) from the tree, reached with the given normalised weighted index.
    * An index of 0 refers to an item with the smallest key.
    * For example, in a tree with these four items: {(1, A), (2, B), (1.3, C), (1.7, D)}, the sum of the indices is 6.
    * A call to getWeighted(0.5) will then return the item with the summed index (0.5 * 6 = 3).
    * Moving through the tree from the smallest index, (A: 1 &lt; 3), (C: 1.3 &lt; (3 - 1)), (D: 1.7 &gt;= (3 - 1 - 1.3)).
    * This means the item with the largest index has the most chance of getting selected, hence the weight.
    * @param normIndex A double between 0 (inclusive) and 1 (exclusive).
    */
   public Pair<Double, E> getWeighted(double normIndex) {
      if(normIndex < 0 || normIndex >= 1)
         throw new IllegalArgumentException("The argument of WeightedTree.getWeighted(double) should be between 0 (inclusive) and 1 (exclusive), got " + normIndex);
      return (root == null ? null : getWeighted(root, normIndex));
   }
   /**
    * Returns a (key, element) from the subtree rooted at the given node, reached with the given normalised weighted index.
    * An index of 0 refers to an item with the smallest key.
    * @param normIndex A double between 0 (inclusive) and 1 (exclusive).
    */
   protected Pair<Double, E> getWeighted(Node node, double normIndex) {
      assert (normIndex >= 0d && normIndex < 1d) : "normIndex should be between 0 (inclusive) and 1 (exclusive), got " + normIndex;

      double l = weight(node.left);
      if(l > 0d) {
         l = l / node.weight;
         if(normIndex < l)
            return getWeighted(node.left, normIndex / l);
      }
      double r = weight(node.right);
      if(r > 0d) {
         r = (node.weight - r) / node.weight;
         if(normIndex > r)
            return getWeighted(node.right, (normIndex - r) / (1d - r));
      }
      else
         r = 1d;

      return new Pair<Double, E>(node.key, node.values.get((int)(node.values.size() * (normIndex - l) / (r - l))));
   }

   /**
    * Returns the node with the given key, or <code>null</code> if there is no such node.
    */
   protected Node node(double key) {
      Node node = root;
      while(node != null) {
         if(key < node.key)
            node = node.left;
         else if(key > node.key)
            node = node.right;
         else // key == node.key
            break;
      }
      return node;
   }

   /**
    * Returns an element from the node with the smallest key in this tree, along with that key.
    * O(height).
    */
   public Pair<Double, E> peekMin() {
      return peek(min());
   }
   /**
    * Returns an element from the node with the largest key in this tree, along with that key.
    * O(height).
    */
   public Pair<Double, E> peekMax() {
      return peek(max());
   }
   /**
    * Returns the element that was added last to the given node, along with the key of the node.
    * O(1).
    */
   protected Pair<Double, E> peek(Node node) {
      return (node == null ? null : new Pair<Double, E>(node.key, node.values.get(node.values.size() - 1)));
   }

   /**
    * Removes an element from the node with the smallest key in this tree, and returns it along with that key.
    */
   public Pair<Double, E> popMin() {
      return pop(min());
   }
   /**
    * Removes an element from the node with the largest key in this tree, and returns it along with that key.
    */
   public Pair<Double, E> popMax() {
      return pop(max());
   }
   /**
    * Removes and returns the element that was added last to the given node, along with the key of the node.
    * If the node contains only one element, it is removed from the tree.
    * O(node.depth) if the node is removed, O(1) otherwise.
    */
   protected Pair<Double, E> pop(Node node) {
      if(node == null)
         throw new IllegalArgumentException("Cannot call WeightedTree.pop() on a null node");
      Pair<Double, E> p = new Pair<Double, E>(node.key, node.values.remove(node.values.size() - 1));
      if(node.values.isEmpty())
         remove(node);
      else
         updateUp(node);
      return p;
   }

   /**
    * Prints this tree to stdout.
    * @see WeightedTree#print(java.io.PrintStream)
    */
   public void print() {
      print(System.out);
   }
   /**
    * Prints this tree to the given stream.
    * O(n).
    */
   public void print(PrintStream out) {
      out.println("dvrlib.container.WeightedTree(" + size() + ")");
      if(root != null)
         print(out, root, "");
   }
   /**
    * Prints the subtree rooted at the given node with the given prefix to the given stream.
    */
   protected void print(PrintStream out, Node node, String prefix) {
      if(node.left != null)
         print(out, node.left, prefix + "\t");
      else
         out.println(prefix + "\tnull");
      out.println(prefix + node);
      if(node.right != null)
         print(out, node.right, prefix + "\t");
      else
         out.println(prefix + "\tnull");
   }

   /**
    * Removes the given key and value from this tree.
    * @return A boolean indicating whether the value was actually removed.
    */
   public boolean remove(double key, E value) {
      Node node = node(key);
      if(node != null && node.values.remove(value)) {
         if(node.values.isEmpty())
            remove(node);
         else
            updateUp(node);
         return true;
      }
      return false;
   }

   /**
    * Returns the weight of the subtree rooted at the given node.
    * O(1).
    */
   protected double weight(Node node) {
      return (node == null ? 0d : node.weight);
   }

   //--- AbstractBinaryTree methods
   @Override
   public void update(Node node) {
      node.size   = size(node.left) + node.values.size() + size(node.right);
      node.height = 1 + Math.max(height(node.left), height(node.right));
      node.weight = weight(node.left) + node.key * node.values.size() + weight(node.right);
   }

   //--- java.lang.Iterable method
   /**
    * Returns an iterator to the elements in this tree, starting from the element with the largest weight.
    */
   @Override
   public Iterator<E> iterator() {
      return new Iterator<E>() {
            protected Node        node         = max();
            protected Iterator<E> nodeIterator = (node == null ? null : node.values.iterator());

            @Override
            public boolean hasNext() {
               return (nodeIterator != null);
            }

            @Override
            public E next() {
               assert hasNext()              : "!hasNext()";
               assert (node != null)         : "node == null";
               assert (nodeIterator != null) : "nodeIterator == null";
               assert nodeIterator.hasNext() : "!nodeIterator.hasNext()";

               E next = nodeIterator.next();
               if(!nodeIterator.hasNext()) {
                  if(node.left != null)
                     node = max(node.left);
                  else {
                     while(node.parent != null && node.parent.left == node) {
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
         };
   }

   //--- java.util.Collection like methods
   /**
    * Returns the elements in this tree as an array.
    */
   public Object[] toArray() {
      Object array[] = new Object[size()];
      int i = 0;
      for(E e : this) {
         array[i++] = e;
      }
      return array;
   }
   /**
    * Copies the elements in this tree to the given array.
    */
   @SuppressWarnings("unchecked")
   public <T> T[] toArray(T[] a) {
      if(a.length < size())
         a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size());
      int i = 0;
      for(E e : this) {
         a[i++] = (T) e;
      }
      if(i < a.length - 1)
         a[i] = null;
      return a;
   }
}
