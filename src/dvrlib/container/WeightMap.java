/*
 * DvRlib - Container
 * Copyright (C) Duncan van Roermund, 2013
 * WeightMap.java
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

public class WeightMap<E> extends java.util.HashMap<E, Double> {
   protected WeightedTree<E> tree = new WeightedTree<E>();

   /**
    * Inserts the given key&value pair into this WeightMap.
    * If <code>value == null</code>, it is replaced by zero.
    */
   @Override
   public Double put(E key, Double value) {
      Double oldValue = super.put(key, (value == null ? 0d : value));
      if(oldValue != null)
         tree.remove(oldValue, key);
      tree.add(value, key);
      return oldValue;
   }

   @Override
   @SuppressWarnings("unchecked")
   public Double remove(Object key) {
      Double oldValue = super.remove(key);
      if(oldValue != null)
         tree.remove(oldValue, (E) key);
      return oldValue;
   }

   /**
    * Returns an element from the tree, reached with the given normalised weighted index.
    * @see WeightedTree#getWeighted(double)
    */
   public E getWeighted(double normIndex) {
      return tree.getWeighted(normIndex).b;
   }

   /**
    * Increases the weight of the given element with the given value.
    * If the element is not present in this map, it will be added with the given value.
    * @see WeightMap#put(E, Double)
    */
   public void increase(E key, double value) {
      Double oldValue = get(key);
      put(key, (oldValue == null ? 0d : oldValue) + value);
   }

   /**
    * Multiplies the weight of the given element by the given value.
    * If the element is not present in this map, it will be set to zero.
    * @see WeightMap#put(E, Double)
    */
   public void multiply(E key, double value) {
      Double oldValue = get(key);
      put(key, (oldValue == null ? 0d : oldValue * value));
   }
}
