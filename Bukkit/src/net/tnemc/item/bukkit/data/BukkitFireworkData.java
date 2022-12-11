package net.tnemc.item.bukkit.data;

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

import net.tnemc.item.bukkit.ParsingUtil;
import net.tnemc.item.SerialItemData;
import net.tnemc.item.data.FireworkData;
import net.tnemc.item.data.firework.SerialFireworkEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class BukkitFireworkData extends FireworkData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final FireworkMeta meta = (FireworkMeta)stack.getItemMeta();

    if(meta != null) {

      this.power = meta.getPower();

      if(meta.hasEffects()) {
        for(FireworkEffect eff : meta.getEffects()) {
          effects.add(ParsingUtil.fromEffect(eff));
        }
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

    final FireworkMeta meta = (FireworkMeta)ParsingUtil.buildFor(stack, FireworkMeta.class);

    meta.setPower(power);
    for(final SerialFireworkEffect effect : effects) {
      meta.addEffect(ParsingUtil.fromSerial(effect));
    }

    return stack;
  }
}