package net.tnemc.item.bukkitbase.data;

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
import net.tnemc.item.data.SkullData;
import net.tnemc.item.providers.SkullProfile;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BukkitSkullData extends SkullData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(final ItemStack stack) {

    final SkullMeta meta = (SkullMeta)stack.getItemMeta();

    profile = new SkullProfile();

    try {

      if(meta != null && meta.getOwningPlayer() != null) {
        profile.setUuid(meta.getOwningPlayer().getUniqueId());
      }

    } catch(Exception ignore) {

      profile.setName(meta.getOwner());
    }
  }

  /**
   * This method is used to apply the data to the implementation's locale itemstack format.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public ItemStack apply(final ItemStack stack) {

    final SkullMeta meta = (SkullMeta)ParsingUtil.buildFor(stack, SkullMeta.class);

    if(profile != null) {

      try {

        if(profile.getUuid() != null) {
          meta.setOwningPlayer(Bukkit.getOfflinePlayer(profile.getUuid()));
        }

      } catch(Exception ignore) {

        meta.setOwner(profile.getName());
      }
    }
    stack.setItemMeta(meta);

    return stack;
  }
}