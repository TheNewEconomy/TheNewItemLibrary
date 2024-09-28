package net.tnemc.item.bukkitbase.data;
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
import net.tnemc.item.data.DamageableData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

/**
 * BukkitDamageData
 *
 * @author creatorfromhell
 * @since 0.0.1.0
 */
public class BukkitDamageData extends DamageableData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(final ItemStack stack) {

    final Damageable meta = (Damageable)stack.getItemMeta();
    if(meta != null) {

      this.damage = meta.getDamage();
      this.maxDamage = meta.getDamage();
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(final ItemStack stack) {

    final Damageable meta = (Damageable)ParsingUtil.buildFor(stack, Damageable.class);

    if(this.damage > -1) {
      meta.setDamage(this.damage);
    }

    if(this.maxDamage > -1) {
      meta.setDamage(this.maxDamage);
    }

    stack.setItemMeta(meta);

    return stack;
  }
}
