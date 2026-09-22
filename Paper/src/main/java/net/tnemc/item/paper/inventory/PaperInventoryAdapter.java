package net.tnemc.item.paper.inventory;

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


import net.tnemc.item.inventory.InventoryMutation;
import net.tnemc.item.inventory.InventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts Paper/Bukkit inventories to isolated TNIL inventory views and
 * commits calculated mutations back to the live inventory.
 */
public final class PaperInventoryAdapter {

  public InventoryView<ItemStack> view(final Inventory inventory) {

    final ItemStack[] contents = inventory.getStorageContents();
    final List<ItemStack> detached = new ArrayList<>(contents.length);
    for(final ItemStack item : contents) {

      detached.add((item == null)? null : item.clone());
    }

    return new InventoryView<>(detached, inventory.getMaxStackSize());
  }

  public void apply(final Inventory inventory, final InventoryMutation<ItemStack> mutation) {

    for(final var change : mutation.changes().entrySet()) {

      final ItemStack item = change.getValue();

      inventory.setItem(change.getKey(), (item == null)? null : item.clone());
    }
  }
}