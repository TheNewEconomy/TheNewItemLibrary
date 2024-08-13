package net.tnemc.item.paper.data;

/*
 * The New Item Library Minecraft Server Plugin
 *
 * Copyright (C) 2022 - 2024 Daniel "creatorfromhell" Vidmar
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

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.data.BannerData;
import net.tnemc.item.data.banner.PatternData;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class PaperBannerModernData extends BannerData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final BannerMeta meta = (BannerMeta)stack.getItemMeta();

    if(meta != null) {
      for(final Pattern pattern : meta.getPatterns()) {
        patterns.add(new PatternData(String.valueOf(pattern.getColor().getColor().asRGB()),
                pattern.getPattern().key().toString()));
      }
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {

    final BannerMeta meta = (BannerMeta)ParsingUtil.buildFor(stack, BannerMeta.class);

    for(final PatternData pattern : patterns) {
      final NamespacedKey key = NamespacedKey.fromString(pattern.getPattern());
      final DyeColor color = DyeColor.getByColor(Color.fromRGB(Integer.valueOf(pattern.getColor())));
      final PatternType type = RegistryAccess.registryAccess().getRegistry(RegistryKey.BANNER_PATTERN).get(key);
      if(key != null && color != null && type != null) {

        meta.addPattern(new Pattern(color, type));
      }
    }
    stack.setItemMeta(meta);

    return stack;
  }
}