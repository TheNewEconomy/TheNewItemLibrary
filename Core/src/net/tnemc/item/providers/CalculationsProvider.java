package net.tnemc.item.providers;

import net.tnemc.item.AbstractItemStack;

import java.util.Collection;
import java.util.UUID;

public interface CalculationsProvider {

  /**
   * Removes all items that are equal to the stack from an inventory.
   * @param stack The stack to compare to for removal from the inventory.
   * @param inventory The inventory to remove the items from.
   */
  void removeAll(AbstractItemStack stack, Object inventory);
  /**
   * Removes all items that are equal to the stack from an inventory.
   * @param stack The stack to compare to for removal from the inventory.
   * @param identifier The identifier of the player to remove the items from.
   */
  void removeAll(AbstractItemStack stack, UUID identifier);

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   * @param stack The stack to get a count of.
   * @param inventory The inventory to check.
   * @return The total count of items in the inventory.
   */
  int count(AbstractItemStack stack, Object inventory);

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   * @param stack The stack to get a count of.
   * @param identifier The identifier of the player to check.
   * @return The total count of items in the inventory.
   */
  int count(AbstractItemStack stack, UUID identifier);

  /**
   * Takes a collection of items from an inventory.
   * @param items The collection of items to remove.
   * @param inventory The inventory to remove the items from.
   */
  void takeItems(Collection<AbstractItemStack> items, Object inventory);

  /**
   * Takes a collection of items from an inventory.
   * @param items The collection of items to remove.
   * @param identifier The identifier of the player to remove the items from.
   */
  void takeItems(Collection<AbstractItemStack> items, UUID identifier);

  /**
   * Adds a collection of net.tnemc.item stacks to an inventory, dropping them on the ground if it's a player inventory and overflow exists.
   * @param items The collection of items to add to the inventory.
   * @param inventory The inventory to add the collection of items to.
   */
  void giveItems(Collection<AbstractItemStack> items, Object inventory);

  /**
   * Adds a collection of net.tnemc.item stacks to an inventory, dropping them on the ground if it's a player inventory and overflow exists.
   * @param items The collection of items to add to the inventory.
   * @param identifier The identifier of the player to add the collection of items to.
   */
  void giveItems(Collection<AbstractItemStack> items, UUID identifier);

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   * @param stack The stack, with the correct amount, to remove.
   * @param inventory The inventory to remove the net.tnemc.item stack from.
   * @return The remaining amount of items to remove.
   */
  int removeItem(AbstractItemStack stack, Object inventory);

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   * @param stack The stack, with the correct amount, to remove.
   * @param identifier The identifier of the player to remove the net.tnemc.item stack from.
   * @return The remaining amount of items to remove.
   */
  int removeItem(AbstractItemStack stack, UUID identifier);

  /**
   * Checks to see if two net.tnemc.item stacks are equal.
   * @param original The original net.tnemc.item stack.
   * @param compare The net.tnemc.item stack you're comparing to the original.
   * @return True if the net.tnemc.item stacks are equal, otherwise false.
   */
  boolean itemsEqual(AbstractItemStack original, AbstractItemStack compare);

  /**
   * Checks to see if a material is a shulker box.
   * @param material The material to check.
   * @return True if it's a shulker box, otherwise false.
   */
  boolean isShulker(String material);
}