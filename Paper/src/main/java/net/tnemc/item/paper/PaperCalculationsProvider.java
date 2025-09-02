package net.tnemc.item.paper;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2024 Daniel "creatorfromhell" Vidmar
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
import net.tnemc.item.paper.platform.PaperItemPlatform;
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
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a Bukkit implementation of the {@link CalculationsProvider}.
 *
 * @author creatorfromhell
 * @since 0.1.5.0
 */
public class PaperCalculationsProvider implements CalculationsProvider<PaperItemStack, ItemStack, Inventory> {

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
  public boolean drop(final Collection<PaperItemStack> left, final UUID player, final boolean setOwner) {

    final Player playerObj = Bukkit.getPlayer(player);
    if(playerObj == null) {
      return false;
    }

    for(final PaperItemStack stack : left) {

      if(setOwner && VersionUtil.isOneSixteen(PaperItemPlatform.instance().version())) {

        final Item it = playerObj.getWorld().dropItemNaturally(playerObj.getLocation(), stack.provider().locale(stack));
        it.setOwner(player);
        continue;
      }

      playerObj.getWorld().dropItemNaturally(playerObj.getLocation(), stack.provider().locale(stack));
    }
    return false;
  }

  /**
   * Removes all items that are equal to the stack from an inventory.
   *
   * @param stack      The stack to compare to for removal from the inventory.
   * @param inventory  The inventory to remove the items from.
   * @param useShulker A boolean flag indicating whether to include shulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include bundles in the removal process.
   */
  @Override
  public int removeAll(final PaperItemStack stack, final Inventory inventory, final boolean useShulker, final boolean useBundles) {

    final ItemStack compare = stack.provider().locale(stack).clone();
    compare.setAmount(1);

    int amount = 0;
    final ItemProvider<ItemStack> provider = stack.provider();

    for(int i = 0; i < inventory.getStorageContents().length; i++) {

      final ItemStack item = inventory.getItem(i);
      if(item == null) {
        continue;
      }

      if(provider.similar(stack, item)) {
        amount += item.getAmount();
        inventory.setItem(i, null);
        continue;
      }

      if(useShulker && item.getItemMeta() instanceof final BlockStateMeta meta && meta.getBlockState() instanceof final ShulkerBox shulker) {

        final Inventory shulkerInventory = shulker.getInventory();
        for(int shulkerSlot = 0; shulkerSlot < shulkerInventory.getStorageContents().length; shulkerSlot++) {

          final ItemStack shulkerStack = shulkerInventory.getItem(shulkerSlot);
          if(shulkerStack == null) {

            continue;
          }

          if(!provider.similar(stack, shulkerStack)) {
            continue;
          }

          amount += item.getAmount();
          shulkerInventory.setItem(shulkerSlot, null);
        }

        shulker.update(true);
        meta.setBlockState(shulker);
        item.setItemMeta(meta);
        inventory.setItem(i, item);
      }

      if(useBundles && item.getItemMeta() instanceof final BundleMeta bundle) {

        final List<ItemStack> items = new ArrayList<>(bundle.getItems());
        final Iterator<ItemStack> it = items.iterator();
        while(it.hasNext()) {

          final ItemStack bundleStack = it.next();
          if(bundleStack == null) {

            continue;
          }

          if(provider.similar(stack, bundleStack)) {

            amount += item.getAmount();
            it.remove();
          }
        }

        bundle.setItems(items);
        item.setItemMeta(bundle);
        inventory.setItem(i, item);
      }
    }
    return amount;
  }

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   *
   * @param stack      The stack to get a count of.
   * @param inventory  The inventory to check.
   * @param useShulker A boolean flag indicating whether to include shulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include bundles in the removal process.
   *
   * @return The total count of items in the inventory.
   */
  @Override
  public int count(final PaperItemStack stack, final Inventory inventory, final boolean useShulker, final boolean useBundles) {

    final ItemStack compare = stack.provider().locale(stack).clone();
    compare.setAmount(1);

    //TODO: make this more efficient
    final ItemProvider<ItemStack> provider = stack.provider();
    int amount = 0;

    for(final ItemStack item : inventory.getStorageContents()) {

      if(item == null) {
        continue;
      }

      if(provider.similar(stack, item)) {

        amount += item.getAmount();
      }

      if(useShulker && item.getItemMeta() instanceof final BlockStateMeta meta && meta.getBlockState() instanceof final ShulkerBox shulker) {

        final Inventory shulkerInventory = shulker.getInventory();
        if(shulkerInventory.isEmpty()) {

          continue;
        }

        for(int ci = 0; ci < shulkerInventory.getStorageContents().length; ci++) {

          final ItemStack shulkerStack = shulkerInventory.getItem(ci);
          if(shulkerStack == null) {

            continue;
          }

          if(provider.similar(stack, shulkerStack)) {

            amount += shulkerStack.getAmount();
          }
        }
      }

      if(useBundles && item.getItemMeta() instanceof final BundleMeta bundle) {

        for(final ItemStack bundleItem : bundle.getItems()) {

          if(bundleItem == null) {

            continue;
          }

          if(provider.similar(stack, bundleItem)) {

            amount += bundleItem.getAmount();
          }
        }
      }
    }
    return amount;
  }

  /**
   * Takes a collection of items from an inventory.
   *
   * @param items      The collection of items to remove.
   * @param inventory  The inventory to remove the items from.
   * @param useShulker A boolean flag indicating whether to include shulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include bundles in the removal process.
   */
  @Override
  public void takeItems(final Collection<PaperItemStack> items, final Inventory inventory, final boolean useShulker, final boolean useBundles) {

    items.forEach(itemStack->removeItem(itemStack, inventory, useShulker, useBundles));
  }

