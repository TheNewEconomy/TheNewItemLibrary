package net.tnemc.item.bukkit;

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


import net.tnemc.item.InventoryType;
import net.tnemc.item.inventory.InventorySlot;
import net.tnemc.item.inventory.InventoryView;
import net.tnemc.item.bukkit.inventory.BukkitInventoryAdapter;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.providers.CalculationsProvider;
import net.tnemc.item.providers.ItemProvider;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BundleMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Paper implementation of TNIL inventory calculations.
 *
 * Mutating operations work against an isolated InventoryView first. The live
 * inventory is changed only after the complete calculation succeeds.
 */
public class BukkitCalculationsProvider implements CalculationsProvider<BukkitItemStack, ItemStack, Inventory> {

  private final BukkitInventoryAdapter inventories = new BukkitInventoryAdapter();

  @Override
  public boolean drop(final Collection<BukkitItemStack> left, final UUID player, final boolean setOwner) {
    final Player playerObj = Bukkit.getPlayer(player);
    if(playerObj == null) {
      return false;
    }

    for(final BukkitItemStack stack : left) {
      if(setOwner && VersionUtil.isOneSixteen(BukkitItemPlatform.instance().version())) {
        final Item item = playerObj.getWorld().dropItemNaturally(
                playerObj.getLocation(), stack.provider().locale(stack));
        item.setOwner(player);
        continue;
      }

      playerObj.getWorld().dropItemNaturally(
              playerObj.getLocation(), stack.provider().locale(stack));
    }
    return true;
  }

  @Override
  public int removeAll(final BukkitItemStack stack, final Inventory inventory,
                       final boolean useShulker, final boolean useBundles) {
    final InventoryView<ItemStack> view = inventories.view(inventory);
    final int removed = removeAll(stack, view, useShulker, useBundles);
    inventories.apply(inventory, view.mutation());
    return removed;
  }

  private int removeAll(final BukkitItemStack stack, final InventoryView<ItemStack> view,
                        final boolean useShulker, final boolean useBundles) {
    final ItemProvider<ItemStack> provider = stack.provider();
    int amount = 0;

    for(final InventorySlot<ItemStack> slot : view.slots()) {
      final ItemStack item = slot.item();
      if(item == null) {
        continue;
      }

      if(provider.similar(stack, item)) {
        amount += item.getAmount();
        slot.item(null);
        continue;
      }

      final ItemStack changed = item.clone();
      boolean dirty = false;

      if(useShulker && changed.getItemMeta() instanceof final BlockStateMeta meta
         && meta.getBlockState() instanceof final ShulkerBox shulker) {
        final Inventory nested = shulker.getInventory();

        for(int nestedSlot = 0; nestedSlot < nested.getStorageContents().length; nestedSlot++) {
          final ItemStack nestedItem = nested.getItem(nestedSlot);
          if(nestedItem != null && provider.similar(stack, nestedItem)) {
            amount += nestedItem.getAmount();
            nested.setItem(nestedSlot, null);
            dirty = true;
          }
        }

        if(dirty) {
          meta.setBlockState(shulker);
          changed.setItemMeta(meta);
        }
      }

      if(useBundles && changed.getItemMeta() instanceof final BundleMeta bundle) {
        final List<ItemStack> contents = new ArrayList<>(bundle.getItems());
        final Iterator<ItemStack> iterator = contents.iterator();
        boolean bundleDirty = false;

        while(iterator.hasNext()) {
          final ItemStack nestedItem = iterator.next();
          if(nestedItem != null && provider.similar(stack, nestedItem)) {
            amount += nestedItem.getAmount();
            iterator.remove();
            bundleDirty = true;
          }
        }

        if(bundleDirty) {
          bundle.setItems(contents);
          changed.setItemMeta(bundle);
          dirty = true;
        }
      }

      if(dirty) {
        slot.item(changed);
      }
    }

    return amount;
  }

  @Override
  public int count(final BukkitItemStack stack, final Inventory inventory,
                   final boolean useShulker, final boolean useBundles) {
    final ItemProvider<ItemStack> provider = stack.provider();
    int amount = 0;

    for(final ItemStack item : inventory.getStorageContents()) {
      if(item == null) {
        continue;
      }

      if(provider.similar(stack, item)) {
        amount += item.getAmount();
      }

      if(useShulker && item.getItemMeta() instanceof final BlockStateMeta meta
         && meta.getBlockState() instanceof final ShulkerBox shulker) {
        for(final ItemStack nested : shulker.getInventory().getStorageContents()) {
          if(nested != null && provider.similar(stack, nested)) {
            amount += nested.getAmount();
          }
        }
      }

      if(useBundles && item.getItemMeta() instanceof final BundleMeta bundle) {
        for(final ItemStack nested : bundle.getItems()) {
          if(nested != null && provider.similar(stack, nested)) {
            amount += nested.getAmount();
          }
        }
      }
    }

    return amount;
  }

  @Override
  public void takeItems(final Collection<BukkitItemStack> items, final Inventory inventory,
                        final boolean useShulker, final boolean useBundles) {
    final InventoryView<ItemStack> view = inventories.view(inventory);

    for(final BukkitItemStack item : items) {
      if(item != null) {
        removeItem(item, view, useShulker, useBundles);
      }
    }

    inventories.apply(inventory, view.mutation());
  }

