package net.tnemc.item.bukkit.data;

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

import net.tnemc.item.SerialItemData;
import net.tnemc.item.bukkitbase.ParsingUtil;
import net.tnemc.item.data.ShieldData;
import net.tnemc.item.data.banner.PatternData;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ShieldMeta;

public class BukkitShieldData extends ShieldData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(final ItemStack stack) {

    final ShieldMeta meta = (ShieldMeta)stack.getItemMeta();

    if(meta != null) {

      if(meta.getBaseColor() != null) {
        this.colorRGB = meta.getBaseColor().getColor().asRGB();
      }
      for(final Pattern pattern : meta.getPatterns()) {
        patterns.add(new PatternData(String.valueOf(pattern.getColor().getColor().asRGB()),
                                     pattern.getPattern().getIdentifier()));
      }
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(final ItemStack stack) {

    final ShieldMeta meta = (ShieldMeta)ParsingUtil.buildFor(stack, ShieldMeta.class);

    if(this.colorRGB > -1) {
      meta.setBaseColor(DyeColor.getByColor(Color.fromRGB(this.colorRGB)));
    }

    for(final PatternData pattern : patterns) {
      meta.addPattern(new Pattern(DyeColor.getByColor(Color.fromRGB(Integer.valueOf(pattern.getColor()))),
                                  PatternType.valueOf(pattern.getPattern())));
    }
    stack.setItemMeta(meta);

    return stack;
  }
}