package net.tnemc.item;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import java.util.Optional;

//not close to being done yet
public interface AbstractInventory<T, I> {

  /**
   * Used to get the platform's inventory object.
   * @return The platform's inventory object.
   */
  T inventory();

  boolean canFit(SerialItem<I> item);

  /**
   * Used to add an item to the inventory.
   * @param item The item to add to the inventory.
   * @param drop If the leftover items that won't fit in the inventory should be dropped or not.
   * @return If drop is set to true this is an empty optional, or if drop is false then this is an
   * optional containing the leftover items that wouldn't fit into the inventory.
   */
  Optional<SerialItem<I>> addItem(SerialItem<I> item, boolean drop);

  /**
   * Used to set a specific slot as a specific item.
   *
   * @param slot The slot number to set the item in.
   * @param item The item to set the slot as.
   * @param drop If this is true if there is an item in the slot already it is dropped.
   * @return If drop is set to true this is an empty optional, or if drop is false then this is an
   * optional containing the item that was in the slot before if applicable.
   */
  Optional<SerialItem<I>> setSlot(int slot, SerialItem<I> item, boolean drop);
}