  /**
   * Adds a collection of net.tnemc.item stacks to an inventory, dropping them on the ground if it's
   * a player inventory and overflow exists.
   *
   * @param items      The collection of items to add to the inventory.
   * @param inventory  The inventory to add the collection of items to.
   * @param useShulker A boolean flag indicating whether to include shulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include bundles in the removal process.
   */
  @Override
  public Collection<PaperItemStack> giveItems(final Collection<PaperItemStack> items, final Inventory inventory, final boolean useShulker, final boolean useBundles) {

    final Collection<PaperItemStack> leftOver = new ArrayList<>();

    for(final PaperItemStack item : items) {

      if(item == null) {
        continue;
      }

      final Map<Integer, ItemStack> left = inventory.addItem(item.provider().locale(item, item.amount()));
      if(left.isEmpty()) {
        continue;
      }

      for(final Map.Entry<Integer, ItemStack> entry : left.entrySet()) {
        final ItemStack i = entry.getValue();

        if(i == null || i.getType() == Material.AIR) {
          continue;
        }

        leftOver.add(new PaperItemStack().of(i));
      }
    }
    return leftOver;
  }

  /**
   * Removes an ItemStack with a specific amount from an inventory.
   *
   * @param stack      The stack, with the correct amount, to remove.
   * @param inventory  The inventory to return the net.tnemc.item stack from.
   * @param useShulker A boolean flag indicating whether to include shulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include bundles in the removal process.
   *
   * @return The remaining amount of items to remove.
   */
  @Override
  public int removeItem(final PaperItemStack stack, final Inventory inventory, final boolean useShulker, final boolean useBundles) {

    int left = stack.provider().locale(stack).clone().getAmount();

    final ItemStack compare = stack.provider().locale(stack).clone();
    compare.setAmount(1);

    //TODO: improve this

    final ItemProvider<ItemStack> provider = stack.provider();

    for(int i = 0; i < inventory.getStorageContents().length; i++) {
      if(left <= 0) break;

      final ItemStack item = inventory.getItem(i);

      if(item == null) continue;

      if(provider.similar(stack, item)) {

        if(item.getAmount() > left) {
          item.setAmount(item.getAmount() - left);
          inventory.setItem(i, item);
          left = 0;
          break;
        }

        if(item.getAmount() == left) {
          inventory.setItem(i, null);
          left = 0;
          break;
        }

        left -= item.getAmount();
        inventory.setItem(i, null);
      }

      if(useShulker && item.getItemMeta() instanceof final BlockStateMeta meta && meta.getBlockState() instanceof final ShulkerBox shulker) {


        final Inventory shulkerInventory = shulker.getInventory();
        for(int shulkerSlot = 0; shulkerSlot < shulkerInventory.getStorageContents().length; shulkerSlot++) {

          if(left <= 0) break;

          final ItemStack shulkerStack = shulkerInventory.getItem(shulkerSlot);
          if(shulkerStack == null) {

            continue;
          }

          if(!provider.similar(stack, shulkerStack)) {
            continue;
          }

          if(shulkerStack.getAmount() > left) {


            shulkerStack.setAmount(shulkerStack.getAmount() - left);
            shulkerInventory.setItem(shulkerSlot, shulkerStack);
            left = 0;
            break;
          }

          if(shulkerStack.getAmount() == left) {
            shulkerInventory.setItem(shulkerSlot, null);
            left = 0;
            break;
          }

          left -= shulkerStack.getAmount();
          shulkerInventory.setItem(shulkerSlot, null);
        }

        shulker.update(true);
        meta.setBlockState(shulker);
        item.setItemMeta(meta);
        inventory.setItem(i, item);
      }

      if(useBundles && item.getItemMeta() instanceof final BundleMeta bundle) {

        final List<ItemStack> items = new ArrayList<>(bundle.getItems());
        final Iterator<ItemStack> it = items.iterator();
        while(it.hasNext()) {

          final ItemStack bundleStack = it.next();
          if(bundleStack == null) {

            continue;
          }

          if(!provider.similar(stack, bundleStack)) {
            continue;
          }

          if(bundleStack.getAmount() > left) {


            bundleStack.setAmount(bundleStack.getAmount() - left);
            left = 0;
            break;
          }

          if(bundleStack.getAmount() == left) {
            it.remove();
            left = 0;
            break;
          }

          left -= bundleStack.getAmount();
          it.remove();
        }

        bundle.setItems(items);
        item.setItemMeta(bundle);
        inventory.setItem(i, item);
      }
    }
    return left;
  }

  /**
   * Used to locate an inventory for a UUID identifier.
   *
   * @param identifier The identifier to use for the search.
   * @param type       The inventory type to return.
   *
   * @return An optional containing the inventory if it works, otherwise false.
   */
  @Override
  public Optional<Inventory> inventory(final UUID identifier, final InventoryType type) {

    final OfflinePlayer player = Bukkit.getOfflinePlayer(identifier);
    if(player.isOnline() && player.getPlayer() != null) {

      if(type.equals(InventoryType.ENDER_CHEST)) {
        return Optional.of(player.getPlayer().getEnderChest());
      } else {
        return Optional.of(player.getPlayer().getInventory());
      }
    }
    return Optional.empty();
  }
}