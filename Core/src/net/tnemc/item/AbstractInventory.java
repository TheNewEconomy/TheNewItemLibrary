package net.tnemc.item;

import java.util.Optional;

public interface AbstractInventory<T, I> {

  /**
   * Used to get the platform's inventory object.
   * @return The platform's inventory object.
   */
  T inventory();

  /**
   * Used to add an item to the inventory.
   * @param item The item to add to the inventory.
   * @param drop If the leftover items that won't fit in the inventory should be dropped or not.
   * @return If drop is set to true this is an empty optional, or if drop is false then this is an
   * optional containing the leftover items that wouldn't fit into the inventory.
   */
  Optional<SerialItem<I>> addItem(SerialItem<I> item, boolean drop);

  /**
   * Used to set a specific slot as a specific item.
   *
   * @param slot The slot number to set the item in.
   * @param item The item to set the slot as.
   * @param drop If this is true if there is an item in the slot already it is dropped.
   * @return If drop is set to true this is an empty optional, or if drop is false then this is an
   * optional containing the item that was in the slot before if applicable.
   */
  Optional<SerialItem<I>> setSlot(int slot, SerialItem<I> item, boolean drop);
}