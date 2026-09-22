package net.tnemc.item.fabric.inventory;

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

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.tnemc.item.inventory.InventoryMutation;
import net.tnemc.item.inventory.InventoryView;

import java.util.ArrayList;
import java.util.List;

public final class FabricInventoryAdapter {

  public InventoryView<ItemStack> view(final Inventory inventory) {

    final List<ItemStack> detached = new ArrayList<>(inventory.size());
    for(int slot = 0; slot < inventory.size(); slot++) {

      detached.add(inventory.getStack(slot).copy());
    }
    return new InventoryView<>(detached, inventory.getMaxCountPerStack());
  }

  public void apply(final Inventory inventory, final InventoryMutation<ItemStack> mutation) {

    mutation.changes().forEach((slot, item)->inventory.setStack(slot, item == null ? ItemStack.EMPTY : item.copy()));
    inventory.markDirty();
  }
}