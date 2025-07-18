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
import net.tnemc.item.component.impl.TrimComponent;
import net.tnemc.item.providers.VersionUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

import java.util.Optional;

/**
 * BukkitTrimComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitTrimComponent extends TrimComponent<BukkitItemStack, ItemStack> {

  public BukkitTrimComponent() {

  }

  public BukkitTrimComponent(final String pattern, final String material) {

    super(pattern, material);
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

    return VersionUtil.isOneTwenty(version);
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

    final Optional<BukkitTrimComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      if(item.hasItemMeta() && item.getItemMeta() instanceof final ArmorMeta meta) {

        try {

          final TrimMaterial material = BukkitItemPlatform.instance().converter().convert(componentOptional.get().material, TrimMaterial.class);
          final TrimPattern pattern = BukkitItemPlatform.instance().converter().convert(componentOptional.get().pattern, TrimPattern.class);
          if(material != null && pattern != null) {

            meta.setTrim(new ArmorTrim(material, pattern));
          }

        } catch(final Exception ignore) {

          //invalid material/pattern
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

    if(item.hasItemMeta() && item.getItemMeta() instanceof final ArmorMeta meta && meta.getTrim() != null) {

      try {

        final String material = BukkitItemPlatform.instance().converter().convert(meta.getTrim().getMaterial(), String.class);
        final String pattern = BukkitItemPlatform.instance().converter().convert(meta.getTrim().getPattern(), String.class);
        if(material != null && pattern != null) {

          final BukkitTrimComponent component = (serialized.bukkitComponent(identifier()) instanceof final TrimComponent<?, ?> getComponent)?
                                                (BukkitTrimComponent)getComponent : new BukkitTrimComponent();

          component.material = material;
          component.pattern = pattern;

          serialized.applyComponent(component);
        }

      } catch(final Exception ignore) {
        //invalid material/pattern
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

    return item.hasItemMeta() && item.getItemMeta() instanceof ArmorMeta;
  }
}