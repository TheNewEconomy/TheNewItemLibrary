package net.tnemc.item.paper.platform;
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

import net.tnemc.item.bukkitbase.data.BukkitSkullData;
import net.tnemc.item.paper.PaperItemStack;
import net.tnemc.item.platform.impl.ItemProfile;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * PaperItemProfile
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class PaperItemProfile extends ItemProfile<PaperItemStack, ItemStack> {
  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   */
  @Override
  public ItemStack apply(PaperItemStack serialized, ItemStack item) {
    final ItemMeta meta = item.getItemMeta();
    if(serialized.profile().isPresent() && meta instanceof SkullMeta skull) {

      if(serialized.profile().get().getUuid() != null) {

        skull.setOwningPlayer(Bukkit.getOfflinePlayer(serialized.profile().get().getUuid()));

      } else if(serialized.profile().get().getUuid() == null && serialized.profile().get().getName() != null) {

        skull.setOwner(serialized.profile().get().getName());
      }
      item.setItemMeta(meta);
    }
    return item;
  }

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   */
  @Override
  public PaperItemStack deserialize(ItemStack item, PaperItemStack serialized) {

    if(item.getItemMeta() instanceof SkullMeta) {
      final BukkitSkullData skullData = new BukkitSkullData();
      skullData.of(item);

      serialized.profile(skullData.getProfile());
    }
    return serialized;
  }
}