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

import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.tnemc.item.InventoryType;
import net.tnemc.item.providers.CalculationsProvider;

import java.util.ArrayList;
import java.util.Collection;
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
   * @param stack     The stack to compare to for removal from the inventory.
   * @param inventory The inventory to remove the items from.
   *
   * @return The amount of items removed.
   */
  @Override
  public int removeAll(final FabricItemStack stack, final Inventory inventory) {

    final ItemStack compare = stack.provider().locale(stack, stack.amount()).copy();
    compare.setCount(1);

    int amount = 0;

    for(int i = 0; i < inventory.size(); i++) {
      final ItemStack slotItem = inventory.getStack(i);

      if(!slotItem.isEmpty()) {
        final ItemStack compareI = slotItem.copy();
        compareI.setCount(1);

        if(ItemStack.areItemsAndComponentsEqual(compare, compareI)) {

          amount += slotItem.getCount();
          inventory.setStack(i, ItemStack.EMPTY);
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
  public int count(final FabricItemStack stack, final Inventory inventory) {
    final ItemStack compare = stack.provider().locale(stack, stack.amount()).copy();
    compare.setCount(1);

    int count = 0;

    for(int i = 0; i < inventory.size(); i++) {
      final ItemStack slotItem = inventory.getStack(i);

      if(!slotItem.isEmpty()) {
        final ItemStack compareI = slotItem.copy();
        compareI.setCount(1);

        if(ItemStack.areItemsAndComponentsEqual(compare, compareI)) {
          count += slotItem.getCount();
        }
      }
    }

    return count;
  }

  /**
   * Takes a collection of items from an inventory.
   *
   * @param items     The collection of items to remove.
   * @param inventory The inventory to remove the items from.
   */
  @Override
  public void takeItems(final Collection<FabricItemStack> items, final Inventory inventory) {
    for(final FabricItemStack stack : items) {
      removeItem(stack, inventory);
    }
  }

  /**
   * Adds a collection of item stacks to an inventory, returns the leftover items that won't fit in
   * the inventory.
   *
   * @param items     The collection of items to add to the inventory.
   * @param inventory The inventory to add the collection of items to.
   *
   * @return The collection of items that won't fit in the inventory.
   */
  @Override
  public Collection<FabricItemStack> giveItems(final Collection<FabricItemStack> items, final Inventory inventory) {
    final Collection<FabricItemStack> leftovers = new ArrayList<>();

    for(final FabricItemStack fabricStack : items) {
      if(fabricStack == null) continue;

      final ItemStack toInsert = fabricStack.provider().locale(fabricStack, fabricStack.amount()).copy();
      int remaining = toInsert.getCount();

      for(int i = 0; i < inventory.size(); i++) {
        if(remaining <= 0) break;

        final ItemStack slot = inventory.getStack(i);

        if(slot.isEmpty()) {
          inventory.setStack(i, toInsert.copy());
          remaining = 0;
          break;

        } else if(ItemStack.areItemsAndComponentsEqual(slot, toInsert)) {
          final int space = Math.min(slot.getMaxCount() - slot.getCount(), remaining);
          if (space > 0) {
            slot.increment(space);
            remaining -= space;
          }
        }
      }

      if(remaining > 0) {
        final ItemStack leftoverStack = toInsert.copy();
        leftoverStack.setCount(remaining);
        leftovers.add(new FabricItemStack().of(leftoverStack));

      }
    }

    return leftovers;
  }

  /**
   * Removes an net.tnemc.item stack with a specific amount from an inventory.
   *
   * @param stack     The stack, with the correct amount, to remove.
   * @param inventory The inventory to remove the net.tnemc.item stack from.
   *
   * @return The remaining amount of items to remove.
   */
  @Override
  public int removeItem(final FabricItemStack stack, final Inventory inventory) {
    int left = stack.provider().locale(stack, stack.amount()).copy().getCount();
    final ItemStack compare = stack.provider().locale(stack, stack.amount()).copy();
    compare.setCount(1); // Normalize

    for(int i = 0; i < inventory.size(); i++) {
      if(left <= 0) break;

      final ItemStack slotItem = inventory.getStack(i);
      if(!slotItem.isEmpty()) {

        final ItemStack compareI = slotItem.copy();
        compareI.setCount(1);

        if(ItemStack.areItemsAndComponentsEqual(compare, compareI)) {

          final int quantity = slotItem.getCount();

          if(quantity > left) {

            slotItem.setCount(quantity - left);
            inventory.setStack(i, slotItem);
            left = 0;
          } else {

            left -= quantity;
            inventory.setStack(i, ItemStack.EMPTY);
          }
        }
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