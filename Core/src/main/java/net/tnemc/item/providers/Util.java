package net.tnemc.item.providers;
/*
 * The New Economy
 * Copyright (C) 2025 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Util
 *
 * @author creatorfromhell
 * @since 1.0.0.0
 */
public class Util {

  /**
   * Compares the text content of two lists of Components to determine if they are equal.
   *
   * @param list1 the first list of Components to compare
   * @param list2 the second list of Components to compare
   * @return true if the text content of the two lists is equal, false otherwise
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  public static boolean textComponentsEqual(final List<Component> list1, final List<Component> list2) {
    final LinkedList<String> list1Copy = new LinkedList<>();
    for(final Component component : list1) {
      list1Copy.add(PlainTextComponentSerializer.plainText().serialize(component));
    }

    final LinkedList<String> list2Copy = new LinkedList<>();
    for(final Component component : list2) {
      list2Copy.add(PlainTextComponentSerializer.plainText().serialize(component));
    }
    return listsEquals(list1Copy, list2Copy);
  }

  /**
   * Compares two lists for equality regardless of order.
   *
   * @param list1 the first list to be compared
   * @param list2 the second list to be compared
   * @return true if the two lists contain the same elements, false otherwise
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  public static <V> boolean listsEquals(final List<V> list1, final List<V> list2) {
    return new HashSet<>(list1).containsAll(list2) && new HashSet<>(list2).containsAll(list1);
  }

  /**
   * Compares two lists to check if they contain the same elements.
   *
   * @param list1 the first list to compare
   * @param list2 the second list to compare
   * @param debug true if debug information should be printed, false otherwise
   * @param <V> the type of elements in the lists
   * @return true if the lists contain the same elements, false otherwise
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  public static <V> boolean listsEquals(final List<V> list1, final List<V> list2, final boolean debug) {

    if(debug) {

      System.out.println("List 1");
      for(final V item : list1) {
        System.out.println("Item: " + item);
      }

      System.out.println("List 2");
      for(final V item : list2) {
        System.out.println("Item: " + item);
      }
    }

    return new HashSet<>(list1).containsAll(list2) && new HashSet<>(list2).containsAll(list1);
  }

  /**
   * Compares two sets to see if they are equal. The sets are considered equal if each set contains all elements of the other set.
   *
   * @param list1 the first set to be compared
   * @param list2 the second set to be compared
   * @return true if the sets are equal, false otherwise
   * @since 0.2.0.0
   * @author creatorfromhell
   */
  public static <V> boolean setsEquals(final Set<V> list1, final Set<V> list2) {
    return new HashSet<>(list1).containsAll(list2) && new HashSet<>(list2).containsAll(list1);
  }
}