package net.tnemc.item.bukkit.platform.impl;
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

import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.platform.impl.ItemMaterial;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;

/**
 * BukkitItemMaterial
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitItemMaterial extends ItemMaterial<BukkitItemStack, ItemStack> {
  /**
   * @param serialized the serialized item stack to use
   * @param item       the item that we should use to apply this applicator to.
   *
   * @return the updated item.
   */
  @Override
  public ItemStack apply(BukkitItemStack serialized, ItemStack item) {

    Material material = null;
    try {
      final NamespacedKey key = NamespacedKey.fromString(serialized.material());
      if(key != null) {
        material = Registry.MATERIAL.get(key);
      }
    } catch(Exception ignore) {
      material = Material.matchMaterial(serialized.material());
    }

    if(material != null) {

      item.setType(material);
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
  public BukkitItemStack serialize(ItemStack item, BukkitItemStack serialized) {
    serialized.material(item.getType().getKey().toString());
    return serialized;
  }
}
