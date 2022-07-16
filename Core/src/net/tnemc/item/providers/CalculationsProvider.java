package net.tnemc.item.providers;

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.InventoryType;
import net.tnemc.item.SerialItem;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.ItemStorageData;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a provider that is utilized for item-based calculations and comparisons.
 * @param <T> The implementation's instance of {@link AbstractItemStack}
 * @param <S> The implementation's instance of item stacks.
 * @param <U> The implementation's instace of inventories.
 *
 * @author creatorfromhell
 * @since 0.1.5.0
 */
public interface CalculationsProvider<T extends AbstractItemStack<S>, S, U> {

  /**
   * Removes all items that are equal to the stack from an inventory.
   * @param stack The stack to compare to for removal from the inventory.
   * @param inventory The inventory to remove the items from.
   *
   * @return The amount of items removed.
   */
  int removeAll(T stack, U inventory);

  /**
   * Removes all items that are equal to the stack from an inventory.
   * @param stack The stack to compare to for removal from the inventory.
   * @param identifier The identifier of the player to remove the items from.
   *
   * @return The amount of items removed.
   */
  default int removeAll(T stack, UUID identifier) {

    final Optional<U> inventory = getInventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->removeAll(stack, u)).orElse(0);
  }

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   * @param stack The stack to get a count of.
   * @param inventory The inventory to check.
   * @return The total count of items in the inventory.
   */
  int count(T stack, U inventory);

  /**
   * Returns a count of items equal to the specific stack in an inventory.
   * @param stack The stack to get a count of.
   * @param identifier The identifier of the player to check.
   * @return The total count of items in the inventory.
   */
  default int count(T stack, UUID identifier) {

    final Optional<U> inventory = getInventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->count(stack, u)).orElse(0);
  }

  /**
   * Takes a collection of items from an inventory.
   * @param items The collection of items to remove.
   * @param inventory The inventory to remove the items from.
   */
  void takeItems(Collection<T> items, U inventory);

  /**
   * Takes a collection of items from an inventory.
   * @param items The collection of items to remove.
   * @param identifier The identifier of the player to remove the items from.
   */
  default void takeItems(Collection<T> items, UUID identifier) {

    final Optional<U> inventory = getInventory(identifier, InventoryType.PLAYER);
    inventory.ifPresent(u->takeItems(items, u));
  }

  /**
   * Adds a collection of item stacks to an inventory, returns the leftover items that won't fit in
   * the inventory.
   * @param items The collection of items to add to the inventory.
   * @param inventory The inventory to add the collection of items to.
   *
   * @return The collection of items that won't fit in the inventory.
   */
  Collection<T> giveItems(Collection<T> items, U inventory);

  /**
   * Adds a collection of item stacks to an inventory, returns the leftover items that won't fit in
   * the inventory.
   * @param items The collection of items to add to the inventory.
   * @param identifier The identifier of the player to add the collection of items to.
   *
   * @return The collection of items that won't fit in the inventory.
   */
  default Collection<T> giveItems(Collection<T> items, UUID identifier) {

    final Optional<U> inventory = getInventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->giveItems(items, u)).orElse(items);
  }

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   * @param stack The stack, with the correct amount, to remove.
   * @param inventory The inventory to remove the net.tnemc.item stack from.
   * @return The remaining amount of items to remove.
   */
  int removeItem(T stack, U inventory);

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   * @param stack The stack, with the correct amount, to remove.
   * @param identifier The identifier of the player to remove the net.tnemc.item stack from.
   * @return The remaining amount of items to remove.
   */
  default int removeItem(T stack, UUID identifier) {

    final Optional<U> inventory = getInventory(identifier, InventoryType.PLAYER);
    return inventory.map(u->removeItem(stack, u)).orElseGet(stack::amount);
  }

  /**
   * Used to locate an invetory for a UUID identifier.
   * @param identifier The identifier to use for the search.
   * @param type The inventory type to return.
   * @return An optional containing the inventory if it works, otherwise false.
   */
  Optional<U> getInventory(UUID identifier, InventoryType type);

  /**
   * Checks to see if two net.tnemc.item stacks are equal.
   * @param original The original net.tnemc.item stack.
   * @param compare The net.tnemc.item stack you're comparing to the original.
   * @return True if the net.tnemc.item stacks are equal, otherwise false.
   */
  default boolean itemsEqual(T original, T compare) {
    return original.similar(compare) && original.data().similar(compare.data());
  }
}