package net.tnemc.item.inventory;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
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


/**
 * Represents a mutable slot inside an isolated inventory view.
 *
 * @param <T> platform-native item type
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public final class InventorySlot<T> {

  private final int index;
  private T item;
  private boolean dirty;

  public InventorySlot(final int index, final T item) {

    if(index < 0) {

      throw new IllegalArgumentException("Inventory slot index cannot be negative.");
    }
    this.index = index;
    this.item = item;
  }

  public int index() {
    return index;
  }

  public T item() {
    return item;
  }

  /**
   * Replaces the working value and marks this slot for commit.
   */
  public void item(final T item) {
    this.item = item;
    this.dirty = true;
  }

  public boolean dirty() {
    return dirty;
  }
}