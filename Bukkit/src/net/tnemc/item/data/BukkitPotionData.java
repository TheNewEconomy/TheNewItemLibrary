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

import net.tnemc.item.ParsingUtil;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.potion.PotionEffectData;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class BukkitPotionData extends SerialPotionData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {

    PotionMeta meta = (PotionMeta)stack.getItemMeta();
    if(meta != null) {

      if(meta.hasColor()) colorRGB = meta.getColor().asRGB();
      type = meta.getBasePotionData().getType().name();
      extended = meta.getBasePotionData().isExtended();
      upgraded = meta.getBasePotionData().isUpgraded();

      for(final PotionEffect effect : meta.getCustomEffects()) {

        customEffects.add(new PotionEffectData(effect.getType().getName(),
                                               effect.getAmplifier(),
                                               effect.getDuration(),
                                               effect.hasParticles(),
                                               effect.isAmbient(),
                                               effect.hasIcon()));
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


    PotionMeta meta = (PotionMeta)ParsingUtil.buildFor(stack, PotionMeta.class);

    if(colorRGB != -1) meta.setColor(Color.fromRGB(colorRGB));

    customEffects.forEach((effect)->meta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(effect.getName()),
                                                                        effect.getDuration(),
                                                                        effect.getAmplifier(),
                                                                        effect.isAmbient(),
                                                                        effect.hasParticles(),
                                                                        effect.hasIcon()), true));
    PotionData data = new PotionData(PotionType.valueOf(type), extended, upgraded);
    meta.setBasePotionData(data);
    stack.setItemMeta(meta);
    return stack;
  }
}