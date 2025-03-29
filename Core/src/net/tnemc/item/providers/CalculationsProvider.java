package net.tnemc.item.providers;

/*
 * The New Item Library Minecraft Server Plugin
 *
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
import net.tnemc.item.InventoryType;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a provider that is utilized for item-based calculations and comparisons.
 *
 * @param <I> The implementation's instance of {@link AbstractItemStack}
 * @param <S> The implementation's instance of item stacks.
 * @param <U> The implementation's instace of inventories.
 *
 * @author creatorfromhell
 * @since 0.1.5.0
 */
public interface CalculationsProvider<I extends AbstractItemStack<S>, S, U> {

  /**
   * Used to drop items near a player.
   *
   * @param left   A Collection containing the items to drop.
   * @param player The UUID of the player to drop the items near.
   *
   * @return True if the items were successfully dropped, otherwise false.
   */
  boolean drop(Collection<I> left, UUID player);

  /**
   * Removes all items that are equal to the stack from an inventory.
   *
   * @param stack     The stack to compare to for removal from the inventory.
   * @param inventory The inventory to remove the items from.
   *
   * @return The amount of items removed.
   */
  int removeAll(I stack, U inventory);

  /**
   * Removes all items that are equal to the stack from an inventory.
   *
   * @param stack      The stack to compare to for removal from the inventory.
   * @param identifier The identifier of the player to remove the items from.
   *
   * @return The amount of items removed.
   */
  default int removeAll(final I stack, final UUID identifier) {

    final Optional<U> inventory = getInventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->removeAll(stack, u)).orElse(0);
  }

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   *
   * @param stack     The stack to get a count of.
   * @param inventory The inventory to check.
   *
   * @return The total count of items in the inventory.
   */
  int count(I stack, U inventory);

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   *
   * @param stack      The stack to get a count of.
   * @param identifier The identifier of the player to check.
   *
   * @return The total count of items in the inventory.
   */
  default int count(final I stack, final UUID identifier) {

    final Optional<U> inventory = getInventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->count(stack, u)).orElse(0);
  }

  /**
   * Takes a collection of items from an inventory.
   *
   * @param items     The collection of items to remove.
   * @param inventory The inventory to remove the items from.
   */
  void takeItems(Collection<I> items, U inventory);

  /**
   * Takes a collection of items from an inventory.
   *
   * @param items      The collection of items to remove.
   * @param identifier The identifier of the player to remove the items from.
   */
  default void takeItems(final Collection<I> items, final UUID identifier) {

    final Optional<U> inventory = getInventory(identifier, InventoryType.PLAYER);
    inventory.ifPresent(u->takeItems(items, u));
  }

  /**
   * Adds a collection of item stacks to an inventory, returns the leftover items that won't fit in
   * the inventory.
   *
   * @param items     The collection of items to add to the inventory.
   * @param inventory The inventory to add the collection of items to.
   *
   * @return The collection of items that won't fit in the inventory.
   */
  Collection<I> giveItems(Collection<I> items, U inventory);

  /**
   * Adds a collection of item stacks to an inventory, returns the leftover items that won't fit in
   * the inventory.
   *
   * @param items      The collection of items to add to the inventory.
   * @param identifier The identifier of the player to add the collection of items to.
   *
   * @return The collection of items that won't fit in the inventory.
   */
  default Collection<I> giveItems(final Collection<I> items, final UUID identifier) {

    final Optional<U> inventory = getInventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->giveItems(items, u)).orElse(items);
  }

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   *
   * @param stack     The stack, with the correct amount, to remove.
   * @param inventory The inventory to remove the net.tnemc.item stack from.
   *
   * @return The remaining amount of items to remove.
   */
  int removeItem(I stack, U inventory);

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   *
   * @param stack      The stack, with the correct amount, to remove.
   * @param identifier The identifier of the player to remove the net.tnemc.item stack from.
   *
   * @return The remaining amount of items to remove.
   */
  default int removeItem(final I stack, final UUID identifier) {

    final Optional<U> inventory = getInventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->removeItem(stack, u)).orElseGet(stack::amount);
  }

  /**
   * Used to locate an invetory for a UUID identifier.
   *
   * @param identifier The identifier to use for the search.
   * @param type       The inventory type to return.
   *
   * @return An optional containing the inventory if it works, otherwise false.
   */
  Optional<U> getInventory(UUID identifier, InventoryType type);

  /**
   * Checks to see if two net.tnemc.item stacks are equal.
   *
   * @param original The original net.tnemc.item stack.
   * @param compare  The net.tnemc.item stack you're comparing to the original.
   *
   * @return True if the net.tnemc.item stacks are equal, otherwise false.
   */
  default boolean itemsEqual(final I original, final S compare) {

    if(!original.itemProviderObject().similar(original, compare)) {
      return false;
    }

    return original.itemProviderObject().componentsEqual(original, compare);
  }

  /**
   * Checks to see if two net.tnemc.item stacks are equal.
   *
   * @param original The original net.tnemc.item stack.
   * @param compare  The net.tnemc.item stack you're comparing to the original.
   *
   * @return True if the net.tnemc.item stacks are equal, otherwise false.
   */
  default boolean itemsEqual(final I original, final I compare) {

    if(!original.itemProviderObject().similar(original, compare)) {
      return false;
    }

    return original.itemProviderObject().componentsEqual(original, compare);
  }
}