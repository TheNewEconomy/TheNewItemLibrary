package net.tnemc.item.bukkit;

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
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.providers.CalculationsProvider;
import net.tnemc.item.providers.ItemProvider;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Container;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a Bukkit implementation of the {@link CalculationsProvider}.
 *
 * @author creatorfromhell
 * @since 0.1.5.0
 */
public class BukkitCalculationsProvider implements CalculationsProvider<BukkitItemStack, ItemStack, Inventory> {

  /**
   * Removes items from a collection based on certain criteria.
   *
   * @param left      The collection of items from which to remove items.
   * @param player    The UUID of the player associated with the removal operation.
   * @param setOwner  Indicates whether to set the owner of the removed items.(supports spigot/paper 1.16.5+)
   *
   * @return True if the removal operation was successful, false otherwise.
   */
  @Override
  public boolean drop(final Collection<BukkitItemStack> left, final UUID player, final boolean setOwner) {

    final Player playerObj = Bukkit.getPlayer(player);
    if(playerObj == null) {
      return false;
    }

    for(final BukkitItemStack stack : left) {

      if(setOwner && VersionUtil.isOneSixteen(BukkitItemPlatform.instance().version())) {

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
   * @param stack     The stack to compare to for removal from the inventory.
   * @param inventory The inventory to remove the items from.
   */
  @Override
  public int removeAll(final BukkitItemStack stack, final Inventory inventory) {

    final ItemStack compare = stack.provider().locale(stack).clone();
    compare.setAmount(1);

    int amount = 0;
    final BukkitItemStack comp = new BukkitItemStack().of(compare);

    for(int i = 0; i < inventory.getStorageContents().length; i++) {

      final ItemStack item = inventory.getItem(i);
      if(item == null) {
        continue;
      }

      if(itemsEqual(comp, item)) {
        amount += item.getAmount();
        inventory.setItem(i, null);
        continue;
      }

      if(item.getItemMeta() instanceof final BlockStateMeta meta && meta.getBlockState() instanceof final Container container) {

        final Inventory containerInventory = container.getInventory();
        for(int ci = 0; ci < containerInventory.getSize(); ci++) {

          final ItemStack containerStack = inventory.getItem(ci);
          if(containerStack == null) {

            continue;
          }

          if(itemsEqual(comp, containerStack)) {

            amount += item.getAmount();
            inventory.setItem(ci, null);
          }
        }
        container.update(true);
        meta.setBlockState(container);
        item.setItemMeta(meta);
        inventory.setItem(i, item);
      }
    }
    return amount;
  }

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   *
   * @param stack     The stack to get a count of.
   * @param inventory The inventory to check.
   *
   * @return The total count of items in the inventory.
   */
  @Override
  public int count(final BukkitItemStack stack, final Inventory inventory) {

    final ItemStack compare = stack.provider().locale(stack).clone();
    compare.setAmount(1);

    //TODO: make this more efficient
    final BukkitItemStack comp = new BukkitItemStack().of(compare);
    int amount = 0;

    for(final ItemStack item : inventory.getStorageContents()) {

      if(item == null) {
        continue;
      }

      if(itemsEqual(comp, item)) {

        amount += item.getAmount();
      }

      if(item.getItemMeta() instanceof final BlockStateMeta meta && meta.getBlockState() instanceof final Container container) {

        final Inventory containerInventory = container.getInventory();
        if(containerInventory.isEmpty()) {

          continue;
        }

        for(int ci = 0; ci < containerInventory.getSize(); ci++) {

          final ItemStack containerStack = containerInventory.getItem(ci);
          if(containerStack == null) {

            continue;
          }

          if(itemsEqual(comp, containerStack)) {

            amount += containerStack.getAmount();
          }
        }
      }
    }
    return amount;
  }

  /**
   * Takes a collection of items from an inventory.
   *
   * @param items     The collection of items to remove.
   * @param inventory The inventory to remove the items from.
   */
  @Override
  public void takeItems(final Collection<BukkitItemStack> items, final Inventory inventory) {

    items.forEach(itemStack->removeItem(itemStack, inventory));
  }

  /**
   * Adds a collection of net.tnemc.item stacks to an inventory, dropping them on the ground if it's
   * a player inventory and overflow exists.
   *
   * @param items     The collection of items to add to the inventory.
   * @param inventory The inventory to add the collection of items to.
   */
  @Override
  public Collection<BukkitItemStack> giveItems(final Collection<BukkitItemStack> items, final Inventory inventory) {

    final Collection<BukkitItemStack> leftOver = new ArrayList<>();

    for(final BukkitItemStack item : items) {

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

        leftOver.add(new BukkitItemStack().of(i));
      }
    }
    return leftOver;
  }

  /**
   * Removes an ItemStack with a specific amount from an inventory.
   *
   * @param stack     The stack, with the correct amount, to remove.
   * @param inventory The inventory to return the net.tnemc.item stack from.
   *
   * @return The remaining amount of items to remove.
   */
  @Override
  public int removeItem(final BukkitItemStack stack, final Inventory inventory) {

    int left = stack.provider().locale(stack).clone().getAmount();

    final ItemStack compare = stack.provider().locale(stack).clone();
    compare.setAmount(1);

    System.out.println("Calc removeItem: Left: " + left);

    //TODO: improve this

    final BukkitItemStack comp = new BukkitItemStack().of(compare);
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

      if(item.getItemMeta() instanceof final BlockStateMeta meta && meta.getBlockState() instanceof final Container container) {

        final Inventory containerInventory = container.getInventory();
        for(int ci = 0; ci < containerInventory.getSize(); ci++) {

          if(left <= 0) break;

          final ItemStack containerStack = inventory.getItem(ci);
          if(containerStack == null) {

            continue;
          }

          if(!itemsEqual(comp, containerStack)) {
            continue;
          }

          if(item.getAmount() > left) {

            item.setAmount(item.getAmount() - left);
            containerInventory.setItem(ci, item);
            left = 0;
            break;
          }

          if(item.getAmount() == left) {
            containerInventory.setItem(ci, null);
            left = 0;
            break;
          }

          left -= item.getAmount();
          containerInventory.setItem(ci, null);
        }
        container.update(true);
        meta.setBlockState(container);
        item.setItemMeta(meta);
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