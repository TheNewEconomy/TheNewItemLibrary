package net.tnemc.item.bukkit.platform.impl;
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

import net.tnemc.item.AbstractItemStack;
import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.component.impl.ContainerComponent;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.Map;
import java.util.Optional;

/**
 * BukkitContainerComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitContainerComponent extends ContainerComponent<BukkitItemStack, ItemStack> {

  public BukkitContainerComponent() {

  }

  public BukkitContainerComponent(final Map<Integer, AbstractItemStack<ItemStack>> items) {

    super(items);
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   */
  @Override
  public boolean enabled(final String version) {

    return true;
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   */
  @Override
  public ItemStack apply(final BukkitItemStack serialized, final ItemStack item) {

    final Optional<BukkitContainerComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      if(item.hasItemMeta() && item.getItemMeta() instanceof final BlockStateMeta meta
         && meta.hasBlockState() && meta.getBlockState() instanceof final Container container) {

        componentOptional.get().items.forEach((slot, stack)->container.getInventory().setItem(slot, stack.locale()));
        container.update(true);
        meta.setBlockState(container);

        item.setItemMeta(meta);
      }
    });
    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   */
  @Override
  public BukkitItemStack serialize(final ItemStack item, final BukkitItemStack serialized) {

    if(item.hasItemMeta() && item.getItemMeta() instanceof final BlockStateMeta meta
       && meta.hasBlockState() && meta.getBlockState() instanceof final Container container) {

      final Inventory inventory = container.getInventory();
      for(int i = 0; i < inventory.getSize(); i++) {

        final ItemStack stack = inventory.getItem(i);

        if(stack != null && !stack.getType().equals(Material.AIR)) {

          items.put(i, new BukkitItemStack().of(stack));
        }
      }
    }

    serialized.applyComponent(this);
    return serialized;
  }

  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.hasItemMeta() && item.getItemMeta() instanceof final BlockStateMeta meta
           && meta.hasBlockState() && meta.getBlockState() instanceof Container;
  }
}