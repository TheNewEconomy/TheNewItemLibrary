package net.tnemc.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Map;

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
    for(int i = 0; i < inventory.getContents().length; i++) {
      final ItemStack item = inventory.getItem(i);

      if(stack != null && itemsEqual(stack, item)) inventory.setItem(i, null);
    }
  }

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   * @param stack The stack to get a count of.
   * @param inventory The inventory to check.
   * @return The total count of items in the inventory.
   */
  public static Integer getCount(ItemStack stack, Inventory inventory) {
    ItemStack compare = stack.clone();
    compare.setAmount(1);

    Integer value = 0;
    for(ItemStack item : inventory.getContents()) {
      if(itemsEqual(compare, item)) {
        value += item.getAmount();
      }
    }
    return value;
  }

  /**
   * Takes a collection of items from an inventory.
   * @param items The collection of items to remove.
   * @param inventory The inventory to remove the items from.
   */
  public static void takeItems(Collection<ItemStack> items, Inventory inventory) {
    for (ItemStack item : items) {
      removeItem(item, inventory);
    }
  }

  /**
   * Adds a collection of item stacks to an inventory, dropping them on the ground if it's a player inventory and overflow exists.
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
            Bukkit.getScheduler().runTask(plugin, () -> {
              try {
                entity.getWorld().dropItemNaturally(entity.getLocation(), i);
              } catch(Exception e) {
                //attempted to drop air/some crazy/stupid error.
              }
            });
          }
        }
      }
    }
  }

  /**
   * Removes an item stack with a specific amount from an inventory.
   * @param stack The stack, with the correct amount, to remove.
   * @param inventory The inventory to return the item stack from.
   * @return The remaining amount of items to remove.
   */
  public static Integer removeItem(ItemStack stack, Inventory inventory) {
    int left = stack.getAmount();

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
   * Checks to see if two item stacks are equal.
   * @param original The original item stack.
   * @param compare The item stack you're comparing to the original.
   * @return True if the item stacks are equal, otherwise false.
   */
  public static Boolean itemsEqual(ItemStack original, ItemStack compare) {
    if(compare == null) return false;
    ItemMeta originalMeta = original.getItemMeta();
    ItemMeta compareMeta = compare.getItemMeta();
    if(compare.hasItemMeta()) {
      if (compareMeta.hasDisplayName()) {
        if (!originalMeta.hasDisplayName()) return false;
        if (!originalMeta.getDisplayName().equalsIgnoreCase(compareMeta.getDisplayName())) return false;
      }

      if (compareMeta.hasLore()) {
        if (!originalMeta.hasLore()) return false;
        if (!originalMeta.getLore().containsAll(compareMeta.getLore())) return false;
      }

      if (compareMeta.hasEnchants()) {
        if (!originalMeta.hasEnchants()) return false;

        for (Map.Entry<Enchantment, Integer> entry : compare.getEnchantments().entrySet()) {
          if (!original.containsEnchantment(entry.getKey())) return false;
          if (original.getEnchantmentLevel(entry.getKey()) != entry.getValue()) return false;
        }
      }
    }

    if(isShulker(original.getType())) {
      if(originalMeta instanceof BlockStateMeta && compareMeta instanceof BlockStateMeta) {
        BlockStateMeta state = (BlockStateMeta) originalMeta;
        BlockStateMeta stateCompare = (BlockStateMeta) compareMeta;
        if (state.getBlockState() instanceof ShulkerBox && stateCompare.getBlockState() instanceof ShulkerBox) {
          ShulkerBox shulker = (ShulkerBox)state.getBlockState();
          ShulkerBox shulkerCompare = (ShulkerBox)stateCompare.getBlockState();

          for(int i = 0; i < shulker.getInventory().getSize(); i++) {
            final ItemStack stack = shulker.getInventory().getItem(i);
            if(stack != null) {
              if(!itemsEqual(stack, shulkerCompare.getInventory().getItem(i))) return false;
            }
          }
          return true;
        }
        return false;
      }
      return false;
    } else if(original.getType().equals(Material.WRITTEN_BOOK) ||
        original.getType().equals(Material.WRITABLE_BOOK)) {
      if(originalMeta instanceof BookMeta && compareMeta instanceof BookMeta) {
        return new SerialItem(original).serialize().equals(new SerialItem(compare).serialize());
      }
      return false;
    }
    if(!original.getType().equals(compare.getType())) return false;
    if(original.getDurability() != compare.getDurability()) return false;
    return true;
  }

  /**
   * Checks to see if a material is a shulker box.
   * @param material The material to check.
   * @return True if it's a shulker box, otherwise false.
   */
  public static boolean isShulker(Material material) {
    return material.equals(Material.WHITE_SHULKER_BOX) ||
        material.equals(Material.ORANGE_SHULKER_BOX) ||
        material.equals(Material.MAGENTA_SHULKER_BOX) ||
        material.equals(Material.LIGHT_BLUE_SHULKER_BOX) ||
        material.equals(Material.YELLOW_SHULKER_BOX) ||
        material.equals(Material.LIME_SHULKER_BOX) ||
        material.equals(Material.PINK_SHULKER_BOX) ||
        material.equals(Material.GRAY_SHULKER_BOX) ||
        material.equals(Material.LIGHT_GRAY_SHULKER_BOX) ||
        material.equals(Material.CYAN_SHULKER_BOX) ||
        material.equals(Material.PURPLE_SHULKER_BOX) ||
        material.equals(Material.BLUE_SHULKER_BOX) ||
        material.equals(Material.BROWN_SHULKER_BOX) ||
        material.equals(Material.GREEN_SHULKER_BOX) ||
        material.equals(Material.RED_SHULKER_BOX) ||
        material.equals(Material.BLACK_SHULKER_BOX);
  }
}