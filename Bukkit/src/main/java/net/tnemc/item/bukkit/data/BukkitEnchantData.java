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

import net.tnemc.item.SerialItemData;
import net.tnemc.item.bukkit.ParsingUtil;
import net.tnemc.item.data.EnchantStorageData;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class BukkitEnchantData extends EnchantStorageData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final EnchantmentStorageMeta meta = (EnchantmentStorageMeta)stack.getItemMeta();

    if(meta != null) {
      for(final Map.Entry<Enchantment, Integer> entry : meta.getStoredEnchants().entrySet()) {
        enchantments.put(entry.getKey().getKey().toString(), entry.getValue());
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

    final EnchantmentStorageMeta meta = (EnchantmentStorageMeta)ParsingUtil.buildFor(stack, EnchantmentStorageMeta.class);

    for(final Map.Entry<String, Integer> entry : enchantments.entrySet()) {
      meta.addStoredEnchant(Enchantment.getByKey(NamespacedKey.fromString(entry.getKey())),
                            entry.getValue(),
                            true);
    }

    stack.setItemMeta(meta);

    return stack;
  }
}