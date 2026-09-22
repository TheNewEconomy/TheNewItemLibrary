package net.tnemc.sponge.inventory;

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
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class SpongeInventoryAdapter {

  public InventoryView<ItemStack> view(final Inventory inventory) {

    final List<ItemStack> detached = new ArrayList<>();
    for(final Inventory slot : inventory.slots()) {

      final ItemStack item = slot.peek();

      detached.add((item == null)? null : item.copy());
    }
    return new InventoryView<>(detached, inventory.capacity());
  }

  public void apply(final Inventory inventory, final InventoryMutation<ItemStack> mutation) {

    final List<Inventory> slots = new ArrayList<>();
    inventory.slots().forEach(slots::add);

    mutation.changes().forEach((index, item)->{

      final Inventory slot = slots.get(index);
      if(item == null || item.isEmpty()) {

        slot.clear();
      } else {

        slot.set(0, item.copy());
      }
    });
  }
}
