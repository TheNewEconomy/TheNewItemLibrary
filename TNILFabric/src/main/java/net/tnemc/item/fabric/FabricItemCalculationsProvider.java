package net.tnemc.item.fabric;
/*
 * The New Item Library
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

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.tnemc.item.InventoryType;
import net.tnemc.item.fabric.inventory.FabricInventoryAdapter;
import net.tnemc.item.inventory.InventorySlot;
import net.tnemc.item.inventory.InventoryView;
import net.tnemc.item.providers.CalculationsProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * FabricItemCalculationsProvider
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class FabricItemCalculationsProvider implements CalculationsProvider<FabricItemStack, ItemStack, Inventory> {


  //TODO: this
  private final MinecraftServer server = null;
  private final FabricInventoryAdapter inventories = new FabricInventoryAdapter();

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
  public boolean drop(final Collection<FabricItemStack> left, final UUID player, final boolean setOwner) {

    final ServerPlayerEntity serverPlayer = getPlayer(player);
    if(serverPlayer == null) {
      return false;
    }

    final ServerWorld world = serverPlayer.getServerWorld();
    final Vec3d pos = serverPlayer.getPos().add(0, 1, 0);

    for(final FabricItemStack fabricStack : left) {

      final ItemStack stack = fabricStack.provider().locale(fabricStack, fabricStack.amount()).copy();
      if(stack.isEmpty()) {
        continue;
      }

      final ItemEntity itemEntity = new ItemEntity(world, pos.x, pos.y, pos.z, stack.copy());
      itemEntity.setVelocity(
              world.random.nextFloat() * 0.2 - 0.1,
              world.random.nextFloat() * 0.2,
              world.random.nextFloat() * 0.2);

      if(setOwner) {

        itemEntity.setThrower(serverPlayer);
      }

      world.spawnEntity(itemEntity);
    }

    return true;
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
  public int removeAll(final FabricItemStack stack, final Inventory inventory, final boolean useShulker, final boolean useBundles) {
    final InventoryView<ItemStack> view = inventories.view(inventory);
    final int removed = removeAll(stack, view, useShulker, useBundles);
    inventories.apply(inventory, view.mutation());
    return removed;
  }

  private int removeAll(final FabricItemStack stack, final InventoryView<ItemStack> inventory, final boolean useShulker, final boolean useBundles) {

    final ItemStack compare = normalized(stack.provider().locale(stack, stack.amount()));
    int removed = 0;

    for(final InventorySlot<ItemStack> slot : inventory.slots()) {
      final ItemStack slotItem = slot.item();
      if(slotItem.isEmpty()) continue;

      if(matches(compare, slotItem)) {
        removed += slotItem.getCount();
        slot.item(ItemStack.EMPTY);
        continue;
      }

      if(useShulker) {
        removed += removeAllFromContainer(compare, slotItem);
      }
      if(useBundles) {
        removed += removeAllFromBundle(compare, slotItem);
      }

      slot.item(slotItem);
    }

    return removed;
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
  public int count(final FabricItemStack stack, final Inventory inventory, final boolean useShulker, final boolean useBundles) {

    final ItemStack compare = normalized(stack.provider().locale(stack, stack.amount()));
    int count = 0;

    for(int i = 0; i < inventory.size(); i++) {
      final ItemStack slotItem = inventory.getStack(i);
      if(slotItem.isEmpty()) continue;

      if(matches(compare, slotItem)) {
        count += slotItem.getCount();
      }
      if(useShulker) {
        count += countContainer(compare, slotItem);
      }
      if(useBundles) {
        count += countBundle(compare, slotItem);
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
  public void takeItems(final Collection<FabricItemStack> items, final Inventory inventory, final boolean useShulker, final boolean useBundles) {

    final InventoryView<ItemStack> view = inventories.view(inventory);
    for(final FabricItemStack stack : items) {
      removeItem(stack, view, useShulker, useBundles);
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
  public Collection<FabricItemStack> giveItems(final Collection<FabricItemStack> items, final Inventory inventory, final boolean useShulker, final boolean useBundles) {
    final InventoryView<ItemStack> view = inventories.view(inventory);
    final Collection<FabricItemStack> leftovers = new ArrayList<>();

    for(final FabricItemStack fabricStack : items) {
      if(fabricStack == null) continue;

      final ItemStack toInsert = fabricStack.provider().locale(fabricStack, fabricStack.amount()).copy();
      int remaining = toInsert.getCount();

      for(final InventorySlot<ItemStack> slot : view.slots()) {
        if(remaining <= 0) break;
        final ItemStack current = slot.item();

        if(current == null || current.isEmpty()) continue;
        if(ItemStack.areItemsAndComponentsEqual(current, toInsert)) {
          final int maximum = Math.min(view.maxStackSize(), current.getMaxCount());
          final int moved = Math.min(maximum - current.getCount(), remaining);
          if(moved > 0) {
            final ItemStack changed = current.copy();
            changed.increment(moved);
            slot.item(changed);
            remaining -= moved;
          }
        }
      }

      for(final InventorySlot<ItemStack> slot : view.slots()) {
        if(remaining <= 0) break;
        final ItemStack current = slot.item();
        if(current != null && !current.isEmpty()) continue;

        final int moved = Math.min(Math.min(view.maxStackSize(), toInsert.getMaxCount()), remaining);
        final ItemStack changed = toInsert.copy();
        changed.setCount(moved);
        slot.item(changed);
        remaining -= moved;
      }

      if(remaining > 0) {
        final ItemStack leftoverStack = toInsert.copy();
        leftoverStack.setCount(remaining);
        leftovers.add(new FabricItemStack().of(leftoverStack));
      }
    }

    inventories.apply(inventory, view.mutation());
    return leftovers;
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
  public int removeItem(final FabricItemStack stack, final Inventory inventory, final boolean useShulker, final boolean useBundles) {
    final InventoryView<ItemStack> view = inventories.view(inventory);
    final int left = removeItem(stack, view, useShulker, useBundles);
    inventories.apply(inventory, view.mutation());
    return left;
  }

  private int removeItem(final FabricItemStack stack, final InventoryView<ItemStack> inventory, final boolean useShulker, final boolean useBundles) {

    int left = stack.amount();
    final ItemStack compare = normalized(stack.provider().locale(stack, stack.amount()));

    for(final InventorySlot<ItemStack> slot : inventory.slots()) {
      if(left <= 0) break;
      final ItemStack slotItem = slot.item();
      if(slotItem.isEmpty()) continue;

      if(matches(compare, slotItem)) {
        final int remove = Math.min(left, slotItem.getCount());
        slotItem.decrement(remove);
        left -= remove;
        slot.item(slotItem.isEmpty()? ItemStack.EMPTY : slotItem);
        continue;
      }

      if(useShulker && left > 0) {
        left = removeFromContainer(compare, slotItem, left);
      }
      if(useBundles && left > 0) {
        left = removeFromBundle(compare, slotItem, left);
      }

      slot.item(slotItem);
    }

    return left;
    }

  private ItemStack normalized(final ItemStack stack) {
    final ItemStack copy = stack.copy();
    copy.setCount(1);
    return copy;
  }

  private boolean matches(final ItemStack compare, final ItemStack candidate) {
    return !candidate.isEmpty() && ItemStack.areItemsAndComponentsEqual(compare, normalized(candidate));
  }

  private int countContainer(final ItemStack compare, final ItemStack containerStack) {
    final ContainerComponent container = containerStack.get(DataComponentTypes.CONTAINER);
    if(container == null) return 0;

    int count = 0;
    for(final ItemStack contained : (Iterable<ItemStack>)container.stream()::iterator) {
      if(matches(compare, contained)) count += contained.getCount();
    }
    return count;
  }

  private int removeAllFromContainer(final ItemStack compare, final ItemStack containerStack) {
    final ContainerComponent container = containerStack.get(DataComponentTypes.CONTAINER);
    if(container == null) return 0;

    final List<ItemStack> contents = new ArrayList<>();
    int removed = 0;
    for(final ItemStack contained : (Iterable<ItemStack>)container.stream()::iterator) {
      if(matches(compare, contained)) {
        removed += contained.getCount();
        contents.add(ItemStack.EMPTY);
      } else {
        contents.add(contained.copy());
      }
    }
    if(removed > 0) containerStack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(contents));
    return removed;
  }

  private int removeFromContainer(final ItemStack compare, final ItemStack containerStack, int left) {
    final ContainerComponent container = containerStack.get(DataComponentTypes.CONTAINER);
    if(container == null) return left;

    final List<ItemStack> contents = new ArrayList<>();
    for(final ItemStack contained : (Iterable<ItemStack>)container.stream()::iterator) {
      final ItemStack updated = contained.copy();
      if(left > 0 && matches(compare, updated)) {
        final int remove = Math.min(left, updated.getCount());
        updated.decrement(remove);
        left -= remove;
      }
      contents.add(updated);
    }
    containerStack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(contents));
    return left;
  }

  private int countBundle(final ItemStack compare, final ItemStack bundleStack) {
    final BundleContentsComponent bundle = bundleStack.get(DataComponentTypes.BUNDLE_CONTENTS);
    if(bundle == null) return 0;

    int count = 0;
    for(int i = 0; i < bundle.size(); i++) {
      final ItemStack contained = bundle.get(i);
      if(matches(compare, contained)) count += contained.getCount();
    }
    return count;
  }

  private int removeAllFromBundle(final ItemStack compare, final ItemStack bundleStack) {
    final BundleContentsComponent bundle = bundleStack.get(DataComponentTypes.BUNDLE_CONTENTS);
    if(bundle == null) return 0;

    final List<ItemStack> contents = new ArrayList<>();
    int removed = 0;
    for(int i = 0; i < bundle.size(); i++) {
      final ItemStack contained = bundle.get(i);
      if(matches(compare, contained)) {
        removed += contained.getCount();
      } else {
        contents.add(contained.copy());
      }
    }
    if(removed > 0) bundleStack.set(DataComponentTypes.BUNDLE_CONTENTS, new BundleContentsComponent(contents));
    return removed;
  }

  private int removeFromBundle(final ItemStack compare, final ItemStack bundleStack, int left) {
    final BundleContentsComponent bundle = bundleStack.get(DataComponentTypes.BUNDLE_CONTENTS);
    if(bundle == null) return left;

    final List<ItemStack> contents = new ArrayList<>();
    for(int i = 0; i < bundle.size(); i++) {
      final ItemStack updated = bundle.get(i).copy();
      if(left > 0 && matches(compare, updated)) {
        final int remove = Math.min(left, updated.getCount());
        updated.decrement(remove);
        left -= remove;
      }
      if(!updated.isEmpty()) contents.add(updated);
    }
    bundleStack.set(DataComponentTypes.BUNDLE_CONTENTS, new BundleContentsComponent(contents));
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

    final ServerPlayerEntity serverPlayer = getPlayer(identifier);

    if(serverPlayer == null) {
      return Optional.empty();
    }

    if(type.equals(InventoryType.ENDER_CHEST)) {
      return Optional.of(serverPlayer.getEnderChestInventory());
    }
    return Optional.of(serverPlayer.getInventory());
  }

  private ServerPlayerEntity getPlayer(final UUID uuid) {

    return server.getPlayerManager().getPlayer(uuid);
  }
}