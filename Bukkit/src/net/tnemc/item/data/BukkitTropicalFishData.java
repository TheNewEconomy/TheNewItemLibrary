package net.tnemc.item.data;

/*
 * The New Economy Minecraft Server Plugin
 *
 * Copyright (C) 2022 Daniel "creatorfromhell" Vidmar
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
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

public class BukkitTropicalFishData extends TropicalFishData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final TropicalFishBucketMeta meta = (TropicalFishBucketMeta)stack.getItemMeta();

    if(meta != null && meta.hasVariant()) {
      variant = true;

      pattern = meta.getPattern().name();
      patternColour = meta.getPatternColor().getColor().asRGB();
      bodyColour = meta.getBodyColor().getColor().asRGB();
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {

    TropicalFishBucketMeta meta;

    if(stack.hasItemMeta() && stack.getItemMeta() instanceof TropicalFishBucketMeta) {
      meta = (TropicalFishBucketMeta)stack.getItemMeta();
    } else {
      meta = (TropicalFishBucketMeta)Bukkit.getItemFactory().getItemMeta(Material.TROPICAL_FISH_BUCKET);
    }

    if(variant) {
      meta.setBodyColor(DyeColor.getByColor(Color.fromRGB(bodyColour)));
      meta.setPatternColor(DyeColor.getByColor(Color.fromRGB(patternColour)));
      meta.setPattern(TropicalFish.Pattern.valueOf(pattern));
    }
    stack.setItemMeta(meta);

    return stack;
  }
}