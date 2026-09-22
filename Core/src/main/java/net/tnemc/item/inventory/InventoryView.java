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


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Isolated working representation of inventory storage.
 *
 * The platform adapter is responsible for supplying detached item values.
 * Calculations may modify slots in this view without touching the live
 * platform inventory.
 *
 * @param <T> platform-native item type
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public final class InventoryView<T> {

  private final List<InventorySlot<T>> slots;
  private final int maxStackSize;

  public InventoryView(final List<T> contents, final int maxStackSize) {

    if(maxStackSize <= 0) {

      throw new IllegalArgumentException("Maximum stack size must be positive.");
    }

    this.maxStackSize = maxStackSize;
    this.slots = new ArrayList<>(contents.size());

    for(int index = 0; index < contents.size(); index++) {

      slots.add(new InventorySlot<>(index, contents.get(index)));
    }
  }

  public int size() {
    return slots.size();
  }

  public int maxStackSize() {
    return maxStackSize;
  }

  public InventorySlot<T> slot(final int index) {
    return slots.get(index);
  }

  public List<InventorySlot<T>> slots() {
    return Collections.unmodifiableList(slots);
  }

  /**
   * Builds the minimal set of dirty slot replacements.
   */
  public InventoryMutation<T> mutation() {

    final Map<Integer, T> changes = new LinkedHashMap<>();
    for(final InventorySlot<T> slot : slots) {

      if(slot.dirty()) {

        changes.put(slot.index(), slot.item());
      }
    }
    return new InventoryMutation<>(changes);
  }
}