  @Override
  public Collection<BukkitItemStack> giveItems(final Collection<BukkitItemStack> items,
                                               final Inventory inventory,
                                               final boolean useShulker,
                                               final boolean useBundles) {
    final InventoryView<ItemStack> view = inventories.view(inventory);
    final Collection<BukkitItemStack> leftOver = new ArrayList<>();

    for(final BukkitItemStack item : items) {
      if(item == null) {
        continue;
      }

      final ItemStack insertion = item.provider().locale(item, item.amount());
      final int remaining = add(view, insertion);

      if(remaining > 0) {
        final ItemStack remainder = insertion.clone();
        remainder.setAmount(remaining);
        leftOver.add(new BukkitItemStack().of(remainder));
      }
    }

    inventories.apply(inventory, view.mutation());
    return leftOver;
  }

  private int add(final InventoryView<ItemStack> view, final ItemStack insertion) {
    int left = insertion.getAmount();

    // Fill compatible existing stacks first.
    for(final InventorySlot<ItemStack> slot : view.slots()) {
      if(left <= 0) {
        break;
      }

      final ItemStack current = slot.item();
      if(current == null || !current.isSimilar(insertion)) {
        continue;
      }

      final int maximum = Math.min(view.maxStackSize(), current.getMaxStackSize());
      final int space = maximum - current.getAmount();
      if(space <= 0) {
        continue;
      }

      final int moved = Math.min(space, left);
      final ItemStack changed = current.clone();
      changed.setAmount(current.getAmount() + moved);
      slot.item(changed);
      left -= moved;
    }

    // Then occupy empty storage slots.
    for(final InventorySlot<ItemStack> slot : view.slots()) {
      if(left <= 0) {
        break;
      }
      if(slot.item() != null) {
        continue;
      }

      final int maximum = Math.min(view.maxStackSize(), insertion.getMaxStackSize());
      final int moved = Math.min(maximum, left);
      final ItemStack changed = insertion.clone();
      changed.setAmount(moved);
      slot.item(changed);
      left -= moved;
    }

    return left;
  }

  @Override
  public int removeItem(final BukkitItemStack stack, final Inventory inventory,
                        final boolean useShulker, final boolean useBundles) {

    final InventoryView<ItemStack> view = inventories.view(inventory);
    final int left = removeItem(stack, view, useShulker, useBundles);
    inventories.apply(inventory, view.mutation());
    return left;
  }

  private int removeItem(final BukkitItemStack stack, final InventoryView<ItemStack> view,
                         final boolean useShulker, final boolean useBundles) {
    int left = stack.amount();
    final ItemProvider<ItemStack> provider = stack.provider();

    for(final InventorySlot<ItemStack> slot : view.slots()) {
      if(left <= 0) {
        break;
      }

      final ItemStack item = slot.item();
      if(item == null) {
        continue;
      }

      if(provider.similar(stack, item)) {
        final int removed = Math.min(left, item.getAmount());
        left -= removed;

        if(removed == item.getAmount()) {
          slot.item(null);
          continue;
        }

        final ItemStack changed = item.clone();
        changed.setAmount(item.getAmount() - removed);
        slot.item(changed);
        continue;
      }

      final ItemStack changed = item.clone();
      boolean dirty = false;

      if(useShulker && changed.getItemMeta() instanceof final BlockStateMeta meta
         && meta.getBlockState() instanceof final ShulkerBox shulker) {
        final Inventory nested = shulker.getInventory();

        for(int nestedSlot = 0; nestedSlot < nested.getStorageContents().length && left > 0; nestedSlot++) {
          final ItemStack nestedItem = nested.getItem(nestedSlot);
          if(nestedItem == null || !provider.similar(stack, nestedItem)) {
            continue;
          }

          final int removed = Math.min(left, nestedItem.getAmount());
          left -= removed;

          if(removed == nestedItem.getAmount()) {
            nested.setItem(nestedSlot, null);
          } else {
            final ItemStack nestedChanged = nestedItem.clone();
            nestedChanged.setAmount(nestedItem.getAmount() - removed);
            nested.setItem(nestedSlot, nestedChanged);
          }
          dirty = true;
        }

        if(dirty) {
          meta.setBlockState(shulker);
          changed.setItemMeta(meta);
        }
      }

      if(left > 0 && useBundles && changed.getItemMeta() instanceof final BundleMeta bundle) {
        final List<ItemStack> contents = new ArrayList<>(bundle.getItems());
        final Iterator<ItemStack> iterator = contents.iterator();
        boolean bundleDirty = false;

        while(iterator.hasNext() && left > 0) {
          final ItemStack nestedItem = iterator.next();
          if(nestedItem == null || !provider.similar(stack, nestedItem)) {
            continue;
          }

          final int removed = Math.min(left, nestedItem.getAmount());
          left -= removed;

          if(removed == nestedItem.getAmount()) {
            iterator.remove();
          } else {
            nestedItem.setAmount(nestedItem.getAmount() - removed);
          }
          bundleDirty = true;
        }

        if(bundleDirty) {
          bundle.setItems(contents);
          changed.setItemMeta(bundle);
          dirty = true;
        }
      }

      if(dirty) {
        slot.item(changed);
      }
    }

    return left;
  }

  @Override
  public Optional<Inventory> inventory(final UUID identifier, final InventoryType type) {
    final OfflinePlayer player = Bukkit.getOfflinePlayer(identifier);

    if(player.isOnline() && player.getPlayer() != null) {
      if(type.equals(InventoryType.ENDER_CHEST)) {
        return Optional.of(player.getPlayer().getEnderChest());
      }
      return Optional.of(player.getPlayer().getInventory());
    }

    return Optional.empty();
  }
}
