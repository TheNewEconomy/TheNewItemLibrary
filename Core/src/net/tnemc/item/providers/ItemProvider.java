package net.tnemc.item.providers;
/*
 * The New Item Library
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.platform.Identifiable;

/**
 * An ItemProvider represents a provider used to give the Locale stack, and provide various item stack
 * comparisons. This could be used for something such as ItemsAdder, etc.
 *
 * @param <T> The implementation-specific type of this item stack.
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public interface ItemProvider<T> extends Identifiable {

  /**
   * Checks if the given serialized item stack applies to the specified item.
   *
   * @param serialized The serialized item stack to check against the item.
   * @param item The item to check against.
   * @return True if the serialized item stack applies to the item, false otherwise.
   * @since 0.2.0.0
   */
  boolean appliesTo(final AbstractItemStack<? extends T> serialized, final T item);

  /**
   * Checks if the provided item stack is similar to the original item stack.
   *
   * @param original The original item stack to compare against.
   * @param compare The item stack to compare.
   * @return True if the two item stacks are similar, otherwise false.
   * @since 0.2.0.0
   */
  boolean similar(final AbstractItemStack<? extends T> original, final T compare);

  /**
   * Returns true if the provided item is similar to this. An item is similar if the basic
   * information is the same, except for the amount. What this includes: - material - display -
   * modelData - flags - lore - attributes - enchantments
   * What this does not include: - Item Data/components.
   *
   * @param compare The stack to compare.
   *
   * @return True if the two are similar, otherwise false.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @since 0.2.0.0
   */
  default boolean similar(final AbstractItemStack<? extends T> original, final AbstractItemStack<? extends T> compare) {

    return original.itemProvider().equalsIgnoreCase(compare.itemProvider())
           && original.providerItemID().equalsIgnoreCase(compare.providerItemID());
  }

  /**
   * Checks if the components of two items are equal.
   *
   * @param original The original item to compare.
   * @param compare The item to compare against the original.
   * @return true if the components of the two items are equal, otherwise false.
   * @since 0.2.0.0
   */
  default boolean componentsEqual(final AbstractItemStack<? extends T> original, final AbstractItemStack<? extends T> compare) {

    //We don't need to check this since the item providers are direct copies.
    return true;
  }

  /**
   * Checks if the components of two items are equal.
   *
   * @param original The original item to compare.
   * @param compare The item to compare against the original.
   * @return true if the components of the two items are equal, otherwise false.
   * @since 0.2.0.0
   */
  default boolean componentsEqual(final AbstractItemStack<? extends T> original, final T compare) {

    //We don't need to check this since the item providers are direct copies.
    return true;
  }

  /**
   * Checks if the components of two items are equal.
   *
   * @param original The original item to compare.
   * @param compare The item to compare against the original.
   * @return true if the components of the two items are equal, otherwise false.
   * @since 0.2.0.0
   */
  default boolean componentsEqual(final T original, final T compare) {

    //We don't need to check this since the item providers are direct copies.
    return true;
  }

  /**
   * Creates a copy of the original item stack with a specified amount.
   *
   * @param original The original item stack to copy.
   * @param amount The amount for the new item stack.
   * @return A new item stack with the specified amount.
   * @since 0.2.0.0
   */
  T locale(final AbstractItemStack<? extends T> original, final int amount);

  /**
   * @return An instance of the implementation's locale version of AbstractItemStack.
   * @since 0.2.0.0
   * @author creatorfromhell
   * @since 0.2.0.0
   * @since 0.2.0.0
   */
  default T locale(final AbstractItemStack<? extends T> original) {

    return locale(original, 1);
  }
}