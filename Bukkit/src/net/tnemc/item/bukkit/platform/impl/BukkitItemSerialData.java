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

import net.tnemc.item.SerialItemData;
import net.tnemc.item.bukkit.BukkitItemStack;
import net.tnemc.item.bukkit.BukkitMetaBuild;
import net.tnemc.item.bukkit.platform.BukkitItemPlatform;
import net.tnemc.item.bukkitbase.platform.impl.BukkitBaseItemSerialData;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * BukkitItemSerialData
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitItemSerialData extends BukkitBaseItemSerialData<BukkitItemStack> {

  /**
   * @param item       the item that we should use to deserialize.
   * @param serialized the serialized item stack we should use to apply this deserializer to
   *
   * @return the updated serialized item.
   */
  @Override
  public BukkitItemStack serialize(final ItemStack item, final BukkitItemStack serialized) {

    final Optional<SerialItemData<ItemStack>> data = BukkitItemPlatform.PLATFORM.parseMeta(item);
    data.ifPresent(serialized::applyData);

    return serialized;
  }
}