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
import net.tnemc.item.providers.CalculationsProvider;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpongeItemCalculationsProvider implements CalculationsProvider<SpongeItemStack, ItemStack, Inventory> {

  @Override
  public boolean drop(final Collection<SpongeItemStack> left, final UUID identifier) {

    final Optional<ServerPlayer> player = Sponge.game().server().player(identifier);

    if(player.isPresent()) {
      for(final SpongeItemStack stack : left) {
        final Location<?, ?> location = player.get().location();
        final Item item = location.world().createEntity(EntityTypes.ITEM, player.get().position());

        item.offer(Keys.ITEM_STACK_SNAPSHOT, stack.locale().createSnapshot());
        location.world().spawnEntity(item);
      }
    }
    return false;
  }

  @Override
  public int removeAll(final SpongeItemStack stack, final Inventory inventory) {

    final ItemStack compare = stack.locale().copy();
    compare.setQuantity(1);

    int amount = 0;
    for(final Inventory slot : inventory.slots()) {
      final ItemStack slotItem = slot.peek();

      if(slotItem != null) {

        final ItemStack compareI = slotItem.copy();
        compareI.setQuantity(1);
        if(compareI.equalTo(stack.locale())) {
          amount += slotItem.quantity();
          slot.clear();
        }
      }
    }
    return amount;
  }

  @Override
  public int count(final SpongeItemStack stack, final Inventory inventory) {

    final ItemStack compare = stack.locale().copy();
    compare.setQuantity(1);
    int count = 0;

    for(final Inventory slot : inventory.slots()) {
      final ItemStack slotItem = slot.peek();

      if(slotItem != null) {

        final ItemStack compareI = slotItem.copy();
        compareI.setQuantity(1);
        if(compareI.equalTo(stack.locale())) {
          count += slot.totalQuantity();
        }
      }
    }
    return count;
  }

  @Override
  public void takeItems(final Collection<SpongeItemStack> items, final Inventory inventory) {

    items.forEach(itemStack->removeItem(itemStack, inventory));
  }

  @Override
  public Collection<SpongeItemStack> giveItems(final Collection<SpongeItemStack> items, final Inventory inventory) {

    final Collection<SpongeItemStack> leftOver = new ArrayList<>();

    for(final SpongeItemStack stack : items) {
      final InventoryTransactionResult result = inventory.offer(stack.locale());
      final List<ItemStackSnapshot> rejected = result.rejectedItems();
      if(rejected.size() > 0) {
        rejected.forEach(snapshot->leftOver.add(new SpongeItemStack().of(snapshot.createStack())));
      }
    }
    return leftOver;
  }

  @Override
  public int removeItem(final SpongeItemStack stack, final Inventory inventory) {

    int left = stack.locale().copy().quantity();

    final ItemStack compare = stack.locale().copy();
    compare.setQuantity(1);
    for(final Inventory slot : inventory.slots()) {
      if(left <= 0) break;

      final ItemStack slotItem = slot.peek();
      if(slotItem != null) {


        final ItemStack compare1 = slotItem.copy();
        compare1.setQuantity(1);
        if(compare.equalTo(compare1)) {
          final int quantity = slotItem.quantity();

          if(quantity > left) {
            slotItem.setQuantity(quantity - left);
            slot.set(0, slotItem);
            left = 0;
          } else {
            left -= quantity;
            slot.clear();
          }
        }
      }
    }
    return left;
  }

  @Override
  public Optional<Inventory> getInventory(final UUID identifier, final InventoryType type) {

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
