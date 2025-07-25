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
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.component.impl.DamageComponent;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Optional;

/**
 * BukkitDamageComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitDamageComponent extends DamageComponent<BukkitItemStack, ItemStack> {

  /**
   * Represents a component that manages damage information. This component stores and provides
   * methods for handling damage values.
   *
   * @since 0.2.0.0
   */
  public BukkitDamageComponent() {

  }

  /**
   * Constructs a new DamageComponent with the specified damage amount.
   *
   * @param damage the amount of damage for the component
   *
   * @since 0.2.0.0
   */
  public BukkitDamageComponent(final int damage) {

    super(damage);
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

    return true;
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

    final Optional<BukkitDamageComponent> componentOptional = serialized.component(identifier());

    if(componentOptional.isPresent()) {
      if(VersionUtil.isOneThirteen(BukkitItemPlatform.instance().version())) {

        if(item.hasItemMeta() && item.getItemMeta() instanceof final Damageable meta) {

          meta.setDamage(componentOptional.get().damage());
          item.setItemMeta(meta);
        }
      } else {

        item.setDurability((short)componentOptional.get().damage);
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

    final BukkitDamageComponent component = (serialized.bukkitComponent(identifier()) instanceof final DamageComponent<?, ?> getComponent)?
                                            (BukkitDamageComponent)getComponent : new BukkitDamageComponent();

    if(VersionUtil.isOneThirteen(BukkitItemPlatform.instance().version())) {

      if(item.hasItemMeta() && item.getItemMeta() instanceof final Damageable meta) {

        if(meta.hasDamage()) {

          component.damage = meta.getDamage();

          serialized.applyComponent(component);
        }
      }
    } else {
      component.damage = item.getDurability();

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

    return true;
  }
}