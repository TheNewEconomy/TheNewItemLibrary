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
   * Removes items from a collection based on certain criteria.
   *
   * @param left     The collection of items from which to remove items.
   * @param player   The UUID of the player associated with the removal operation.
   * @param setOwner Indicates whether to set the owner of the removed items.(supports spigot/paper
   *                 1.16.5+)
   *
   * @return True if the removal operation was successful, false otherwise.
   */
  boolean drop(Collection<I> left, UUID player, final boolean setOwner);

  /**
   * Removes all items that are equal to the stack from an inventory.
   *
   * @param stack     The stack to compare to for removal from the inventory.
   * @param inventory The inventory to remove the items from.
   *
   * @return The amount of items removed.
   */
  default int removeAll(final I stack, final U inventory) {

    return removeAll(stack, inventory, true, true);
  }

  /**
   * Removes all items from the inventory that match the specified stack, with additional options to
   * consider useShulker boxes and useBundles.
   *
   * @param stack      The stack to compare against for removal.
   * @param inventory  The inventory from which to remove items.
   * @param useShulker A boolean flag indicating whether to include useShulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include useBundles in the removal
   *                   process.
   *
   * @return The count of items that were successfully removed from the inventory.
   */
  int removeAll(I stack, U inventory, boolean useShulker, boolean useBundles);

  /**
   * Removes all items that are equal to the stack from an inventory.
   *
   * @param stack      The stack to compare to for removal from the inventory.
   * @param identifier The identifier of the player to remove the items from.
   *
   * @return The amount of items removed.
   */
  default int removeAll(final I stack, final UUID identifier) {

    final Optional<U> inventory = inventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->removeAll(stack, u, true, true)).orElse(0);
  }

  /**
   * Removes all items that are equal to the stack from an inventory.
   *
   * @param stack      The stack to compare to for removal from the inventory.
   * @param identifier The identifier of the player to remove the items from.
   * @param useShulker A boolean flag indicating whether to include useShulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include useBundles in the removal
   *                   process.
   *
   * @return The amount of items removed.
   */
  default int removeAll(final I stack, final UUID identifier, final boolean useShulker, final boolean useBundles) {

    final Optional<U> inventory = inventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->removeAll(stack, u, useShulker, useBundles)).orElse(0);
  }

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   *
   * @param stack     The stack to get a count of.
   * @param inventory The inventory to check.
   *
   * @return The total count of items in the inventory.
   */
  default int count(final I stack, final U inventory) {

    return count(stack, inventory, true, true);
  }

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   *
   * @param stack      The stack to get a count of.
   * @param inventory  The inventory to check.
   * @param useShulker A boolean flag indicating whether to include useShulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include useBundles in the removal
   *                   process.
   *
   * @return The total count of items in the inventory.
   */
  int count(I stack, U inventory, boolean useShulker, boolean useBundles);

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   *
   * @param stack      The stack to get a count of.
   * @param identifier The identifier of the player to check.
   *
   * @return The total count of items in the inventory.
   */
  default int count(final I stack, final UUID identifier) {

    final Optional<U> inventory = inventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->count(stack, u, true, true)).orElse(0);
  }

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   *
   * @param stack      The stack to get a count of.
   * @param identifier The identifier of the player to check.
   * @param useShulker A boolean flag indicating whether to include useShulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include useBundles in the removal
   *                   process.
   *
   * @return The total count of items in the inventory.
   */
  default int count(final I stack, final UUID identifier, final boolean useShulker, final boolean useBundles) {

    final Optional<U> inventory = inventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->count(stack, u, useShulker, useBundles)).orElse(0);
  }

  /**
   * Takes a collection of items from an inventory.
   *
   * @param items     The collection of items to remove.
   * @param inventory The inventory to remove the items from.
   */
  default void takeItems(final Collection<I> items, final U inventory) {

    takeItems(items, inventory, true, true);
  }

  /**
   * Takes a collection of items from an inventory.
   *
   * @param items      The collection of items to remove.
   * @param inventory  The inventory to remove the items from.
   * @param useShulker A boolean flag indicating whether to include useShulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include useBundles in the removal
   *                   process.
   */
  void takeItems(Collection<I> items, U inventory, boolean useShulker, boolean useBundles);

  /**
   * Takes a collection of items from an inventory.
   *
   * @param items      The collection of items to remove.
   * @param identifier The identifier of the player to remove the items from.
   */
  default void takeItems(final Collection<I> items, final UUID identifier) {

    final Optional<U> inventory = inventory(identifier, InventoryType.PLAYER);
    inventory.ifPresent(u->takeItems(items, u, true, true));
  }

  /**
   * Takes a collection of items from an inventory.
   *
   * @param items      The collection of items to remove.
   * @param identifier The identifier of the player to remove the items from.
   * @param useShulker A boolean flag indicating whether to include useShulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include useBundles in the removal
   *                   process.
   */
  default void takeItems(final Collection<I> items, final UUID identifier, final boolean useShulker, final boolean useBundles) {

    final Optional<U> inventory = inventory(identifier, InventoryType.PLAYER);
    inventory.ifPresent(u->takeItems(items, u, useShulker, useBundles));
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
  default Collection<I> giveItems(final Collection<I> items, final U inventory) {

    return giveItems(items, inventory, true, true);
  }

  /**
   * Adds a collection of item stacks to an inventory, returns the leftover items that won't fit in
   * the inventory.
   *
   * @param items      The collection of items to add to the inventory.
   * @param inventory  The inventory to add the collection of items to.
   * @param useShulker A boolean flag indicating whether to include useShulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include useBundles in the removal
   *                   process.
   *
   * @return The collection of items that won't fit in the inventory.
   */
  Collection<I> giveItems(Collection<I> items, U inventory, final boolean useShulker, final boolean useBundles);

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

    final Optional<U> inventory = inventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->giveItems(items, u, true, true)).orElse(items);
  }

  /**
   * Adds a collection of item stacks to an inventory, returns the leftover items that won't fit in
   * the inventory.
   *
   * @param items      The collection of items to add to the inventory.
   * @param identifier The identifier of the player to add the collection of items to.
   * @param useShulker A boolean flag indicating whether to include useShulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include useBundles in the removal
   *                   process.
   *
   * @return The collection of items that won't fit in the inventory.
   */
  default Collection<I> giveItems(final Collection<I> items, final UUID identifier, final boolean useShulker, final boolean useBundles) {

    final Optional<U> inventory = inventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->giveItems(items, u, useShulker, useBundles)).orElse(items);
  }

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   *
   * @param stack     The stack, with the correct amount, to remove.
   * @param inventory The inventory to remove the net.tnemc.item stack from.
   *
   * @return The remaining amount of items to remove.
   */
  default int removeItem(final I stack, final U inventory) {

    return removeItem(stack, inventory, true, true);
  }

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   *
   * @param stack      The stack, with the correct amount, to remove.
   * @param inventory  The inventory to remove the net.tnemc.item stack from.
   * @param useShulker A boolean flag indicating whether to include useShulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include useBundles in the removal
   *                   process.
   *
   * @return The remaining amount of items to remove.
   */
  int removeItem(I stack, U inventory, final boolean useShulker, final boolean useBundles);

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   *
   * @param stack      The stack, with the correct amount, to remove.
   * @param identifier The identifier of the player to remove the net.tnemc.item stack from.
   *
   * @return The remaining amount of items to remove.
   */
  default int removeItem(final I stack, final UUID identifier) {

    final Optional<U> inventory = inventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->removeItem(stack, u, true, true)).orElseGet(stack::amount);
  }

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   *
   * @param stack      The stack, with the correct amount, to remove.
   * @param identifier The identifier of the player to remove the net.tnemc.item stack from.
   * @param useShulker A boolean flag indicating whether to include useShulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include useBundles in the removal
   *                   process.
   *
   * @return The remaining amount of items to remove.
   */
  default int removeItem(final I stack, final UUID identifier, final boolean useShulker, final boolean useBundles) {

    final Optional<U> inventory = inventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->removeItem(stack, u, useShulker, useBundles)).orElseGet(stack::amount);
  }

  /**
   * Used to locate an invetory for a UUID identifier.
   *
   * @param identifier The identifier to use for the search.
   * @param type       The inventory type to return.
   *
   * @return An optional containing the inventory if it works, otherwise false.
   */
  Optional<U> inventory(UUID identifier, InventoryType type);
}