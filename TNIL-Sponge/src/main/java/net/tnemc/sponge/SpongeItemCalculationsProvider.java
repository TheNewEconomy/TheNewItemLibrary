package net.tnemc.sponge;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2025 Daniel "creatorfromhell" Vidmar
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
import net.tnemc.sponge.inventory.SpongeInventoryAdapter;
import net.tnemc.item.providers.CalculationsProvider;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpongeItemCalculationsProvider implements CalculationsProvider<SpongeItemStack, ItemStack, Inventory> {

  private final SpongeInventoryAdapter inventories = new SpongeInventoryAdapter();

  /**
   * Removes items from a collection based on certain criteria.
   *
   * @param left       The collection of items from which to remove items.
   * @param identifier The UUID of the player associated with the removal operation.
   * @param setOwner   Indicates whether to set the owner of the removed items.(supports
   *                   spigot/paper 1.16.5+)
   *
   * @return True if the removal operation was successful, false otherwise.
   */
  @Override
  public boolean drop(final Collection<SpongeItemStack> left, final UUID identifier, final boolean setOwner) {

    final Optional<ServerPlayer> player = Sponge.game().server().player(identifier);

    if(player.isPresent()) {
      for(final SpongeItemStack stack : left) {
        final Location<?, ?> location = player.get().location();
        final Item item = location.world().createEntity(EntityTypes.ITEM, player.get().position());

        item.offer(Keys.ITEM_STACK_SNAPSHOT, stack.provider().locale(stack, stack.amount()).asImmutable());
        location.world().spawnEntity(item);
      }
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
   *
   * @return The amount of items removed.
   */
  @Override
  public int removeAll(final SpongeItemStack stack, final Inventory inventory, final boolean useShulker, final boolean useBundles) {
    final InventoryView<ItemStack> view = inventories.view(inventory);
    final ItemStack compare = stack.provider().locale(stack, stack.amount()).copy();
    compare.setQuantity(1);

    int amount = 0;
    for(final InventorySlot<ItemStack> slot : view.slots()) {
      final ItemStack slotItem = slot.item();
      if(slotItem == null) continue;

      final ItemStack candidate = slotItem.copy();
      candidate.setQuantity(1);
      if(candidate.equalTo(compare)) {
        amount += slotItem.quantity();
        slot.item(null);
      }
    }

    inventories.apply(inventory, view.mutation());
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
  public int count(final SpongeItemStack stack, final Inventory inventory, final boolean useShulker, final boolean useBundles) {

    final ItemStack compare = stack.provider().locale(stack, stack.amount()).copy();
    compare.setQuantity(1);
    int count = 0;

    for(final Inventory slot : inventory.slots()) {
      final ItemStack slotItem = slot.peek();

      if(slotItem != null) {

        final ItemStack compareI = slotItem.copy();
        compareI.setQuantity(1);
        if(compareI.equalTo(compare)) {
          count += slot.totalQuantity();
        }
      }
    }
    return count;
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
  public void takeItems(final Collection<SpongeItemStack> items, final Inventory inventory, final boolean useShulker, final boolean useBundles) {

    final InventoryView<ItemStack> view = inventories.view(inventory);
    for(final SpongeItemStack item : items) {
      removeItem(item, view);
    }
    inventories.apply(inventory, view.mutation());
  }

  /**
   * Adds a collection of item stacks to an inventory, returns the leftover items that won't fit in
   * the inventory.
   *
   * @param items      The collection of items to add to the inventory.
   * @param inventory  The inventory to add the collection of items to.
   * @param useShulker A boolean flag indicating whether to include shulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include bundles in the removal process.
   *
   * @return The collection of items that won't fit in the inventory.
   */
  @Override
  public Collection<SpongeItemStack> giveItems(final Collection<SpongeItemStack> items, final Inventory inventory, final boolean useShulker, final boolean useBundles) {
    final InventoryView<ItemStack> view = inventories.view(inventory);
    final Collection<SpongeItemStack> leftOver = new ArrayList<>();

    for(final SpongeItemStack stack : items) {
      if(stack == null) continue;

      final ItemStack insertion = stack.provider().locale(stack, stack.amount()).copy();
      int left = insertion.quantity();

      for(final InventorySlot<ItemStack> slot : view.slots()) {
        if(left <= 0) break;
        final ItemStack current = slot.item();
        if(current == null) continue;

        final ItemStack a = current.copy();
        final ItemStack b = insertion.copy();
        a.setQuantity(1);
        b.setQuantity(1);
        if(!a.equalTo(b)) continue;

        final int maximum = Math.min(view.maxStackSize(), current.maxStackQuantity());
        final int moved = Math.min(maximum - current.quantity(), left);
        if(moved > 0) {
          final ItemStack changed = current.copy();
          changed.setQuantity(current.quantity() + moved);
          slot.item(changed);
          left -= moved;
        }
      }

      for(final InventorySlot<ItemStack> slot : view.slots()) {
        if(left <= 0) break;
        if(slot.item() != null) continue;

        final int moved = Math.min(Math.min(view.maxStackSize(), insertion.maxStackQuantity()), left);
        final ItemStack changed = insertion.copy();
        changed.setQuantity(moved);
        slot.item(changed);
        left -= moved;
      }

      if(left > 0) {
        final ItemStack remainder = insertion.copy();
        remainder.setQuantity(left);
        leftOver.add(new SpongeItemStack().of(remainder));
      }
    }

    inventories.apply(inventory, view.mutation());
    return leftOver;
  }

  /**
   * Removes a net.tnemc.item stack with a specific amount from an inventory.
   *
   * @param stack      The stack, with the correct amount, to remove.
   * @param inventory  The inventory to remove the net.tnemc.item stack from.
   * @param useShulker A boolean flag indicating whether to include shulker boxes in the removal
   *                   process.
   * @param useBundles A boolean flag indicating whether to include bundles in the removal process.
   *
   * @return The remaining amount of items to remove.
   */
  @Override
  public int removeItem(final SpongeItemStack stack, final Inventory inventory, final boolean useShulker, final boolean useBundles) {
    final InventoryView<ItemStack> view = inventories.view(inventory);
    final int left = removeItem(stack, view);
    inventories.apply(inventory, view.mutation());
    return left;
  }

  private int removeItem(final SpongeItemStack stack, final InventoryView<ItemStack> view) {
    int left = stack.amount();
    final ItemStack compare = stack.provider().locale(stack, stack.amount()).copy();
    compare.setQuantity(1);

    for(final InventorySlot<ItemStack> slot : view.slots()) {
      if(left <= 0) break;
      final ItemStack slotItem = slot.item();
      if(slotItem == null) continue;

      final ItemStack candidate = slotItem.copy();
      candidate.setQuantity(1);
      if(!compare.equalTo(candidate)) continue;

      final int removed = Math.min(left, slotItem.quantity());
      left -= removed;

      if(removed == slotItem.quantity()) {
        slot.item(null);
      } else {
        final ItemStack changed = slotItem.copy();
        changed.setQuantity(slotItem.quantity() - removed);
        slot.item(changed);
      }
    }
    return left;
  }

  /**
   * Used to locate an invetory for a UUID identifier.
   *
   * @param identifier The identifier to use for the search.
   * @param type       The inventory type to return.
   *
   * @return An optional containing the inventory if it works, otherwise false.
   */
  @Override
  public Optional<Inventory> inventory(final UUID identifier, final InventoryType type) {

    final Optional<ServerPlayer> player = Sponge.game().server().player(identifier);
    if(player.isPresent() && player.get().isOnline()) {

      if(type.equals(InventoryType.ENDER_CHEST)) {
        return Optional.of(player.get().enderChestInventory());
      } else {
        return Optional.of(player.get().inventory());
      }
    }
    return Optional.empty();
  }
}
