package net.tnemc.item.bukkitbase.data.block;
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
import net.tnemc.item.data.CreatureData;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

/**
 * BukkitSpawnerData
 *
 * @author creatorfromhell
 * @since 0.1.7.7
 */
public class BukkitSpawnerData extends CreatureData<ItemStack> {

  /**
   * This method is used to convert from the implementation's ItemStack object to a valid
   * {@link SerialItemData} object.
   *
   * @param stack The locale itemstack object of the implementation.
   */
  @Override
  public void of(ItemStack stack) {
    final BlockStateMeta meta = (BlockStateMeta)stack.getItemMeta();

    if(meta != null && meta.getBlockState() instanceof CreatureSpawner spawner) {

      if(spawner.getSpawnedType() != null) {
        entity = spawner.getSpawnedType().name();
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

    final BlockStateMeta meta = (BlockStateMeta)ParsingUtil.buildFor(stack, BlockStateMeta.class);

    if(meta.getBlockState() instanceof CreatureSpawner spawner) {

      spawner.setSpawnedType(EntityType.valueOf(entity));
      meta.setBlockState(spawner);
      stack.setItemMeta(meta);
    }
    return stack;
  }
}