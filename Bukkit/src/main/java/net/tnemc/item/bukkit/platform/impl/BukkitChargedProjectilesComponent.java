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
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * BukkitChargedProjectilesComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitChargedProjectilesComponent extends ContainerComponent<BukkitItemStack, ItemStack> {

  public BukkitChargedProjectilesComponent() {

  }

  public BukkitChargedProjectilesComponent(final Map<Integer, AbstractItemStack<ItemStack>> items) {

    super(items);
  }

  /**
   * @param version the version being used when this check is called.
   *
   * @return true if this check is enabled for the version, otherwise false
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean enabled(final String version) {

    return VersionUtil.isOneFourteen(version);
  }

  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   *
   * @since 0.2.0.0
   */
  @Override
  public ItemStack apply(final BukkitItemStack serialized, final ItemStack item) {

    final Optional<BukkitChargedProjectilesComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      if(item.hasItemMeta() && item.getItemMeta() instanceof final CrossbowMeta meta) {

        componentOptional.get().items.forEach((slot, stack)->meta.addChargedProjectile(stack.provider().locale(serialized)));

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
   *
   * @since 0.2.0.0
   */
  @Override
  public BukkitItemStack serialize(final ItemStack item, final BukkitItemStack serialized) {

    if(item.hasItemMeta() && item.getItemMeta() instanceof final CrossbowMeta meta) {

      final BukkitChargedProjectilesComponent component = (serialized.bukkitComponent(identifier()) instanceof final ContainerComponent<?, ?> getComponent)?
                                                          (BukkitChargedProjectilesComponent)getComponent : new BukkitChargedProjectilesComponent();

      final List<ItemStack> projectiles = meta.getChargedProjectiles();
      for(int i = 0; i < projectiles.size(); i++) {

        final ItemStack stack = projectiles.get(i);

        if(stack != null && !stack.getType().equals(Material.AIR)) {

          component.items.put(i, new BukkitItemStack().of(stack));
        }
      }

      serialized.applyComponent(component);
    }
    return serialized;
  }

  /**
   * Checks if this component applies to the specified item.
   *
   * @param item The item to check against.
   *
   * @return True if this component applies to the item, false otherwise.
   *
   * @since 0.2.0.0
   */
  @Override
  public boolean appliesTo(final ItemStack item) {

    return item.hasItemMeta() && item.getItemMeta() instanceof final CrossbowMeta meta;
  }
}