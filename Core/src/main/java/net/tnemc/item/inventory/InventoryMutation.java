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


import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Describes the slot replacements produced by an inventory calculation.
 *
 * This class intentionally does not know how to apply itself to a platform
 * inventory. Committing a mutation belongs to the platform adapter.
 *
 * @param <T> platform-native item type
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public final class InventoryMutation<T> {

  private final Map<Integer, T> changes;

  public InventoryMutation(final Map<Integer, T> changes) {
    this.changes = Collections.unmodifiableMap(new LinkedHashMap<>(changes));
  }

  public Map<Integer, T> changes() {
    return changes;
  }

  public boolean empty() {
    return changes.isEmpty();
  }

  public int size() {
    return changes.size();
  }
}