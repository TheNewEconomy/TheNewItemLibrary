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

import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.component.impl.MaxDamageComponent;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Optional;

/**
 * BukkitMaxDamageComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitMaxDamageComponent extends MaxDamageComponent<BukkitItemStack, ItemStack> {

  public BukkitMaxDamageComponent() {

  }

  public BukkitMaxDamageComponent(final int maxDamage) {

    super(maxDamage);
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

    return VersionUtil.isOneThirteen(version);
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

    final Optional<BukkitMaxDamageComponent> componentOptional = serialized.component(identifier());

    if(componentOptional.isPresent()) {

      if(item.hasItemMeta() && item.getItemMeta() instanceof final Damageable meta) {

        meta.setMaxDamage(componentOptional.get().maxDamage);
        item.setItemMeta(meta);
      }
    }
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

    if(item.hasItemMeta() && item.getItemMeta() instanceof final Damageable meta) {

      final BukkitMaxDamageComponent component = (serialized.bukkitComponent(identifier()) instanceof final MaxDamageComponent<?, ?> getComponent)?
                                                 (BukkitMaxDamageComponent)getComponent : new BukkitMaxDamageComponent();

      component.maxDamage = meta.getMaxDamage();

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

    return item.hasItemMeta() && item.getItemMeta() instanceof Damageable;
  }
}