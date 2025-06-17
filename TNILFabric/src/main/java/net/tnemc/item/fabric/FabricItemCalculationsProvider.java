package net.tnemc.item.fabric;
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

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.tnemc.item.InventoryType;
import net.tnemc.item.providers.CalculationsProvider;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * FabricItemCalculationsProvider
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class FabricItemCalculationsProvider implements CalculationsProvider<FabricItemStack, ItemStack, Inventory> {

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
  @Override
  public boolean drop(final Collection<FabricItemStack> left, final UUID player, final boolean setOwner) {

    return false;
  }

  /**
   * Removes all items that are equal to the stack from an inventory.
   *
   * @param stack     The stack to compare to for removal from the inventory.
   * @param inventory The inventory to remove the items from.
   *
   * @return The amount of items removed.
   */
  @Override
  public int removeAll(final FabricItemStack stack, final Inventory inventory) {

    return 0;
  }

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   *
   * @param stack     The stack to get a count of.
   * @param inventory The inventory to check.
   *
   * @return The total count of items in the inventory.
   */
  @Override
  public int count(final FabricItemStack stack, final Inventory inventory) {

    return 0;
  }

  /**
   * Takes a collection of items from an inventory.
   *
   * @param items     The collection of items to remove.
   * @param inventory The inventory to remove the items from.
   */
  @Override
  public void takeItems(final Collection<FabricItemStack> items, final Inventory inventory) {

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
  @Override
  public Collection<FabricItemStack> giveItems(final Collection<FabricItemStack> items, final Inventory inventory) {

    return List.of();
  }

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   *
   * @param stack     The stack, with the correct amount, to remove.
   * @param inventory The inventory to remove the net.tnemc.item stack from.
   *
   * @return The remaining amount of items to remove.
   */
  @Override
  public int removeItem(final FabricItemStack stack, final Inventory inventory) {

    return 0;
  }

  /**
   * Used to locate an invetory for a UUID identifier.
   *
   * @param identifier The identifier to use for the search.
   * @param type       The inventory type to return.
   *
   * @return An optional containing the inventory if it works, otherwise false.
   */
  @Override
  public Optional<Inventory> inventory(final UUID identifier, final InventoryType type) {

    return Optional.empty();
  }
}