package net.tnemc.item.bukkit;

/*
 * The New Economy Minecraft Server Plugin
 *
 * Copyright (C) 2022 Daniel "creatorfromhell" Vidmar
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
import net.tnemc.item.SerialItem;
import net.tnemc.item.data.ItemStorageData;
import net.tnemc.item.providers.CalculationsProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
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
   * Used to drop items near a player.
   *
   * @param left A Collection containing the items to drop.
   * @param player The UUID of the player to drop the items near.
   *
   * @return True if the items were successfully dropped, otherwise false.
   */
  @Override
  public boolean drop(Collection<BukkitItemStack> left, UUID player) {
    final Player playerObj = Bukkit.getPlayer(player);

    if(playerObj != null) {
      for(BukkitItemStack stack : left) {
        Objects.requireNonNull(playerObj.getWorld()).dropItemNaturally(playerObj.getLocation(), stack.locale());
      }
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
  public int removeAll(BukkitItemStack stack, Inventory inventory) {
    final ItemStack compare = stack.locale().clone();
    compare.setAmount(1);

    int amount = 0;

    for(int i = 0; i < inventory.getStorageContents().length; i++) {
      ItemStack item = inventory.getItem(i);
      if(item != null) {
        final boolean equal = itemsEqual(BukkitItemStack.locale(compare),
                                         BukkitItemStack.locale(item)
        );

        if(equal) {
          amount += item.getAmount();
          inventory.setItem(i, null);
        }
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
  public int count(BukkitItemStack stack, Inventory inventory) {
    final ItemStack compare = stack.locale().clone();
    compare.setAmount(1);

    int amount = 0;

    for(ItemStack itemStack : inventory.getContents()) {
      if(itemStack != null) {
        final BukkitItemStack locale = BukkitItemStack.locale(itemStack);
        final boolean equal = itemsEqual(BukkitItemStack.locale(compare), locale);

        if(locale.data().isPresent()) {
          if(locale.data().get() instanceof ItemStorageData) {
            for(Object obj : ((ItemStorageData)locale.data().get()).getItems().entrySet()) {
              final Map.Entry<Integer, SerialItem> entry = ((Map.Entry<Integer, SerialItem>)obj);

              if(itemsEqual(BukkitItemStack.locale(compare), new BukkitItemStack().of(entry.getValue()))) {
                amount += entry.getValue().getStack().amount();
              }
            }
          }
        }

        if(equal) {
          amount += itemStack.getAmount();
        }
      }
    }
    return amount;
  }

  /**
   * Takes a collection of items from an inventory.
   * @param items The collection of items to remove.
   * @param inventory The inventory to remove the items from.
   */
  public void takeItems(Collection<BukkitItemStack> items, Inventory inventory) {
    items.forEach(itemStack -> removeItem(itemStack, inventory));
  }

  /**
   * Adds a collection of net.tnemc.item stacks to an inventory, dropping them on the ground if it's a player inventory and overflow exists.
   * @param items The collection of items to add to the inventory.
   * @param inventory The inventory to add the collection of items to.
   */
  public Collection<BukkitItemStack> giveItems(Collection<BukkitItemStack> items, Inventory inventory) {
    Collection<BukkitItemStack> leftOver = new ArrayList<>();

    for(BukkitItemStack item : items) {
      Map<Integer, ItemStack> left = inventory.addItem(item.locale());

      if(left.size() > 0 && inventory instanceof PlayerInventory) {
        for(Map.Entry<Integer, ItemStack> entry : left.entrySet()) {
          final ItemStack i = entry.getValue();
          if(i == null || i.getType() == Material.AIR) {
            continue;
          }
          leftOver.add(BukkitItemStack.locale(i));
        }
      }
    }
    return leftOver;
  }

  /**
   * Removes an ItemStack with a specific amount from an inventory.
   * @param stack The stack, with the correct amount, to remove.
   * @param inventory The inventory to return the net.tnemc.item stack from.
   * @return The remaining amount of items to remove.
   */
  @Override
  public int removeItem(BukkitItemStack stack, Inventory inventory) {
    int left = stack.locale().clone().getAmount();

    for(int i = 0; i < inventory.getStorageContents().length; i++) {
      if(left <= 0) break;
      ItemStack item = inventory.getItem(i);
      if(item == null || !itemsEqual(stack, BukkitItemStack.locale(item))) continue;

      if(item.getAmount() <= left) {
        left -= item.getAmount();
        inventory.setItem(i, null);
      } else {
        item.setAmount(item.getAmount() - left);
        inventory.setItem(i, item);
        left = 0;
      }
    }

    //Helmet check, because Bukkit includes this in the count but not in removal.
    if(left > 0 && inventory instanceof PlayerInventory) {
      final ItemStack helmet = ((PlayerInventory) inventory).getHelmet();
      if(helmet != null && helmet.isSimilar(stack.locale())) {
        if(helmet.getAmount() <= left) {
          left -= helmet.getAmount();
          ((PlayerInventory) inventory).setHelmet(null);
        } else {
          helmet.setAmount(helmet.getAmount() - left);
          ((PlayerInventory) inventory).setHelmet(helmet);
          left = 0;
        }
      }

      //Off-hand check, because Bukkit includes this in the count but not in removal.
      if(left > 0) {
        final ItemStack hand = ((PlayerInventory) inventory).getItemInOffHand();

        if(hand.isSimilar(stack.locale())) {
          if (hand.getAmount() <= left) {
            left -= hand.getAmount();
            ((PlayerInventory) inventory).setItemInOffHand(null);
          } else {
            hand.setAmount(hand.getAmount() - left);
            ((PlayerInventory) inventory).setItemInOffHand(hand);
            left = 0;
          }
        }
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
  public Optional<Inventory> getInventory(UUID identifier, InventoryType type) {
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
