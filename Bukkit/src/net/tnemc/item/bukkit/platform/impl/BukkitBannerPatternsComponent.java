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
import net.tnemc.item.component.helper.PatternData;
import net.tnemc.item.component.impl.BannerPatternsComponent;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.List;
import java.util.Optional;

/**
 * BukkitModernBannerPatternsComponent
 *
 * @author creatorfromhell
 * @since 0.2.0.0
 */
public class BukkitBannerPatternsComponent extends BannerPatternsComponent<BukkitItemStack, ItemStack> {

  /**
   * Represents a component that handles banner patterns for an object.
   */
  public BukkitBannerPatternsComponent() {

  }

  /**
   * Creates a new BannerPatternsComponent with the provided list of PatternData objects.
   *
   * @param patterns The list of PatternData objects to initialize the component with.
   */
  public BukkitBannerPatternsComponent(final List<PatternData> patterns) {

    super(patterns);
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

    final Optional<BukkitBannerPatternsComponent> componentOptional = serialized.component(identifier());
    componentOptional.ifPresent(component->{

      if(item.hasItemMeta() && item.getItemMeta() instanceof final BannerMeta meta) {

        for(final PatternData pattern : componentOptional.get().patterns) {

          final DyeColor color = DyeColor.getByColor(Color.fromRGB(Integer.parseInt(pattern.getColor())));

          if(color != null) {

            try {

              meta.addPattern(new Pattern(color, BukkitItemPlatform.instance().converter().convert(pattern.getPattern(), PatternType.class)));
            } catch(final Exception ignore) {

              //pattern type doesn't exist.
            }
          }
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
   */
  @Override
  public BukkitItemStack serialize(final ItemStack item, final BukkitItemStack serialized) {

    if(item.hasItemMeta() && item.getItemMeta() instanceof final BannerMeta meta) {
      for(final Pattern pattern : meta.getPatterns()) {

        try {
          patterns.add(new PatternData(String.valueOf(pattern.getColor().getColor().asRGB()),
                                       BukkitItemPlatform.instance().converter().convert(pattern.getPattern(), String.class)));
        } catch(final Exception ignore) {

          //key isn't found
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

    return item.hasItemMeta() && item.getItemMeta() instanceof BannerMeta;
  }
}