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
import net.tnemc.item.component.impl.BaseColorComponent;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ShieldMeta;

import java.util.Optional;

/**
 * BukkitBaseColorComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitBaseColorComponent extends BaseColorComponent<BukkitItemStack, ItemStack> {

  public BukkitBaseColorComponent() {

  }

  /**
   * Constructs a new BaseColorComponent with the specified color.
   *
   * @param color The base color value for the component.
   *
   * @since 0.2.0.0
   */
  public BukkitBaseColorComponent(final String color) {

    super(color);
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

    return VersionUtil.isOneTwentyOne(version);
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

    final Optional<BukkitBaseColorComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      if(item.hasItemMeta() && item.getItemMeta() instanceof final ShieldMeta meta) {

        try {
          final DyeColor dyeColor = BukkitItemPlatform.instance().converter().convert(componentOptional.get().color, DyeColor.class);
          meta.setBaseColor(dyeColor);
        } catch(final IllegalArgumentException ignore) {
          meta.setBaseColor(null);
        }

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

    if(item.hasItemMeta() && item.getItemMeta() instanceof final ShieldMeta meta) {

      if(meta.getBaseColor() != null) {

        final BukkitBaseColorComponent component = (serialized.bukkitComponent(identifier()) instanceof final BaseColorComponent<?, ?> getComponent)?
                                                   (BukkitBaseColorComponent)getComponent : new BukkitBaseColorComponent();

        component.color = BukkitItemPlatform.instance().converter().convert(meta.getBaseColor(), String.class);

        serialized.applyComponent(component);
      }
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

    return item.hasItemMeta() && item.getItemMeta() instanceof ShieldMeta;
  }
}