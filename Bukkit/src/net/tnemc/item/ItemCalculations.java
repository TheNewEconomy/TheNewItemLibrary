package net.tnemc.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * Created by Daniel on 6/3/2018.
 * <p>
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
public class ItemCalculations {

  /**
   * Removes all items that are equal to the stack from an inventory.
   * @param stack The stack to compare to for removal from the inventory.
   * @param inventory The inventory to remove the items from.
   */
  public static void removeAllItem(ItemStack stack, Inventory inventory) {
    Arrays.stream(inventory.getContents())
            .filter(Objects::nonNull)
            .filter(itemStack -> itemsEqual(stack, itemStack))
            .forEach(inventory::remove);
  }

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   * @param stack The stack to get a count of.
   * @param inventory The inventory to check.
   * @return The total count of items in the inventory.
   */
  public static int getCount(ItemStack stack, Inventory inventory) {
    ItemStack compare = stack.clone();
    compare.setAmount(1);
    return Arrays
            .stream(inventory.getContents())
            .filter(Objects::nonNull)
            .filter(itemStack -> itemsEqual(compare, itemStack))
            .mapToInt(ItemStack::getAmount)
            .sum();
  }

  /**
   * Takes a collection of items from an inventory.
   * @param items The collection of items to remove.
   * @param inventory The inventory to remove the items from.
   */
  public static void takeItems(Collection<ItemStack> items, Inventory inventory) {
    items.forEach(itemStack -> removeItem(itemStack, inventory));
  }

  /**
   * Adds a collection of net.tnemc.item stacks to an inventory, dropping them on the ground if it's a player inventory and overflow exists.
   * @param plugin The instance of your plugin class.
   * @param items The collection of items to add to the inventory.
   * @param inventory The inventory to add the collection of items to.
   */
  public static void giveItems(JavaPlugin plugin, Collection<ItemStack> items, Inventory inventory) {
    for(ItemStack item : items) {
      Map<Integer, ItemStack> left = inventory.addItem(item);

      if(left.size() > 0) {
        if(inventory instanceof PlayerInventory) {
          final HumanEntity entity = ((HumanEntity)inventory.getHolder());
          for (Map.Entry<Integer, ItemStack> entry : left.entrySet()) {
            final ItemStack i = entry.getValue();
            if (i == null || i.getType() == Material.AIR) {
              continue;
            }
            Bukkit.getScheduler().runTask(plugin, () -> {
              entity.getWorld().dropItemNaturally(entity.getLocation(), i);
            });
          }
        }
      }
    }
  }

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   * @param stack The stack, with the correct amount, to remove.
   * @param inventory The inventory to return the net.tnemc.item stack from.
   * @return The remaining amount of items to remove.
   */
  public static int removeItem(ItemStack stack, Inventory inventory) {
    int left = stack.clone().getAmount();

    for(int i = 0; i < inventory.getStorageContents().length; i++) {
      if(left <= 0) break;
      ItemStack item = inventory.getItem(i);
      if(item == null || !itemsEqual(stack, item)) continue;

      if(item.getAmount() <= left) {
        left -= item.getAmount();
        inventory.setItem(i, null);
      } else {
        item.setAmount(item.getAmount() - left);
        inventory.setItem(i, item);
        left = 0;
      }
    }

    if(left > 0 && inventory instanceof PlayerInventory) {
      final ItemStack helmet = ((PlayerInventory) inventory).getHelmet();
      if(helmet != null && helmet.isSimilar(stack)) {
        if(helmet.getAmount() <= left) {
          left -= helmet.getAmount();
          ((PlayerInventory) inventory).setHelmet(null);
        } else {
          helmet.setAmount(helmet.getAmount() - left);
          ((PlayerInventory) inventory).setHelmet(helmet);
          left = 0;
        }
      }

      if(left > 0) {
        final ItemStack hand = ((PlayerInventory) inventory).getItemInOffHand();

        if(hand != null && hand.isSimilar(stack)) {
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
   * Checks to see if two net.tnemc.item stacks are equal.
   * @param original The original net.tnemc.item stack.
   * @param compare The net.tnemc.item stack you're comparing to the original.
   * @return True if the net.tnemc.item stacks are equal, otherwise false.
   */
  public static boolean itemsEqual(ItemStack original, ItemStack compare) {
    if (compare == null) return false;
    ItemMeta originalMeta = original.getItemMeta();
    ItemMeta compareMeta = compare.getItemMeta();
    if (compare.hasItemMeta()) {
      if (isShulker(original.getType())) {
        if (originalMeta instanceof BlockStateMeta && compareMeta instanceof BlockStateMeta) {
          BlockStateMeta state = (BlockStateMeta) originalMeta;
          BlockStateMeta stateCompare = (BlockStateMeta) compareMeta;
          if (state.getBlockState() instanceof ShulkerBox && stateCompare.getBlockState() instanceof ShulkerBox) {
            ShulkerBox shulker = (ShulkerBox) state.getBlockState();
            ShulkerBox shulkerCompare = (ShulkerBox) stateCompare.getBlockState();

            for (int i = 0; i < shulker.getInventory().getSize(); i++) {
              final ItemStack stack = shulker.getInventory().getItem(i);
              if (stack != null) {
                if (!itemsEqual(stack, shulkerCompare.getInventory().getItem(i))) return false;
              }
            }
            return true;
          }
          return false;
        }
        return false;
      }
      return original.isSimilar(compare);
    }
    return false;
  }

  /**
   * Checks to see if a material is a shulker box.
   * @param material The material to check.
   * @return True if it's a shulker box, otherwise false.
   */
  public static boolean isShulker(Material material) {
    return material.name().contains("SHULKER_BOX");
  }
}
