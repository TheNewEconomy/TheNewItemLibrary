package net.tnemc.item.bukkit.data;
/*
 * The New Item Library
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
import net.tnemc.item.data.ColourableArmourData;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ColorableArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

/**
 * BukkitColourArmourData
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitColourArmourData extends ColourableArmourData<ItemStack> {
  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final ColorableArmorMeta meta = (ColorableArmorMeta)stack.getItemMeta();

    if(meta != null && meta.getTrim() != null) {

      this.colorRGB = meta.getColor().asRGB();
      this.material = meta.getTrim().getMaterial().getKey().getKey();
      this.pattern = meta.getTrim().getPattern().getKey().getKey();
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(ItemStack stack) {

    final ColorableArmorMeta meta = (ColorableArmorMeta)ParsingUtil.buildFor(stack, ColorableArmorMeta.class);

    if(material != null && pattern != null) {

      meta.setColor(Color.fromRGB(colorRGB));
      try {

        final TrimMaterial materialObj = Registry.TRIM_MATERIAL.get(NamespacedKey.fromString(material));
        final TrimPattern patternObj = Registry.TRIM_PATTERN.get(NamespacedKey.fromString(pattern));
        if(materialObj != null && patternObj != null) {

          meta.setTrim(new ArmorTrim(materialObj, patternObj));
        }

      } catch(Exception ignore) {
      }
    }
    stack.setItemMeta(meta);

    return stack;
  }
}