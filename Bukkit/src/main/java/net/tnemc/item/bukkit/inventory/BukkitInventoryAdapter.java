package net.tnemc.item.bukkit.inventory;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2026 Daniel "creatorfromhell" Vidmar
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 */

import net.tnemc.item.inventory.InventoryMutation;
import net.tnemc.item.inventory.InventoryView;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class BukkitInventoryAdapter {

  public InventoryView<ItemStack> view(final Inventory inventory) {

    final ItemStack[] contents = inventory.getStorageContents();
    final List<ItemStack> detached = new ArrayList<>(contents.length);
    for(final ItemStack item : contents) {

      detached.add((item == null)? null : item.clone());
    }
    return new InventoryView<>(detached, inventory.getMaxStackSize());
  }

  public void apply(final Inventory inventory, final InventoryMutation<ItemStack> mutation) {
    mutation.changes().forEach((slot, item)->inventory.setItem(slot, (item == null)? null : item.clone()));
  }
}
